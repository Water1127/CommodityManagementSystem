package cn.itcast.travel.dao;

import cn.itcast.travel.domain.RouteImg;

import java.util.List;

public interface RouteImageDao {

    /**
     * 根据 rid 查询 RouteImg
     * @param rid
     * @return
     */
    List<RouteImg> findByRid(int rid);

}
