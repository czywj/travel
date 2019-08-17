package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.RouteDao;
import cn.itcast.travel.dao.impl.RouteDaoImpl;
import cn.itcast.travel.domain.PageBean;
import cn.itcast.travel.domain.Route;
import cn.itcast.travel.service.RouteService;

import java.util.List;

public class RouteServiceImpl implements RouteService {
    private RouteDao dao = new RouteDaoImpl();
    @Override
    public PageBean<Route> queryList(int cid,int currentPage,int pageSize,String rname) {
        PageBean<Route> pageBean = new PageBean<>();
        int total = dao.findTotal(cid,rname);
        List<Route> list = dao.findList(cid, (currentPage - 1) * pageSize, pageSize,rname);
        int a = total%pageSize == 0 ? total / pageSize : (total / pageSize) + 1;
        pageBean.setTotalCount(total);
        pageBean.setCurrentPage(currentPage);
        pageBean.setPageSize(pageSize);
        pageBean.setTotalPage(a);
        pageBean.setList(list);
        return pageBean;
    }
}
