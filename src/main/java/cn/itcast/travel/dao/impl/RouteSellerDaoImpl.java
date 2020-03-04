package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteSellerDao;
import cn.itcast.travel.domain.Seller;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;


public class RouteSellerDaoImpl implements RouteSellerDao {

    /* 私有成员变量 JdbcTemplate */
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据 sid 查询 Seller
     * @param sid
     * @return
     */
    @Override
    public Seller findByRid(int sid) {
        String sql = "SELECT * FROM tab_seller WHERE sid = ? ;";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Seller.class),sid);
    }

}
