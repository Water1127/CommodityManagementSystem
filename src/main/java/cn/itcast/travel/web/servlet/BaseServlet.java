package cn.itcast.travel.web.servlet;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


public class BaseServlet extends HttpServlet {


    /**
     * 方法的分发，通过请求的路径来调用其子类的方法
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /* 获取请求路径 */
        String URI = req.getRequestURI();
        /* 获取方法名 */
        String methodName = URI.substring(URI.lastIndexOf("/") + 1);
        System.out.println(this.getServletName().substring(29)+"---"+methodName);
        try {
            /* 通过class类，忽略访问修饰符，获取Method对象 */
            Method method = this.getClass().getMethod(methodName, HttpServletRequest.class, HttpServletResponse.class);
            /* 暴力反射 */
//            method.setAccessible(true);
            /* 执行方法 */
            method.invoke(
                    this,
                    req,
                    resp);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    /**
     * 将对象序列化为Json对象，并写回客户端
     * @param o
     */
    public void writeTheValue(Object o,HttpServletResponse response) throws IOException {
        response.setContentType("application/json;charset=utf-8");
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(response.getOutputStream(),o);
    }

    /**
     * 将对象序列化为Json对象，并写回客户端
     * @param o
     * @return
     */
    public String writeTheValueAsString(Object o) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(o);
    }


}
