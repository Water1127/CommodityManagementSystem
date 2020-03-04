package cn.itcast.travel.web.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 解决全站乱码问题，处理所有的请求
 */
@WebFilter("/*")
public class CharacterFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse rep, FilterChain filterChain) throws IOException, ServletException {

        /* 将Servlet接口转为HttpServlet接口 */
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) rep;

        /* 处理POST请求方式，中文数据乱码问题 */
        String method = request.getMethod();
        if (method.equalsIgnoreCase("post")){
            request.setCharacterEncoding("utf-8");
        }

        /* 处理响应乱码 */
        response.setContentType("text/html;charset=utf-8");
        rep.setCharacterEncoding("UTF-8");

        /* 放行 */
        filterChain.doFilter(request,response);
    }

    @Override
    public void destroy() {

    }
}
