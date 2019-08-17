package cn.itcast.travel.service.impl;

import cn.itcast.travel.dao.CategoryDao;
import cn.itcast.travel.dao.impl.CategoryDaoImpl;
import cn.itcast.travel.domain.Category;
import cn.itcast.travel.service.CategoryService;
import cn.itcast.travel.util.JedisUtil;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Tuple;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class CategoryServiceImpl implements CategoryService {
    private CategoryDao dao = new CategoryDaoImpl();
    @Override
    public List<Category> findAll() {
        //1.从redis中查询
        Jedis jedis = JedisUtil.getJedis();
        //使用sortedset排序查询
        //Set<String> category = jedis.zrange("category", 0, -1);
        Set<Tuple> category = jedis.zrangeWithScores("category", 0, -1);
        //判断查询的集合是否为null
        List<Category> cs = null;
        if(category == null || category.size() == 0){
            //为空查询数据库把数据存储到redis中
             cs = dao.findAll();
            for (int i = 0; i < cs.size() ; i++) {
                jedis.zadd("category",cs.get(i).getCid(),cs.get(i).getCname());
            }

        }else {
            cs = new ArrayList<Category>();

            for (Tuple n : category) {
                Category c = new Category();
                c.setCname(n.getElement());
                c.setCid((int)n.getScore());
                cs.add(c);
            }
        }

        return cs;
    }
}
