package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    /**
     * 查询用户名是否重复
     * @param username
     */
    User findByUsername(String username);

    /**
     * 添加一名用户
     * @param user
     */
    void addUser(User user);

    /**
     * 根据激活码查询用户
     * @param code
     * @return
     */
    User findByCode(String code);

    /**
     * 更新用户的激活状态
     * @param user
     */
    void updateStatus(User user);

    /**
     * 根据用户名和密码查询用户
     * @return
     */
    User findByUsernameAndPassword(String username,String password);
}
