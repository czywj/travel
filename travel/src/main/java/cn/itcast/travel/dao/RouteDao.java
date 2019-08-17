package cn.itcast.travel.dao;

import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;

import java.util.List;

public interface RouteDao {
     int findTotal(int cid,String rname);
     List<Route> findList(int cid, int start, int pagesize,String rname);
}
