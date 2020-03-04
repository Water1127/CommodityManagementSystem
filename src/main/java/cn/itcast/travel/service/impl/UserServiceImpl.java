package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

public class UserServiceImpl implements UserService {

    private UserDao dao = new UserDaoImpl();

    /**
     * 用户注册
     * @param user
     * @return
     */
    @Override
    public boolean registerUser(User user) {
        /* 调用dao，检查用户名是否重复 */
        User userByUsername = dao.findByUsername(user.getUsername());
        if (userByUsername!=null){
            return false;
        }
        /* 设置验证码 */
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        /* 调用dao，添加用户 */
        dao.addUser(user);
        /* 发送邮件 */
        String content = "<h1><a href='http://localhost/user/active?code="+user.getCode()+"'>点击激活【黑马旅游网】</a></h1>";
        MailUtils.sendMail(user.getEmail(),content,"激活邮件");

        return true;
    }

    /**
     * 邮箱验证
     * @param code
     * @return
     */
    @Override
    public boolean active(String code) {
        /* 根据激活码查询用户对象 */
        User user = dao.findByCode(code);
        /* 如果存在更新用户信息 status */
        if (user!=null){
            dao.updateStatus(user);
            return true;
        }else {
            return false;
        }
    }

    /**
     * 登录验证
     * @param user
     * @return
     */
    @Override
    public User login(User user) {
        return dao.findByUsernameAndPassword( user.getUsername(),user.getPassword());
    }
}
