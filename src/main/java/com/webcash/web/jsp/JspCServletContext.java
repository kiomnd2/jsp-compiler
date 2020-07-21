package com.webcash.web.jsp;

import javax.servlet.RequestDispatcher;
import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class JspCServletContext implements ServletContext {

    private Set<File> files;

    JspCServletContext(File... source)
    {
        files = new HashSet<>(Arrays.asList(source));
    }


    @Override
    public ServletContext getContext(String s) {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public int getMajorVersion() {
        return 1;
    }

    @Override
    public int getMinorVersion() {
        return 0;
    }

    @Override
    public String getMimeType(String s) {
        return null;
    }

    @Override
    public Set getResourcePaths(String s) {
        return this.files.stream().map(File::getAbsoluteFile).collect(Collectors.toSet());
    }

    @Override
    public URL getResource(String s) throws MalformedURLException {
        File resourceFile = getResourceFile(s);
        if (resourceFile != null && resourceFile.exists())
        {
            return resourceFile.toURI().toURL();
        }
        return null;
    }

    @Override
    public InputStream getResourceAsStream(String s) {
        File resourceFile = getResourceFile(s);
        if (resourceFile != null && resourceFile.exists())
        {
            try {
                return new FileInputStream(resourceFile);
            } catch (FileNotFoundException e) {
                e.getCause();
                e.printStackTrace();
            }
        }

        return null;
    }

    protected File getResourceFile(String file)
    {
        Optional<File> first = this.files.stream().filter(path -> new File(path, file).exists()).findFirst();
        return first.orElse(null);
    }

    @Override
    public RequestDispatcher getRequestDispatcher(String s) {
        return null;
    }

    @Override
    public RequestDispatcher getNamedDispatcher(String s) {
        return null;
    }

    @Override
    public Servlet getServlet(String s) throws ServletException {
        return null;
    }

    @Override
    public Enumeration getServlets() {
        return null;
    }

    @Override
    public Enumeration getServletNames() {
        return null;
    }

    @Override
    public void log(String s) {

    }

    @Override
    public void log(Exception e, String s) {

    }

    @Override
    public void log(String s, Throwable throwable) {

    }

    @Override
    public String getRealPath(String s) {
        return null;
    }

    @Override
    public String getServerInfo() {
        return null;
    }

    @Override
    public String getInitParameter(String s) {
        return null;
    }

    @Override
    public Enumeration getInitParameterNames() {
        return null;
    }

    @Override
    public Object getAttribute(String s) {
        return null;
    }

    @Override
    public Enumeration getAttributeNames() {
        return null;
    }

    @Override
    public void setAttribute(String s, Object o) {

    }

    @Override
    public void removeAttribute(String s) {

    }

    @Override
    public String getServletContextName() {
        return null;
    }
}
