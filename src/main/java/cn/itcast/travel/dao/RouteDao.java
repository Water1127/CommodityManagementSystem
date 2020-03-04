package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    int findTotalCount(int cid, String rname);

    /**
     * 查询当前页面的数据的集合
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    List<Route> findByPage(int cid, int start, int pageSize, String rname);

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    Route findOne(int rid);


}
