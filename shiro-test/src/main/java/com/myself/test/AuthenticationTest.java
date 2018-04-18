package com.myself.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * Test Certification(测试认证)
 *
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:14 2018\4\17 0017
 */
public class AuthenticationTest {
    // SimpleAccountRealm Custom Authorization not supported
    // SimpleAccountRealm 不支持自定义授权
    SimpleAccountRealm simpleAccountRealm = new SimpleAccountRealm();

    @Before
    public void addUser(){
        simpleAccountRealm.addAccount("myself","123456","admin");
        // Multiple Role Assignment
        // 多角色赋值
        // simpleAccountRealm.addAccount("myself","123456","admin","user");
    }

    @Test
    public void testAuthentication(){
        // The first step is to build the SecurityManager environment
        // 第一步，构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(simpleAccountRealm);

        // In the second step, the subject submits the authentication request(Get the body from the tool class provided by Shiro)
        // 第二步，主体提交认证请求(通过Shiro提供的工具类获取主体)
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // Submit Certified Data
        // 提交认证数据
        UsernamePasswordToken token = new UsernamePasswordToken("myself","123456");
        subject.login(token);

        // Print certification Results
        // 打印认证结果
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        // Log out
        // 登出
//        subject.logout();
//
//        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        // Check to see if the user has Admin role permissions
        // 检查用户是否具备admin角色权限
        subject.checkRole("admin");
        // Multiple role Checks
        // 多角色检查
//        subject.checkRoles("admin","user");

    }
}
