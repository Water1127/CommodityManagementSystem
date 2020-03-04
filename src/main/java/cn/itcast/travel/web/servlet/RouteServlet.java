package cn.itcast.travel.web.servlet;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.FavoriteService;
import cn.itcast.travel.service.RouteService;
import cn.itcast.travel.service.impl.FavoriteServiceImpl;
import cn.itcast.travel.service.impl.RouteServiceImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet("/route/*")
public class RouteServlet extends BaseServlet {

    /* service对象 */
    private RouteService service = new RouteServiceImpl();
    private FavoriteService service_favorite = new FavoriteServiceImpl();


    /**
     * 分页查询
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void pageQuery(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 接收参数 */
            /* 不同的cid，查询的内容不同； */
        String currentPageStr = request.getParameter("currentPage");
        String pageSizeStr = request.getParameter("pageSize");
        String cidStr = request.getParameter("cid");
        /* 接收搜索栏数据 */
        String rname = request.getParameter("rname");
            /* 乱码问题 */
//        rname = new String(rname.getBytes("iso-8859-1"),"utf-8");
        /* 处理参数 */
        int currentPage = 0;
        if (currentPageStr != null && currentPageStr.length()> 0){
            currentPage = Integer.parseInt(currentPageStr);
        }else {
            /* 防止第一次访问没有获取当前页码的状况，默认为1 */
            currentPage = 1;
        }
        int pageSize = 0;
        if (pageSizeStr != null && pageSizeStr.length()> 0){
            pageSize = Integer.parseInt(pageSizeStr);
        }else {
            /* 防止没有获取的情况，默认为5 */
            pageSize = 5;
        }
        int cid = 0;
        if (cidStr != null && cidStr.length()> 0 && !"null".equals(cidStr)){
            cid = Integer.parseInt(cidStr);
        }
        /* 调用service 查询PageBean对象 */
        PageBean<Route> route = service.pageQuery(cid,currentPage,pageSize,rname);
        /* 将PageBean对象序列化为Json */
        writeTheValue(route,response);

    }

    /**
     * 根据rid查询旅游路线详细信息
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void findOne(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 接收rid */
        String rid = request.getParameter("rid");

        /* 调用service 查询Route对象 */
        Route route = service.findOne(rid);

        /* 序列化为Json，写回客户端 */
        writeTheValue(route,response);

    }

    /**
     * 判断用户是否收藏了某线路
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void isFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 获取线路 */
        String rid = request.getParameter("rid");
        /* 获取当前登录用户对象 */
        User user = (User) request.getSession().getAttribute("user");
        /* 判断用户是否登录 */
        int uid;
        if (user!=null){
            uid = user.getUid();
        }else {
            uid = 0;
        }
        /* 调用service查询是否收藏 */
        boolean favorite = service_favorite.isFavorite(rid, uid);
        /* 写回客户端 */
        writeTheValue(favorite,response);
    }

    /**
     * 添加收藏
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void addFavorite(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        /* 获取rid */
        String rid = request.getParameter("rid");
        /* 获取当前登录的用户对*/
        User user = (User) request.getSession().getAttribute("user");
        /* 判断用户是否登录 */
        int uid;
        if (user!=null){
            uid = user.getUid();
        }else {
            return;
        }
        /* 调用service 添加收藏 */
        service_favorite.add(rid,uid);



    }
}
