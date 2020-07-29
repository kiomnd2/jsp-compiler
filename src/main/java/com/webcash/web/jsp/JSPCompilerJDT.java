package com.webcash.web.jsp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;


import org.apache.jasper.JasperException;

class JSPCompilerJDT extends org.apache.jasper.compiler.JDTCompiler
{
    private static final String[] REMOVE_LINE = new String[] { " _jspx_dependants", "javax.el.ExpressionFactory ", "org.apache.AnnotationProcessor ", "_jspxFactory" };
    private static final String[] REMOVE_LINE2 = new String[] { " ServletContext application", "ServletConfig config", "JspWriter _jspx_out", "PageContext pageContext", "PageContext _jspx_page_context", "Object page = this" };
    private static final String[] REMOVE_METHOD = new String[] { "_jspInit()", "jspDestroy()", "getDependants()" };

    private boolean _bServlet = false;
    private ToJavaInterceptor _writerInfo = null;

    public JSPCompilerJDT() {
    }

    public JSPCompilerJDT(boolean servletType, ToJavaInterceptor writerInfo)
    {
        super();
        _bServlet = servletType;
        _writerInfo = writerInfo;
    }

    public boolean isServletFile()
    {
        return _bServlet;
    }

    protected ToJavaInterceptor getToJavaInterceptor()
    {
        return _writerInfo;
    }

    protected String[] generateJava() throws Exception
    {
        String[] result = super.generateJava();
        changeSimpleServlet(new File(ctxt.getServletJavaFileName()));
        return result;
    }

    @Override
    protected void generateClass(String[] smap) throws Exception
    {
        super.generateClass(smap);
    }

