package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.dao.RouteSellerDao;
import cn.itcast.travel.dao.impl.FavoriteDaoImpl;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.dao.impl.RouteImageDaoImpl;
import cn.itcast.travel.dao.impl.RouteSellerDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {

    /* 成员变量 dao */
    private RouteDao dao = new RouteDaoImpl();
    private RouteImageDao daoImg = new RouteImageDaoImpl();
    private RouteSellerDao daoSeller = new RouteSellerDaoImpl();
    private FavoriteDao dao_favorite = new FavoriteDaoImpl();

    /**
     * 分类查询
     * @param cid
     * @param currentPage
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public PageBean<Route> pageQuery(int cid, int currentPage, int pageSize, String rname) {
        /* 创建PageBean */
        PageBean<Route> pageBean =new PageBean<>();
        /* 设置当前页码 */
        pageBean.setCurrentPage(currentPage);
        /* 设置每页显示的记录数 */
        pageBean.setPageSize(pageSize);
        /* 设置总记录数 */
        int totalCount = dao.findTotalCount(cid,rname);
        pageBean.setTotalCount(totalCount);
        /* 设置总页数 */
        int totalPage = totalCount%pageSize==0 ? totalCount/pageSize : totalCount/pageSize+1;
        pageBean.setTotalPage(totalPage);
        /* 设置当前页显示的数据的集合 */
        int start = (currentPage-1) * pageSize;
        /* 调用dao 分页查询 */
        List<Route> pageList = dao.findByPage(cid, start, pageSize,rname);
        /* 将查询结果list集合封装到PageBean<Route>对象 */
        pageBean.setList(pageList);
        /* 返回PageBean<Route> */
        return pageBean;
    }

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    @Override
    public Route findOne(String rid) {
        /* route表 */
        /* 根据rid 查询Route对象 */
        Route route = dao.findOne(Integer.parseInt(rid));

        /* tab_route_img表 */
        /* 根据rid 查询RouteImg集合对象 */
        List<RouteImg> routeImgs = daoImg.findByRid(Integer.parseInt(rid));
        /* 将RouteImg集合对象设置到Route对象 */
        route.setRouteImgList(routeImgs);

        /* tab_seller表 */
        /* 根据sid 查询Seller对象 */
        Seller seller = daoSeller.findByRid(route.getSid());
        /* 将Seller对象设置到Route对象 */
        route.setSeller(seller);

        /* tab_favorite表 */
        /* 根据rid 查询收藏次数 */
        int count = dao_favorite.findCountByRid(route.getRid());
        /* 将收藏次数设置到Route对象 */
        route.setCount(count);

        return route;
    }


}
