package com.webcash.web.jsp;

import org.apache.jasper.JasperException;
import org.apache.jasper.JspCompilationContext;
import org.apache.jasper.Options;
import org.apache.jasper.compiler.Compiler;
import org.apache.jasper.compiler.JDTCompiler;
import org.apache.jasper.compiler.JspRuntimeContext;
import org.apache.jasper.servlet.JspServletWrapper;

import javax.servlet.ServletContext;

public class JspCompileContext extends JspCompilationContext {

    public JspCompileContext(String uriRootPath, JspCompileOption options, ServletContext context, JspRuntimeContext runtimeContext, boolean isServlet) {
        super(uriRootPath, false, options, context, null , runtimeContext);
    }


    @Override
    public Compiler createCompiler() throws JasperException {
        JDTCompiler jdtCompiler = new JDTCompiler();
        jdtCompiler.init(this, null);
        return jdtCompiler;
    }
}
