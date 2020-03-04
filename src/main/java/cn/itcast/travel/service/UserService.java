package cn.itcast.travel.service;

import cn.itcast.travel.domain.User;

public interface UserService {
    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean registerUser(User user);

    /**
     * 邮箱验证
     * @param code
     * @return
     */
    boolean active(String code);

    /**
     * 登录验证
     * @param user
     * @return
     */
    User login(User user);
}
