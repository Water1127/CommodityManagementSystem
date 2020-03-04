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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

//@WebServlet("/registerServlet")
public class RegisterServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        /* 获取web验证码，获取前端页面用户输入的验证码 */
        String check = request.getParameter("check");
        System.out.println("user:"+check);
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
        UserService service = new UserServiceImpl();
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
        ObjectMapper mapper = new ObjectMapper();
        String strResult = mapper.writeValueAsString(result);
        /* 设置写回JSON数据类型 */
        response.setContentType("application/json;charset=utf-8");
        /* 将JSON数据写回客户端 */
        response.getWriter().write(strResult);

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        this.doPost(request, response);
    }
}
