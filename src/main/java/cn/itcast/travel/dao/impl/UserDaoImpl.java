package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class UserDaoImpl implements UserDao {

    /* 私有成员变量 JdbcTemplate */
    private  JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource()) ;


    /**
     * 根据用户名查询用户
     * @param username
     */
    @Override
    public User findByUsername(String username) {
        User user = null;
        try {
            /* 定义SQL语句 */
            String sql = "SELECT * FROM tab_user WHERE username = ? ;";
            /*  */
            user = template.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(User.class),
                    username);
        } catch (Exception e) {
        }
        return user;
    }

    /**
     * 添加一名用户
     * @param user
     */
    @Override
    public void addUser(User user) {
        /* 定义SQL语句 */
        String sql = "INSERT INTO tab_user(username,password,name,birthday,sex,telephone,email,code,status) " +
                "VALUES(?,?,?,?,?,?,?,?,?);";

        template.update(
                sql,
                user.getUsername(),
                user.getPassword(),
                user.getName(),
                user.getBirthday(),
                user.getSex(),
                user.getTelephone(),
                user.getEmail(),
                user.getCode(),
                user.getStatus());

    }

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    @Override
    public User findByCode(String code) {
        /* 防止返回为空 */

        User user = null;
        try {
            String sql = "SELECT * FROM tab_user WHERE code = ? ;";
            user = template.queryForObject(sql, new BeanPropertyRowMapper<>(User.class), code);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 更新用户的激活状态
     * @param user
     */
    @Override
    public void updateStatus(User user) {
        String sql = "UPDATE tab_user SET status = 'Y' WHERE uid = ? ;";
        template.update(sql,user.getUid());
    }

    /**
     * 根据用户名和密码查询用户
     * @return
     */
    @Override
    public User findByUsernameAndPassword(String username,String password) {

        User user = null;
        try {
            String sql = "SELECT * FROM tab_user WHERE username = ? AND password=  ?;";
            user = template.queryForObject(
                    sql,
                    new BeanPropertyRowMapper<>(User.class),
                    username,
                    password);
        } catch (Exception e) {
            System.out.println("用户没有输入用户名或密码或验证码");
        }
        return user;
    }
}
