import com.zhangzhaowen.dao.impl.UserDaoImpl;
import com.zhangzhaowen.domain.User;
import com.zhangzhaowen.util.CacheUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by zhangzhaowen on 2016/7/26.23:28
 * Describe:
 */
@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring/applicationContext.xml", "classpath:spring/mvc-dispatcher-servlet.xml"})
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class MybatisFirst {
    @Autowired
    private  SqlSessionFactory sqlSessionFactory ;
    @Autowired
    private UserDaoImpl UserDaoImpl;

//    ClassPathXmlApplicationContext appCtx = new ClassPathXmlApplicationContext("applicationContext.xml");
    @Autowired
    private RedisTemplate<String, Object> redisTemplate ;
    @Test
    public void testinsertUser() throws Exception {
        SqlSession sqlSession = sqlSessionFactory.openSession();
        User user = new User();
        user.setCreateName("zhangsan");
        user.setMobilePhone("12526547560");
        user.setORGANID("6");
        user.setPassWord("1111");
        user.setUserName("123");
        user.setRealName("小七");
        user.setUserType("2");
        sqlSession.insert("insertUser", user);
        sqlSession.commit();
        sqlSession.close();

    }

    @Test
    public void testFindUserById() {
        User user = UserDaoImpl.findUserById("1");
        System.out.println();
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");
        System.out.println(user.getId());
        System.out.println(user.getEmail());
        System.out.println(user.getCreateName());
        System.out.println(user.getMobilePhone());
        System.out.println(user.getPassWord());
        System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>");

        System.out.println("redis-----------------------------------");
        ValueOperations<String, Object> value = redisTemplate.opsForValue();
        value.set("1", user);
        System.out.println(value.get("1"));
    }

    @Test
    public void testGetUserList() {
        List<User> userList = UserDaoImpl.getUserList();
        if(userList!=null &&userList.size()>0){
            for(User user : userList){
                System.out.println(user);
            }
            System.out.println("redis-----------------------------------");
            ValueOperations<String, Object> value = redisTemplate.opsForValue();
            value.set("userList", userList);
            List<Object> objectList = (List<Object>) value.get("userList");
            for (Object o : objectList){
                System.out.println(o);
            }

        }
    }

    @Test
    public void testMap() {
        //-----添加数据----------
        Map<String, String> map = new HashMap<String, String>();
        HashOperations<String, Object, Object> hashOperations = redisTemplate.opsForHash();
        map.put("name", "xinxin");
        map.put("age", "22");
        map.put("qq", "123456");
        hashOperations.putAll("map1",map);
        //取出user中的name，执行结果:[minxr]-->注意结果是一个泛型的List
        //第一个参数是存入redis中map对象的key，后面跟的是放入map中的对象的key，后面的key可以跟多个，是可变参数
        System.out.println(hashOperations.entries("map1"));
        Map mapGetFromRedis =hashOperations.entries("map1");
        Iterator<Map.Entry<Integer, String>> it = mapGetFromRedis.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<Integer, String> entry = it.next();
            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
        }

//        System.out.println(redisTemplate.boundHashOps("map1").toString());
    }
    @Test
    public void testIncrement() throws InterruptedException {
        //-----添加数据----------
//        redisTemplate.setValueSerializer(new StringRedisSerializer());
        ValueOperations<String, Object> value =redisTemplate.opsForValue();
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        long num = 20L;
        //必须是字符串类型
        value.set("1", String.valueOf(num));
        System.out.println(value.get("1"));
        value.increment("1", 2);
        System.out.println(value.get("1"));
        System.out.println(Double.valueOf(value.get("1").toString()));
        redisTemplate.expire("1", 5, TimeUnit.SECONDS);
        Thread.sleep(2000);
        System.out.println("2SEN 后" + value.get("1"));
        redisTemplate.delete("2");
        System.out.println("delete 2 " + value.get("1"));
        redisTemplate.delete("1");
        System.out.println("delete 1 " + value.get("1"));
        BoundValueOperations<String, Object> boundValueOps = redisTemplate.boundValueOps("2");
        System.out.println("boundValueOps-----------" );
        boundValueOps.set("22222222222222");
        System.out.println(boundValueOps.get());



    }
//    @Test
    public long getIncrValue(final String key) {

        return redisTemplate.execute(new RedisCallback<Long>() {
            @Override
            public Long doInRedis(RedisConnection connection) throws DataAccessException {
                RedisSerializer<String> serializer=redisTemplate.getStringSerializer();
                byte[] rowkey=serializer.serialize(key);
                byte[] rowval=connection.get(rowkey);
                try {
                    String val=serializer.deserialize(rowval);
                    return Long.parseLong(val);
                } catch (Exception e) {
                    return 0L;
                }
            }
        });
    }
}
