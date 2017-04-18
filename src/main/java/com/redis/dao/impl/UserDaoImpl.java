package com.redis.dao.impl;

import com.redis.dao.inf.UserDao;
import com.redis.domain.User;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.List;

/**
 * Created by redis on 2016/7/27.23:35
 * Describe:
 */
@Repository
public class UserDaoImpl implements UserDao {

    @Autowired
    private RedisTemplate<Serializable, Serializable> redisTemplate;
    //注入SqlSessionFactory
    @Autowired
    private SqlSessionFactory sqlSessionFactory;
//    //使用构造方法来初始化SqlSessionFactory
//    public UserDaoImpl(SqlSessionFactory sqlSessionFactory){
//        this.sqlSessionFactory = sqlSessionFactory;
//    }

    @Override
    public User findUserById(String userId) {
        //通过工厂，在方法内部获取SqlSession，这样就可以避免线程不安全
        SqlSession sqlSession = sqlSessionFactory.openSession();
        //返回结果集
        return sqlSession.selectOne("com.redis.dao.inf.UserMapper.findUserById", userId);
    }

    @Override
    public User findUsersByName(String username) {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectOne("com.redis.dao.inf.UserMapper.findUsersByName", username);
    }

    @Override
    public void insertUser(User user) {

        //通过工厂，在方法内部获取SqlSession，这样就可以避免线程不安全
        SqlSession sqlSession = sqlSessionFactory.openSession();
        sqlSession.insert("com.redis.dao.inf.UserMapper.insertUser", user);
    }

    @Override
    public List<User> getUserList() {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        return sqlSession.selectList("com.redis.dao.inf.UserMapper.getUserList");
    }


    public RedisTemplate<Serializable, Serializable> getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate<Serializable, Serializable> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

}
