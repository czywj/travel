package cn.itcast.travel.dao;

import cn.itcast.travel.domain.User;

public interface UserDao {
    User findByUsername(String username);
    void save(User user);

    User findByCode(String code);

    void update(User user);

    User findNamePass(String username,String password);
}
