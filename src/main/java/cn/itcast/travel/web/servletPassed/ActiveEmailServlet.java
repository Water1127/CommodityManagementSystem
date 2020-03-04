package cn.itcast.travel.web.servletPassed;

import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

//@WebServlet("/activeEmailServlet")
public class ActiveEmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* 获取激活码 */
        String code = request.getParameter("code");
        /* 激活码非空 */
        if (code != null) {
            /* 调用service中的 active方法，激活邮箱 */
            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            /* 是否成功激活 */
            String mas = null;
            if (flag){
                System.out.println("激活成功");
                /* href='/login.html'   “/”就是虚拟目录
                *  href='login.html'    自动添加“/user/”
                *  */
                mas="激活成功，请<a href='/login.html'>登录</a>";
            }else {
                System.out.println("激活失败");
                mas="激活失败，请联系管理员！";
            }
            /* 写回数据 */
//            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(mas);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
