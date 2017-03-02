package com.zhangzhaowen.dao.inf;

import com.zhangzhaowen.domain.User;

import java.util.List;

/**
 * Created by zhangzhaowen on 2016/7/27.23:33
 * Describe:
 */
public interface UserDao {
    //根据用户ID来查询用户信息
   User findUserById(String userId);
    //根据用户名称来模糊查询用户信息列表
   User findUsersByName(String username);
    //添加用户
   void insertUser(User user);

    /*
    * created by zhangzhaowen on 2017/2/24
    * description
    * @param
    */
    List<User> getUserList();

}
