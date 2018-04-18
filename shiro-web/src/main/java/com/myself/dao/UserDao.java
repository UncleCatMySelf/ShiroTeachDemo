package com.myself.dao;

import com.myself.vo.User;

import java.util.List;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 10:42 2018\4\18 0018
 */
public interface UserDao {
    User getUserByUserName(String userName);

    List<String> queryRolesByUserName(String userName);
}
