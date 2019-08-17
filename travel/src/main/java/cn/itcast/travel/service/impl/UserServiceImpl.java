package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.service.UserService;
import cn.itcast.travel.util.MailUtils;
import cn.itcast.travel.util.UuidUtil;

import java.util.UUID;

public class UserServiceImpl implements UserService {
    private UserDao dao = new UserDaoImpl();
    @Override
    public boolean register(User user) {
        User u = dao.findByUsername(user.getUsername());
        if (u != null){
            return false;
        }
        user.setStatus("N");
        user.setCode(UuidUtil.getUuid());
        dao.save(user);

        String s = "<a href='http:localhost/travel/activeUserServlet?code="+user.getCode()+"'>点击激活【旅游网】</a>";
        MailUtils.sendMail(user.getEmail(),s,"激活邮件");

        return true;
    }

    @Override
    public boolean active(String code) {
        UserDao dao = new UserDaoImpl();
        User user = dao.findByCode(code);
        if(user != null){
            dao.update(user);
            return true;
        }
        return false;
    }

    @Override
    public User login(User user) {
        UserDao dao = new UserDaoImpl();
        return dao.findNamePass(user.getUsername(),user.getPassword());
    }
}
