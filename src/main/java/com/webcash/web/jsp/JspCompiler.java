package com.webcash.web.jsp;

import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.compiler.Compiler;
import org.apache.jasper.compiler.JspConfig;
import org.apache.jasper.compiler.JspRuntimeContext;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;


public class JspCompiler {

    private static final Log log = LogFactory.getLog(JspCompiler.class);


    private String uriRootPath;
    private String scratchDir;
    private JspRuntimeContext runtimeContext;
    private String strFileEncoding = "UTF-8";
    private boolean bKeepGenerated = false;
    private boolean isServlet;
    private JspCServletContext context;
    private JspCompileOption option;


    private String classPath;



    public JspCompiler(String uriRootPath, String scratchDir) throws IOException {
        this.uriRootPath = uriRootPath;
        this.scratchDir = scratchDir;

        File outputDir = new File(this.scratchDir);
        if (!outputDir.exists())
        {
            boolean mkdirs = outputDir.mkdirs();
        }

        // 클래스로더
        ClassLoader classLoader = getClassLoader();

        log.debug("URL Source PATH :: " + uriRootPath);
        log.debug("TARGET PATH :: " + scratchDir);

        initServletContext();
    }

    public void setIsServlet(boolean isServlet) {
        this.isServlet = isServlet;
    }



    public JspCompilationContext getJspCompilationContext(String jspFile) {
        JspCompilationContext jspCompilationContext = new JspCompileContext(jspFile, option, getContext(), getRuntimeContext(), this.isServlet);
        return jspCompilationContext;
    }

    public JspRuntimeContext getRuntimeContext() {
        return runtimeContext;
    }


    private void setRuntimeContext(JspCompileOption options) {
        this.runtimeContext = new JspRuntimeContext(getContext(), options);
    }

    public JspCServletContext getContext() {
        if(this.context == null ){
            this.context = new JspCServletContext(new File(this.uriRootPath));
        }
        return context;
    }

    public void addSourceRoot(File file) {
        this.context.addSource(file);
    }

    public void setUriRoot(String uriRootPath)
    {
        this.uriRootPath = uriRootPath;
    }

    public void setOutputRoot(String scratchDir)
    {
        this.scratchDir = scratchDir;
    }

    public String getUriRootPath() {
        return uriRootPath;
    }

    public String getScratchDir() {
        return scratchDir;
    }

    public String getStrFileEncoding() {
        return strFileEncoding;
    }

    public String getClassPath()
    {
        if( classPath != null )
        {
            return classPath;
        }
        return System.getProperty("java.class.path");
    }

    public String getFileEncoding()
    {
        if (this.strFileEncoding == null)
        {
            return System.getProperty("file.encoding");
        }
        return this.strFileEncoding;
    }

    public void compile(File source) throws Exception
    {
        // 클래스 로드 전에 소스, 타겟 루트 추기화
        // 컴파일 컨텍스트 생성


        String jspFile = source.getAbsolutePath().substring(getUriRootPath().length());
        JspCompilationContext jspCompilationContext = getJspCompilationContext(jspFile);

        //jsp에서 변환된 java file package

        //jsp파일을 변화한 java파일 명. .jsp를 제외한 이름을 그대로 사용한다.
        jspCompilationContext.setServletClassName(source.getName().substring(0, source.getName().length()-4));

        Compiler compiler = jspCompilationContext.createCompiler();
        compiler.compile();

    }

    public boolean isKeepGenerated()
    {
        return this.bKeepGenerated;
    }


    public void setKeepGenerated(boolean bKeepGenerated)
    {
        this.bKeepGenerated = bKeepGenerated;
    }



    protected ClassLoader getClassLoader()
    {
        List<URL> urls = new ArrayList<>();
        // TODO 클래스패스 세팅..


        URL urlsA[]=new URL[urls.size()];
        urls.toArray(urlsA);
        return URLClassLoader.newInstance(urlsA ,getClass().getClassLoader());
    }


    protected void initServletContext() throws IOException
    {
        option = new JspCompileOption(this );
        setRuntimeContext(option);
    }



}
