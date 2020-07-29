package com.webcash.web.jsp;

import java.util.HashMap;
import java.util.Map;

/**
 * JSP -> JAVA 변환 단계에 접근할수 있는 Class
 *
 * 파일이 아래와 같을경우 각 KEY 를 통해 각각의 단계에 접근할수 있다.
<pre>
package _jsp;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;

public final class index extends org.apache.jasper.runtime.HttpJspBase
{

    <b>KEY_ADD_METHOD</b>

    public void _jspService(HttpServletRequest request, HttpServletResponse response)
        throws java.io.IOException, ServletException {

    JspFactory _jspxFactory = null;
    HttpSession session = null; <b>KEY_SESSION_VARIABLE</b>
    JspWriter  out = null; <b>KEY_JSP_WRITE_VARIABLE</b>
    <b>KEY_VARIABLE_END</b>

    try {
      _jspxFactory = JspFactory.getDefaultFactory();
      response.setContentType("text/html; charset=UTF-8");
      session = request.getSession(); <b>KEY_SESSION_ASSIGNMENT</b>
      out = ? <b>KEY_JSP_WRITE_ASSIGNMENT</b>

      <b>KEY_VARIABLE_ASSIGNMENT_END</b>

      out.write("\n<!DOCTYPE html PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html>\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<title>Insert title here</title>\n</head>\n<body>\n<pre>\nHi~~!!!\n");
      out.print(new root.Test().toString());
      out.write("\n=====dadd\n");
      out.print(out.getClass());
      out.write("\n</pre>\n</body>\n</html>");
    } catch (Throwable t) {
      <b>KEY_CATCH</b>
            throw new ServletException(t);
    }finally{
        <b>KEY_FINALLY</b>
}
  }
}
</pre>

 * @author alfree
 *
 */

public class ToJavaInterceptor
{
    public static final String KEY_SESSION_VARIABLE        = "SESSION_VARIABLE";

    public static final String KEY_JSP_WRITE_VARIABLE      = "JSP_WRITE_VARIABLE";

    public static final String KEY_SESSION_ASSIGNMENT      = "SESSION_ASSIGNMENT";

    public static final String KEY_JSP_WRITE_ASSIGNMENT    = "JSP_WRITE_ASSIGNMENT";

    public static final String KEY_VARIABLE_END            = "VARIABLE_END";

    public static final String KEY_VARIABLE_ASSIGNMENT_END = "VARIABLE_ASSIGNMENT_END";

    public static final String KEY_ADD_METHOD              = "ADD_METHOD";
    public static final String KEY_FINALLY                 = "FINALLY";
    public static final String KEY_CATCH                   =  "CATCH";


    private Map<String, String> _map = new HashMap<String, String>();

    public ToJavaInterceptor()
    {
    }

    public void put(String key, String value)
    {
        _map.put(key, value);
    }

    public String get(String key)
    {
        return _map.get(key);
    }

    public void putAll(Map<String, String> map)
    {
        _map.putAll(map);
    }
}
