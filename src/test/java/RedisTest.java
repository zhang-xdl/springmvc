import com.redis.dao.impl.UserDaoImpl;
import com.redis.domain.User;
import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.Set;

/**
 * Created by zhangzhaowen on 2017/4/18.
 * Description
 */

@RunWith(SpringJUnit4ClassRunner.class)
//@ContextConfiguration(locations={"classpath:spring/applicationContext.xml", "classpath:spring/mvc-dispatcher-servlet.xml"})
@ContextConfiguration(locations={"file:src/main/webapp/WEB-INF/applicationContext.xml"})
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate ;
    @Autowired
    private SqlSessionFactory sqlSessionFactory ;
    @Autowired
    private  UserDaoImpl UserDaoImpl;

    @Test
    public void testRedisZet(){
        ZSetOperations<String,Object> zSetOperations = redisTemplate.opsForZSet();
        System.out.println("倒序-----------------------------------");
        Set<Object> userSet1=zSetOperations.reverseRangeByScore("userList", 0, 13);
        System.out.println(userSet1);
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

    /*
    * created by zhangzhaowen on 2017/4/18
    * description redis 向左侧添加（头）List 获得数据倒叙
    * @param
    */
    @Test
    public void testRedisList(){
        List<User> userList = UserDaoImpl.getUserList();
        ListOperations listOperations = redisTemplate.opsForList();
        Long result = listOperations.leftPushAll("List", userList);
        System.out.println("result"+ result);
        System.out.println("List size is " +listOperations.size("List"));
//        Object list =  listOperations.leftPop("List");
//        System.out.println("List size is " +listOperations.size("List"));
        Long litSize =  listOperations.size("List");
        User  u = new User();
        while(litSize>0){
            u =  (User)listOperations.leftPop("List");
            litSize =  listOperations.size("List");
            System.out.println(u);
        }
        System.out.println(listOperations.size("List"));
    }

    /*
    * created by zhangzhaowen on 2017/4/18
    * description rightPush 以list形式存，rightPushAll 是以单个形式存;
    * @param
    */
    @Test
    public void testRedisListRight(){
        List<User> userList = UserDaoImpl.getUserList();
        ListOperations listOperations = redisTemplate.opsForList();
        Long result = listOperations.rightPushAll("List", userList);
        System.out.println("result:"+ result);
        System.out.println("List size is " +listOperations.size("List"));
//        Object list =  listOperations.leftPop("List");
//        System.out.println("List size is " +listOperations.size("List"));
        Long litSize =  listOperations.size("List");
        User  u = new User();
        while(litSize>0){
            u =  (User)listOperations.leftPop("List");
            litSize =  listOperations.size("List");
            System.out.println(u);
        }
        System.out.println(listOperations.size("List"));


        System.out.println("rightPush method................");
        result = listOperations.rightPush("List", userList);
        System.out.println("result:"+ result);
        System.out.println("List size is " +listOperations.size("List"));
        List<User> userList1 = (List<User>) listOperations.leftPop("List");
        System.out.println(userList1);
        System.out.println("List size is " +listOperations.size("List"));
        //
    }


    /*
    * created by zhangzhaowen on 2017/4/18
    * description  listOperations.range("List",0,8) 一共9 个
    * @param
    */
    @Test
    public void testRedisGetListAndNotDelete(){
        List<User> userList = UserDaoImpl.getUserList();
        ListOperations listOperations = redisTemplate.opsForList();
        Long result = listOperations.rightPushAll("List", userList);
        System.out.println("result:"+ result);
        Long size = listOperations.size("List");
        System.out.println("List size is " + size);

        List<User> userList1 = listOperations.range("List",0,-1);
        System.out.println(userList1);
        size = listOperations.size("List");
        System.out.println("List size is " + size);
    }

}
