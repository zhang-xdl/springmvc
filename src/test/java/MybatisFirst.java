import com.redis.dao.impl.UserDaoImpl;
import com.redis.domain.User;
import com.redis.util.CacheUtils;
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

import java.util.*;
import java.util.concurrent.*;

/**
 * Created by redis on 2016/7/26.23:28
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
        ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
        List<User> userList = UserDaoImpl.getUserList();
        redisTemplate.delete("userList");
        if(userList!=null &&userList.size()>0){
            for(User user : userList){
//                System.out.println(user);
                boolean flag = zSetOperations.add("userList", user, Double.valueOf(user.getId()));
                System.out.println("-------"+flag);
            }
            System.out.println("redis-----------------------------------");
            Set<Object> userSet=zSetOperations.rangeByScore("userList", 0, 10);
            System.out.println(userSet);
            System.out.println("倒序-----------------------------------");
            Set<Object> userSet1=zSetOperations.reverseRangeByScore("userList", 0, 10);
            System.out.println(userSet1);
        }
    }


    @Test
    public void testRedisZet(){
        ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
        System.out.println("倒序-----------------------------------");
        Set<Object> userSet1=zSetOperations.reverseRangeByScore("userList", 0, 13);
        System.out.println(userSet1);
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



    @Test
    public void cas()  {
        redisTemplate.setValueSerializer(new StringRedisSerializer());
        List<Object> txResults = redisTemplate.execute(new SessionCallback<List<Object>>() {
            public List<Object> execute(RedisOperations operations) throws DataAccessException {
                operations.multi();
                operations.opsForValue().set("test_long", "1");
//                if(true){
//                    throw new RuntimeException();
//                }
                operations.opsForValue().increment("test_long", 1);
                // This will contain the results of all ops in the transaction
                return operations.exec();
            }
        });
        System.out.println(txResults.size());
        System.out.println("Number of items added to set: " + txResults.get(0));
//        System.out.println("Number of items added to set: " + txResults.get(0));
//        final String key = "test-cas-1";
//        ValueOperations<String, Object> strOps = redisTemplate.opsForValue();
//        redisTemplate.setEnableTransactionSupport(true);
//        strOps.set(key, "hello");
//        ExecutorService pool  = Executors.newCachedThreadPool();
//        List<Callable<Object>> tasks = new ArrayList<>();
//        for(int i=0;i<5;i++){
//            final int idx = i;
//            tasks.add(new Callable() {
//                @Override
//                public Object call() throws Exception {
//                    return redisTemplate.execute(new SessionCallback() {
//                        @Override
//                        public Object execute(RedisOperations operations) throws DataAccessException {
//                            operations.watch(key);
//                            String origin = (String) operations.opsForValue().get(key);
//                            operations.multi();
//                            operations.opsForValue().set(key, origin + idx);
//                            Object rs = operations.exec();
//                            System.out.println("set:"+origin+idx+" rs:"+rs);
//                            return rs;
//                        }
//                    });
//                }
//            });
//        }
//        List<Future<Object>> futures = pool.invokeAll(tasks);
//        for(Future<Object> f:futures){
//            System.out.println(f.get());
//        }
//        pool.shutdown();
//        pool.awaitTermination(1000, TimeUnit.MILLISECONDS);
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
