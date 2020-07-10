package com.webcash.web.jsp;

import org.apache.jasper.Options;
import org.apache.jasper.compiler.JspConfig;
import org.apache.jasper.compiler.JspRuntimeContext;
import org.apache.jasper.compiler.TagPluginManager;
import org.apache.jasper.servlet.JspCServletContext;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;

import javax.servlet.ServletContext;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;


public class JspCompiler {

    private static final Log log = LogFactory.getLog(JspCompiler.class);


    private String uriRootPath;
    private String scratchDir;
    private ClassLoader classLoader;
    private ServletContext context;
    private JspRuntimeContext runtimeContext;
    private String strFileEncoding = "UTF-8";
    private boolean bKeepGenerated = false;


    private String classPath;



    public JspCompiler(String uriRootPath, String scratchDir) {
        this.uriRootPath = uriRootPath;
        this.scratchDir = scratchDir;

        File outputDir = new File(this.scratchDir);
        if (!outputDir.exists())
        {
            boolean mkdirs = outputDir.mkdirs();
        }
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

    public ServletContext getContext() {
        return context;
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

    public void compile() throws Exception
    {
        // 클래스 로드 전에 소스, 타겟 루트 추기화

        // 클래스로더
        if (classLoader == null)
        {
            this.classLoader = getClassLoader();
        }

        if (uriRootPath != null)
        {
            log.debug("URL Source PATH :: " + uriRootPath);
        }

        if (scratchDir != null)
        {
            log.debug("TARGET PATH :: " + scratchDir);
        }

        // 서블릿 초기화
        initServletContext();

        //



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
        PrintWriter log = new PrintWriter(System.out);
        URL resourceBase = new File(uriRootPath).getCanonicalFile().toURI().toURL();

        this.context = new JspCServletContext(log, resourceBase);

        JspCompileOption options = new JspCompileOption(this );

        this.runtimeContext = new JspRuntimeContext(context, options);

    }


}
