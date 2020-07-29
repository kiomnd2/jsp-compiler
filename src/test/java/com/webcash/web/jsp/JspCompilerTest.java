package com.webcash.web.jsp;

import com.sun.org.apache.xerces.internal.xs.ItemPSVI;
import org.apache.jasper.compiler.JspRuntimeContext;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static org.hamcrest.CoreMatchers.is;


public class JspCompilerTest {

    File outputDir;

    @Before
    public void init() {
        File tempDir = new File(System.getProperty("jex.test.temp",
                "output/tmp"));
        outputDir = new File(tempDir, "jspc");
    }

    @After
    public void cleanup() throws IOException {
        remove(outputDir);
    }

    @Test
    public void 웹애베서_잘_컴파일찾아가는지_테스트() throws Exception
    {
        File appDir = new File("src/test/java/webapp");

        JspCompiler jspCompiler = new JspCompiler(appDir.getAbsolutePath(), outputDir.getAbsolutePath());
        Assert.assertTrue(appDir.exists());
        Assert.assertTrue(outputDir.exists());
        Assert.assertNotNull( jspCompiler.getContext());
        Assert.assertNotNull( jspCompiler.getClassPath());
    }

    @Test
    public void 컴파일_테스트() throws Exception {
        File appDir = new File("src/test/java/webapp/jsp");
        JspCompiler jspCompiler = new JspCompiler(appDir.getAbsolutePath(), outputDir.getAbsolutePath());
        File file = new File("src/test/java/webapp/jsp/test.jsp");
        jspCompiler.compile(file);


    }



    private void remove(File base) throws IOException{
        if (!base.exists()) {
            return;
        }
        Files.walkFileTree(base.toPath(), new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file,
                                             BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir,
                                                      IOException exc) throws IOException {
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
