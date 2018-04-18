package com.myself.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.SimpleAccountRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Before;
import org.junit.Test;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:25 2018\4\17 0017
 */
public class IniRealmTest {

    @Test
    public void testAuthentication(){
        // Create a new file under the resource file and configure the user's basic information
        // 在资源文件下新建对应文件，配置用户基本信息
        IniRealm iniRealm = new IniRealm("classpath:user.ini");

        // The first step is to build the SecurityManager environment
        // 第一步，构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        // In the second step, the subject submits the authentication request(Get the body from the tool class provided by Shiro)
        // 第二步，主体提交认证请求(通过Shiro提供的工具类获取主体)
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        // Submit Certified Data
        // 提交认证数据
        UsernamePasswordToken token = new UsernamePasswordToken("Myself","123456");
        subject.login(token);

        // Print certification Results
        // 打印认证结果
        System.out.println("isAuthenticated: " + subject.isAuthenticated());

        subject.checkRole("admin");

        // Whether the user has permission to delete
        // 用户是否具备删除权限
        subject.checkPermission("user:delete");
    }
}
