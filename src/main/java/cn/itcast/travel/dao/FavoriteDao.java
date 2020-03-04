package cn.itcast.travel.dao;

import cn.itcast.travel.domain.Favorite;

public interface FavoriteDao {

    /**
     * 根据 rid 和 uid 查询Favorite对象
     * @param rid
     * @param uid
     * @return
     */
    public Favorite isFavorite(int rid , int uid);

    /**
     * 根据 rid  查询收藏次数
      * @param rid
     * @return
     */
    int findCountByRid(int rid);

    /**
     * 添加收藏
     * @param parseInt
     * @param uid
     */
    void add(int parseInt, int uid);
}
