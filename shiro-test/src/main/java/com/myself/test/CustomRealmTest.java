package com.myself.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ByteSource;
import org.junit.Before;
import org.junit.Test;

/**
 * 先使用main方法计算出加密值，赋值给addUser方法，再进行加密、盐测试
 * The main method is used to calculate the encryption value, assign the value to the AddUser method,
 * and then encrypt and salt test.
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:23 2018\4\17 0017
 */
public class CustomRealmTest {

    // CustomRealm Custom Dogetauthenticationinfo () 157 line Add Salt field definition
    // CustomRealm 自定义doGetAuthenticationInfo（）157行 加入盐字段定义
    CustomRealm customRealm = new CustomRealm();

    @Before
    public void addUser(){
        customRealm.addAccount("myself","1a5c371af54e05096421312d605147e7","admin");
    }

    @Test
    public void testAuthentication(){

        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        // 设置加密类型
        matcher.setHashAlgorithmName("md5");
        // 设置加密次数
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);


        // The first step is to build the SecurityManager environment
        // 第一步，构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(customRealm);

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

    }

    public static void main(String[] args) {
        // md5加密与盐使用
        Md5Hash md5Hash = new Md5Hash("123456","MySelf");
        System.out.println(md5Hash.toString());
    }

}
