package com.myself.test;

import com.alibaba.druid.pool.DruidDataSource;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 14:47 2018\4\17 0017
 */
public class JdbcRealmTest {

    DruidDataSource dataSource = new DruidDataSource();

    {
        dataSource.setUrl("jdbc:mysql://localhost:3306/test");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testAuthentication(){

        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        // To set the permission switch when using Jdbcrealm, the default is False
        // 使用JdbcRealm时要设置权限开关，默认为false
        jdbcRealm.setPermissionsLookupEnabled(true);

        // customizing SQL Tables
        // 自定义sql表
        String sql = "select password from test_users where user_name = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        String roleSql = "select role_name from test_user_roles where user_name = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        // The first step is to build the SecurityManager environment
        // 第一步，构建SecurityManager环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(jdbcRealm);

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

        subject.checkPermission("user:update");
    }
}
