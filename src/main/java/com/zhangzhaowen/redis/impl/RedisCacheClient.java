package com.zhangzhaowen.redis.impl;

import com.zhangzhaowen.redis.inf.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Created by zhangzhaowen on 2017/2/24.
 * Description
 */
public class RedisCacheClient implements RedisCache{

    @Autowired
    private RedisTemplate<String, Object> redisTemplate ;


    @Override
    public boolean add(String key, Object value) {
        return false;
    }

    @Override
    public boolean add(String key, Object value, int seconds) {
        return false;
    }

    @Override
    public Object get(String key) {
        return null;
    }

    @Override
    public long delete(String key) {
        return 0;
    }

    @Override
    public boolean exists(String key) {
        return false;
    }


    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }
}
