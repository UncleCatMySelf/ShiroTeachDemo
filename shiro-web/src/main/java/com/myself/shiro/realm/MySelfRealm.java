package com.myself.shiro.realm;

import com.myself.dao.UserDao;
import com.myself.vo.User;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Resource;
import java.util.*;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 16:27 2018\4\17 0017
 */
public class MySelfRealm extends AuthorizingRealm{

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private UserDao userDao;

    // Authorization Abstraction method
    // 授权抽象方法
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        String userName = (String) principalCollection.getPrimaryPrincipal();
        // Get role data from a database or cache
        // 从数据库或缓存中获取角色数据
        Set<String> roles = getRolesByUserName(userName);
        Set<String> permissions = getPremissionsByUserName(userName);

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.setRoles(roles);
        simpleAuthorizationInfo.setStringPermissions(permissions);

        return simpleAuthorizationInfo;
    }

    private Set<String> getPremissionsByUserName(String userName) {
        Set<String> sets = new HashSet<String>();
        sets.add("user:delete");
        sets.add("user:add");
        return sets;
    }

    /**
     * Simulate cache read User role data
     * 模拟缓存读取用户角色数据
     * @param userName
     * @return
     */
    private Set<String> getRolesByUserName(String userName) {
        logger.info("Reading permission data from a database");
        logger.info("从数据库读取权限数据");
        List<String> list = userDao.queryRolesByUserName(userName);
        Set<String> sets = new HashSet<String>(list);
        return sets;
    }

    // Authentication abstract Methods
    // 认证抽象方法
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // 1, from the main body passed the authentication information, get the username
        // 1、从主体传过来的认证信息中，获得用户名
        String userName = (String) authenticationToken.getPrincipal();

        // 2, through the user name to the database to obtain vouchers
        // 2、通过用户名到数据库中获取凭证
        String password = getPasswordByUserName(userName);
        if (password == null){
            return null;
        }
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo
                (userName,password,"MySelfRealm");
        authenticationInfo.setCredentialsSalt(ByteSource.Util.bytes(userName));
        return authenticationInfo;
    }

    /**
     * Mock database Query voucher
     * 模拟数据库查询凭证
     * @param userName
     * @return
     */
    private String getPasswordByUserName(String userName) {
        User user = userDao.getUserByUserName(userName);
        if (user != null){
            return user.getPassword();
        }
        return null;
    }
}
