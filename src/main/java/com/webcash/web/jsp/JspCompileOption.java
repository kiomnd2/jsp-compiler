package com.webcash.web.jsp;

import org.apache.jasper.Options;
import org.apache.jasper.compiler.JspConfig;
import org.apache.jasper.compiler.TagPluginManager;
import org.apache.jasper.compiler.TldLocationsCache;

import javax.servlet.ServletContext;
import java.io.File;
import java.util.Map;

public class JspCompileOption implements Options {


    private final JspCompiler jspCompiler;
    private final ServletContext servletContext;

    public JspCompileOption( JspCompiler jspCompiler )
    {
        this.jspCompiler = jspCompiler;
        servletContext = jspCompiler.getContext();
    }

    @Override
    public boolean getErrorOnUseBeanInvalidClassAttribute() {
        return false;
    }

    @Override
    public boolean getKeepGenerated() {
        return this.jspCompiler.isKeepGenerated();
    }

    @Override
    public boolean isPoolingEnabled() {
        return false;
    }

    @Override
    public boolean getMappedFile() {
        return false;
    }

    @Override
    public boolean getSendErrorToClient() {
        return false;
    }

    @Override
    public boolean getClassDebugInfo() {
        return false;
    }

    @Override
    public int getCheckInterval() {
        return 0;
    }

    @Override
    public boolean getDevelopment() {
        return false;
    }

    @Override
    public boolean getDisplaySourceFragment() {
        return false;
    }

    @Override
    public boolean isSmapSuppressed() {
        return false;
    }

    @Override
    public boolean isSmapDumped() {
        return false;
    }

    @Override
    public boolean getTrimSpaces() {
        return false;
    }

    @Override
    public String getIeClassId() {
        return null;
    }

    @Override
    public File getScratchDir() {
        return new File(jspCompiler.getScratchDir());
    }

    @Override
    public String getClassPath() {
        return null;
    }

    @Override
    public String getCompiler() {
        return null;
    }

    @Override
    public String getCompilerTargetVM() {
        return null;
    }

    @Override
    public String getCompilerSourceVM() {
        return null;
    }

    @Override
    public String getCompilerClassName() {
        return null;
    }

    @Override
    public TldLocationsCache getTldLocationsCache() {
        return null;
    }

    @Override
    public String getJavaEncoding() {
        return this.jspCompiler.getStrFileEncoding();
    }

    @Override
    public boolean getFork() {
        return false;
    }

    @Override
    public JspConfig getJspConfig() {
        return new JspConfig(servletContext);
    }

    @Override
    public boolean isXpoweredBy() {
        return false;
    }

    @Override
    public TagPluginManager getTagPluginManager() {
        return new TagPluginManager(servletContext);
    }

    @Override
    public boolean genStringAsCharArray() {
        return false;
    }

    @Override
    public int getModificationTestInterval() {
        return 0;
    }

    @Override
    public boolean getRecompileOnFail() {
        return false;
    }

    @Override
    public boolean isCaching() {
        return false;
    }

    @Override
    public Map getCache() {
        return null;
    }
}
