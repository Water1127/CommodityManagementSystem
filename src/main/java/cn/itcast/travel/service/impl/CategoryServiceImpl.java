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

    /**
     * 查询所有
     * @return
     */
    @Override
    public List<Category> findAll() {
        /* 获取Jedis客服端 */
        Jedis jedis = JedisUtil.getJedis();
        /* 查询 sortedSet集合的值  */
//        Set<String> categorys = jedis.zrange("category", 0, -1);
        /* 查询 sortedSet集合中的值和分数  */
        Set<Tuple> categorys = jedis.zrangeWithScores("category", 0, -1);
        /* 创建List集合 */
        List<Category> all = null;
        /* 判断缓存中是否存储了数据 */
        if (categorys==null || categorys.size()==0){
            /* 缓存中有没数据，从数据库查询 */
             all = dao.findAll();
            System.out.println("从数据库查询");
            /* 并且将集合数据存储到缓存中 */
            for (Category category : all) {
                jedis.zadd("category",category.getCid(),category.getCname());
            }
        }else {
            /* 缓存中有数据，将缓存查询的set集合中的数据存入list集合 */
            all = new ArrayList<Category>();
            System.out.println("从缓存查询");
            for (Tuple tuple : categorys) {
                Category c = new Category();
                c.setCname(tuple.getElement());
                c.setCid((int)tuple.getScore());
                all.add(c);
            }
        }
        return all;
    }
}
