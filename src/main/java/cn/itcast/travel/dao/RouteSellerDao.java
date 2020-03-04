package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Seller;


public interface RouteSellerDao {

    /**
     * 根据 sid 查询 Seller
     * @param rid
     * @return
     */
    Seller findByRid(int rid);


}