    protected File changeSimpleServlet(File file) throws IOException
    {
        File tmpFile = null;
        BufferedReader read = null;
        PrintWriter out = null;
        try
        {
            read = new BufferedReader(new FileReader(file));
            tmpFile = new File(file.getParentFile(), file.getName() + ".tmp");
            out = new PrintWriter(tmpFile);
            int nState = 0;
            boolean bRemoveTag = false;
            String s = null;
            while ((s = read.readLine()) != null)
            {
                if (bRemoveTag)
                {
                    if (s.trim().equals("}"))
                    {
                        bRemoveTag = false;
                    }
                    continue;
                }
                // 1.
                if (nState == 0)
                {
                    if (isServletFile() && s.indexOf(" class ") != -1 && s.indexOf(" extends ") != -1)
                    {
                        s = s.substring(0, s.indexOf(" extends ")) + " extends javax.servlet.http.HttpServlet";
                    }
                    else if (s.indexOf("implements ") != -1)
                    {
                        s = "{";
                        nState = 1;
                    }
                }
                else if (nState == 1)
                {
                    // 3.
                    if (hasTag(REMOVE_LINE, s))
                    {
                        continue;
                    }
                    if (hasTag(REMOVE_METHOD, s))
                    {
                        bRemoveTag = true;
                        continue;
                    }

                    if (s.indexOf("_jspService(") != -1)
                    {
                        if (isServletFile())
                        {
                            s = "public void service(HttpServletRequest request, HttpServletResponse response)";
                        }
                        nState = 2;

                        if (getInterceptorValue(ToJavaInterceptor.KEY_ADD_METHOD) != null)
                        {
                            s = getInterceptorValue(ToJavaInterceptor.KEY_ADD_METHOD) + "\n\t" + s;
                        }

                    }
                }
                else if (nState == 2)
                {
                    if (s.indexOf("HttpSession session") != -1)
                    {
                        if (getInterceptorValue(ToJavaInterceptor.KEY_SESSION_VARIABLE) != null)
                        {
                            s = getInterceptorValue(ToJavaInterceptor.KEY_SESSION_VARIABLE);
                        }
                    }
                    else if (s.indexOf("JspWriter out") != -1)
                    {
                        if (getInterceptorValue(ToJavaInterceptor.KEY_JSP_WRITE_VARIABLE) != null)
                        {
                            s = getInterceptorValue(ToJavaInterceptor.KEY_JSP_WRITE_VARIABLE);
                        }
                        else if (isServletFile())
                        {
                            s = "    java.io.PrintWriter out = null;";
                        }
                    }
                    else if (s.indexOf("PageContext _jspx_page_context") != -1)
                    {
                        if (getInterceptorValue(ToJavaInterceptor.KEY_VARIABLE_END) != null)
                        {
                            out.println(getInterceptorValue(ToJavaInterceptor.KEY_VARIABLE_END));
                        }
                        continue;
                    }
                    else if (s.indexOf("_jspxFactory.getPageContext") != -1)
                    {

                        nState = 3;
                        continue;
                    }
                    else if (hasTag(REMOVE_LINE2, s))
                    {
                        continue;
                    }

                }
                else if (nState == 3)
                {
                    do
                    {
                        if (s.indexOf("getSession()") != -1)
                        {
                            if (getInterceptorValue(ToJavaInterceptor.KEY_SESSION_ASSIGNMENT) == null)
                            {
                                out.println("      session = request.getSession();");
                            }
                            else
                            {
                                out.println(getInterceptorValue(ToJavaInterceptor.KEY_SESSION_ASSIGNMENT));
                            }

                        }
                        else if (s.indexOf("getOut()") != -1)
                        {
                            if (getInterceptorValue(ToJavaInterceptor.KEY_JSP_WRITE_ASSIGNMENT) != null)
                            {
                                out.println(getInterceptorValue(ToJavaInterceptor.KEY_JSP_WRITE_ASSIGNMENT));
                            }
                            else if (isServletFile())
                            {
                                out.println("      out = response.getWriter();");
                            }
                            else


                            {
                                out.println(s);
                                ;
                            }
                        }
                        else if (s.trim().equals(""))
                        {
                            if (getInterceptorValue(ToJavaInterceptor.KEY_VARIABLE_ASSIGNMENT_END) != null)
                            {
                                out.println(getInterceptorValue(ToJavaInterceptor.KEY_VARIABLE_ASSIGNMENT_END));
                            }
                            nState = 4;
                            break;
                        }
                    }
                    while ((s = read.readLine()) != null);
                    continue;
                }
                else if (nState == 4 && (s.indexOf("t instanceof SkipPageException") != -1))
                {
                    // 오류처리부
                    bRemoveTag = true;
                    nState = 5;
                    if (getInterceptorValue(ToJavaInterceptor.KEY_CATCH) != null)
                    {

                        s = getInterceptorValue(ToJavaInterceptor.KEY_CATCH) + "\n\t        throw new ServletException(t);";
                    }
                    else
                    {
                        s = "        throw new ServletException(t);";
                    }

                }
                else if (nState == 5 && (s.indexOf("} finally {") != -1))
                {
                    s = "    }";
                    if (getInterceptorValue(ToJavaInterceptor.KEY_FINALLY) != null)
                    {
                        s = s + "finally{ \n\t" + getInterceptorValue(ToJavaInterceptor.KEY_FINALLY) + "\n}";
                    }
                    bRemoveTag = true;
                }
                out.println(s);
            }
            out.flush();
        }
        finally
        {
            if (read != null)
            {
                try{read.close();}catch (Throwable t){}
            }
            if (out != null)
            {
                try{out.close();}catch (Throwable t){}
            }
        }
        file.delete();
        tmpFile.renameTo(file);
        return file;
    }

    protected String getInterceptorValue(String key)
    {
        return getToJavaInterceptor() == null ? null : getToJavaInterceptor().get(key);
    }

    private boolean hasTag(String[] tags, String s)
    {
        for (String tag : tags)
        {
            if (s.indexOf(tag) != -1) { return true; }
        }
        return false;
    }
}
