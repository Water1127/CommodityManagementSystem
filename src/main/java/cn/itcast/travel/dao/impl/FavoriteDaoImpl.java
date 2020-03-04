package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.FavoriteDao;
import cn.itcast.travel.domain.Favorite;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.Date;

public class FavoriteDaoImpl implements FavoriteDao {

    /* 私有成员变量 JdbcTemplate */
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 根据 rid 和 uid 查询Favorite对象
     * @param rid
     * @param uid
     * @return
     */
    @Override
    public Favorite isFavorite(int rid, int uid) {
        Favorite favorite = null;
        try {
            String sql = "SELECT * FROM tab_favorite WHERE rid = ? AND uid = ? ;";
             favorite = template.queryForObject(sql, new BeanPropertyRowMapper<>(Favorite.class), rid, uid);
        } catch (DataAccessException e) {
        }
        return favorite;
    }


    /**
     * 根据 rid  查询收藏次数
     * @param rid
     * @return
     */
    @Override
    public int findCountByRid(int rid) {
        String sql = "SELECT COUNT(*) FROM tab_favorite WHERE rid = ?  ;";
        return template.queryForObject(sql, Integer.class, rid);
    }

    @Override
    public void add(int rid, int uid) {
        String sql = "INSERT INTO tab_favorite VALUES(?,?,?);";
        template.update(sql,rid,new Date(),uid );
    }
}
