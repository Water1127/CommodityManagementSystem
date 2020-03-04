package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.service.impl.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/category/*")
public class CategoryServlet extends BaseServlet {

    /* service对象 */
    private CategoryService service = new CategoryServiceImpl();

    /**
     * 查询导航分类
     * @return
     */
    public void findAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        /* 调用service的findAll方法 */
        List<Category> all = service.findAll();
        /* 序列化json，并回写数据 */
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.writeValue(response.getOutputStream(),all);
        writeTheValue(all,response); // 调用父类的方法
    }


    public void add(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("add");
    }


}
