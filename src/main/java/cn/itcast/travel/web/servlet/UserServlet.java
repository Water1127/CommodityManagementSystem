package cn.itcast.travel.web.servlet;

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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user/*")
public class UserServlet extends BaseServlet {

    /* service对象 */
    private UserService service = new UserServiceImpl();

    /**
     * 注册功能
     * @param request
     * @param response
     * @throws IOException
     */
    public void register(HttpServletRequest request, HttpServletResponse response) throws IOException {
        /* 获取web验证码，获取前端页面用户输入的验证码 */
        String check = request.getParameter("check");
        /* 获取servlet验证码，从Session中获取CheckCodeServlet生成的验证码 */
        HttpSession session = request.getSession();
        String checkcode_server = (String) session.getAttribute("CHECKCODE_SERVER");
        /* 避免验证码重复使用，从Session中删除CheckCodeServlet生成的验证码 */
        session.removeAttribute("CHECKCODE_SERVER");
        /* 验证验证码是否正确 */
        if (checkcode_server == null || !checkcode_server.equalsIgnoreCase(check)){
            /* 设置响应数据 */
            ResultInfo result = new ResultInfo();
            result.setFlag(false);
            result.setErrorMsg("验证码错误！");
            /* 将result对象序列化为JSON对象*/
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(result);
            /* 设置写回JSON数据类型 */
            response.setContentType("application/json;charset=utf-8");
            /* 将JSON数据写回客户端 */
            response.getWriter().write(json);
            return;
        }
        /* 获取web数据，前端页面将单个注册用户的数据作为一个封装的Map集合传递过来。 */
        Map<String, String[]> map = request.getParameterMap();
        /* 获取domain对象，对象中的每个成员变量对应着Map集合中的每个数据。 */
        User user = new User();
        /* 将数据封装进对象，调用org.apache.commons包下的BeanUtils类中的populate方法*/
        try {
            BeanUtils.populate(user,map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        /* 调用service方法，注册用户的数据 */
//        UserService service = new UserServiceImpl();
        boolean flag = service.registerUser(user);
        /* 设置响应数据 */
        ResultInfo result = new ResultInfo();
        if (flag){
            // 注册成功
            result.setFlag(true);
        }else {
            // 注册失败
            result.setFlag(false);
            result.setErrorMsg("注册失败！");
        }
        /* 将result对象序列化为JSON对象*/
//        ObjectMapper mapper = new ObjectMapper();
//        String strResult = mapper.writeValueAsString(result);
        /* 设置写回JSON数据类型 */
//        response.setContentType("application/json;charset=utf-8");
        String s = writeTheValueAsString(result);
        /* 将JSON数据写回客户端 */
        response.getWriter().write(s);
    }

    /**
     * 登录功能
     * @param request
     * @param response
     * @throws IOException
     */
    public void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
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
//        UserService service = new UserServiceImpl();
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
            request.getSession().setAttribute("user",user);
        }
        /* 响应数据 */
//        ObjectMapper mapper = new ObjectMapper();
//        response.setContentType("application/json;charset=utf-8");
//        mapper.writeValue(response.getOutputStream(),result);
        writeTheValue(result,response);
    }

    /**
     * 回写登录用户名
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void getUsername(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 获取登录用户名 */
        User user = (User)request.getSession().getAttribute("user");
        String username = null;
        try {
            username = user.getUsername();
        } catch (Exception e) {
        }
        /* 回写数据 */
//        response.setContentType("application/json;charset=utf-8");
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(),username);
        writeTheValue(username,response);
    }

    /**
     * 退出功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void exit(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 销毁session */
        request.getSession().invalidate();
        /* 跳转页面 */
        response.sendRedirect(request.getContextPath()+"/login.html");
    }

    /**
     * 激活邮箱功能
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 获取激活码 */
        String code = request.getParameter("code");
        /* 激活码非空 */
        if (code != null) {
            /* 调用service中的 active方法，激活邮箱 */
//            UserService service = new UserServiceImpl();
            boolean flag = service.active(code);
            /* 是否成功激活 */
            String mas = null;
            if (flag) {
                System.out.println("激活成功");
                mas = "激活成功，请<a href='/login.html'>登录</a>";
            } else {
                System.out.println("激活失败");
                mas = "激活失败，请联系管理员！";
            }
            /* 写回数据 */
//            response.setContentType("text/html;charset=utf-8");
            response.getWriter().write(mas);
        }

    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("add");
    }



}
