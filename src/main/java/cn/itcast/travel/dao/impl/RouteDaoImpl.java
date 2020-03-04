package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl  implements RouteDao {

    /* 私有成员变量 JdbcTemplate */
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());

    /**
     * 查询总记录数
     * @param cid
     * @param rname
     * @return
     */
    @Override
    public int findTotalCount(int cid, String rname) {

        /* 动态SQL */
          /* 定义SQL模板*/
        String sql = "SELECT COUNT(*) FROM tab_route WHERE 1 = 1  ";
          /* 判断参数是否有值 */
        StringBuilder sb =new StringBuilder(sql);
        List params = new ArrayList(); // 判断条件的数量
        if (cid!=0){
            sb.append(" AND cid = ? ");
            params.add(cid);
        }
        if (rname!=null&&rname.length()>0){
            sb.append(" AND rname like ? ");//模糊查询
            params.add("%"+rname+"%");
        }
        sb.append(";");
        sql = sb.toString();

        return template.queryForObject(sql, Integer.class, params.toArray());
    }

    /**
     * 查询当前页面的数据的集合
     * @param cid
     * @param start
     * @param pageSize
     * @param rname
     * @return
     */
    @Override
    public List<Route> findByPage(int cid, int start, int pageSize, String rname) {
        /* 动态SQL */
          /* 定义SQL模板*/
        String sql = "SELECT * FROM tab_route WHERE 1 = 1 ";
          /* 判断参数是否有值 */
        StringBuilder sb =new StringBuilder(sql);
        List params = new ArrayList(); // 判断条件的数量
        if (cid!=0){
            sb.append(" AND cid = ? ");
            params.add(cid);
        }
        if (rname!=null&&rname.length()>0){
            sb.append(" AND rname like ? ");//模糊查询
            params.add("%"+rname+"%");
        }
        sb.append("LIMIT ? , ? ;");
        params.add(start);
        params.add(pageSize);
        sql = sb.toString();

        return template.query(sql, new BeanPropertyRowMapper<>(Route.class), params.toArray());
    }

    /**
     * 根据rid查询
     * @param rid
     * @return
     */
    @Override
    public Route findOne(int rid) {
        /* 定义SQL语句 */
        String sql = "SELECT * FROM tab_route WHERE rid = ? ;";
        return template.queryForObject(sql, new BeanPropertyRowMapper<>(Route.class), rid);
    }
}
