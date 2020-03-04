package cn.itcast.travel.web.servletPassed;

import cn.itcast.travel.domain.ResultInfo;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.service.impl.UserServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//@WebServlet("/loginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 获取用户名和密码 */
        Map<String, String[]> map = request.getParameterMap();
        /* 获取User对象*/
        User user_login = new User();
        try {
            /* 将用用户名和密码封装到User对象中 */
            BeanUtils.populate(user_login,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        /* 调用service中的 login方法，根据用户名查询用户 */
        UserService service = new UserServiceImpl();
        User user = service.login(user_login);
        /* 判断此用户是否存在 */
        ResultInfo result = new ResultInfo();
        if (user == null) {
            result.setFlag(false);
            result.setErrorMsg("用户名或密码错误");
        }
        /* 判断此用户邮箱是否已激活 */
        if (user!=null && !"Y".equals(user.getStatus())){
            result.setFlag(false);
            result.setErrorMsg("邮箱未激活，请激活邮箱");
        }
        /* 登录成功 */
        if (user!=null && "Y".equals(user.getStatus())){
            result.setFlag(true);
            /* 将用户名存储到session */
            request.getSession().setAttribute("username",user.getUsername());
        }
        /* 响应数据 */
        ObjectMapper mapper = new ObjectMapper();
        response.setContentType("application/json;charset=utf-8");
//        response.getWriter().write(mapper.writeValueAsString(result));
//        mapper.writeValue(response.getWriter(),result);
        mapper.writeValue(response.getOutputStream(),result);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
