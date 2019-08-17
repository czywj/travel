package cn.itcast.travel.test;

import cn.itcast.travel.dao.UserDao;
import cn.itcast.travel.dao.impl.UserDaoImpl;
import cn.itcast.travel.domain.User;
import cn.itcast.travel.util.JDBCUtils;
import org.junit.Test;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class ErrTest {
    @Test
    public void test1(){
        String s = "d1a9a24dce3d45a78b1f47b58ae70006";
        JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
        String sql = "select * from tab_user where code = ?";
        User user = template.queryForObject(sql, new BeanPropertyRowMapper<User>(User.class), s);
        System.out.println(user.getUid());
    }
}
