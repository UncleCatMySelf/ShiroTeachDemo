package com.myself.test;

import com.myself.shiro.realm.MySelfRealm;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:39 2018\4\17 0017
 */
public class MySelfRealmTest {

    @Test
    public void testAuthentication(){
        MySelfRealm mySelfRealm = new MySelfRealm();

        // The first step is to build the SecurityManager environment
        // 第一步，构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(mySelfRealm);

        // In the second step, the subject submits the authentication request(Get the body from the tool class provided by Shiro)
        // 第二步，主体提交认证请求(通过Shiro提供的工具类获取主体)
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // Submit Certified Data
        // 提交认证数据
        UsernamePasswordToken token = new UsernamePasswordToken("MySelf","123456");
        subject.login(token);

        // Print certification Results
        // 打印认证结果
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");

        subject.checkPermissions("user:delete","user:add");
    }

}
