package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteImageDao;
import cn.itcast.travel.domain.RouteImg;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class RouteImageDaoImpl implements RouteImageDao {

    /* 私有成员变量 JdbcTemplate */
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据 rid 查询 RouteImg
     * @param rid
     * @return
     */
    @Override
    public List<RouteImg> findByRid(int rid) {
        String sql = "SELECT * FROM tab_route_img WHERE rid = ? ;";
        return template.query(sql, new BeanPropertyRowMapper<>(RouteImg.class),rid);
    }
}
