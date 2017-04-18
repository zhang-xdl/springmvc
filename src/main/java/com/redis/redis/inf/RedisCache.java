package com.redis.redis.inf;

/**
 * Created by redis on 2017/2/24.
 * Description
 */
public interface RedisCache {

    /**
     * @Title: add
     * @Description: 添加一个缓冲数据
     * @param key 字符串的缓存key
     * @param value 缓冲的缓存数据
     * @return
     * @author 
     */
    boolean add(String key, Object value);

    /**
     * @Title: add
     * @Description: 缓存一个数据，并指定缓存过期时间
     * @param key
     * @param value
     * @param seconds
     * @return
     * @author
     */
    boolean add(String key, Object value, int seconds);

    /**
     * @Title: get
     * @Description: 根据key获取到一直值
     * @param key 字符串的缓存key
     * @return
     * @author
     */
    Object get(String key);

    /**
     * @Title: delete
     * @Description: 删除一个数据问题
     * @param key 字符串的缓存key
     * @return
     * @author
     */
    long delete(String key);

    /**
     * @Title: exists
     * @Description: 判断指定key是否在缓存中已经存在
     * @param key 字符串的缓存key
     * @return
     * @author
     */
    boolean exists(String key);

}
