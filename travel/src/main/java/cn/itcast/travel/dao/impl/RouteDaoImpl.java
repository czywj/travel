package cn.itcast.travel.dao.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.util.JDBCUtils;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class RouteDaoImpl implements RouteDao {
    private JdbcTemplate template = new JdbcTemplate(JDBCUtils.getDataSource());
    @Override
    public int findTotal(int cid,String rname) {
        //String sql = "select count(*) from tab_route where cid = ?";
        String sql = "select count(*) from tab_route where 1=1 ";
        StringBuilder builder = new StringBuilder(sql);
        List list = new ArrayList();
        if(cid != 0){
            builder.append(" and cid = ? ");
            list.add(cid);
        }
        if (rname != null && rname.length() > 0){
            builder.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        sql = builder.toString();
        return template.queryForObject(sql,Integer.class,list.toArray());

    }

    @Override
    public List<Route> findList(int cid, int start, int pagesize, String rname) {
        //String sql = "select * from tab_route where cid = ? limit ? , ?";

        String sql = " select * from tab_route where 1=1 ";
        StringBuilder builder = new StringBuilder(sql);
        List list = new ArrayList();
        if(cid != 0){
            builder.append(" and cid = ? ");
            list.add(cid);
        }
        if (rname != null && rname.length() > 0){
            builder.append(" and rname like ? ");
            list.add("%"+rname+"%");
        }
        builder.append(" limit ? , ? ");
        list.add(start);
        list.add(pagesize);
        sql = builder.toString();
        return template.query(sql,new BeanPropertyRowMapper<Route>(Route.class),list.toArray());
    }
}
