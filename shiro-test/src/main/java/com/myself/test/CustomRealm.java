package com.myself.test;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleRole;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.apache.shiro.util.CollectionUtils;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 13:46 2018\4\17 0017
 */
public class CustomRealm extends AuthorizingRealm {

    protected final Map<String, SimpleAccount> users;
    protected final Map<String, SimpleRole> roles;
    protected final ReadWriteLock USERS_LOCK;
    protected final ReadWriteLock ROLES_LOCK;

    public CustomRealm() {
        this.users = new LinkedHashMap();
        this.roles = new LinkedHashMap();
        this.USERS_LOCK = new ReentrantReadWriteLock();
        this.ROLES_LOCK = new ReentrantReadWriteLock();
        this.setCachingEnabled(false);
    }

    public CustomRealm(String name) {
        this();
        this.setName(name);
    }

    protected SimpleAccount getUser(String username) {
        this.USERS_LOCK.readLock().lock();

        SimpleAccount var2;
        try {
            var2 = (SimpleAccount)this.users.get(username);
        } finally {
            this.USERS_LOCK.readLock().unlock();
        }

        return var2;
    }

    public boolean accountExists(String username) {
        return this.getUser(username) != null;
    }

    public void addAccount(String username, String password) {
        this.addAccount(username, password, (String[])null);
    }

    public void addAccount(String username, String password, String... roles) {
        Set<String> roleNames = CollectionUtils.asSet(roles);
        SimpleAccount account = new SimpleAccount(username, password, this.getName(), roleNames, (Set)null);
        this.add(account);
    }

    protected String getUsername(SimpleAccount account) {
        return this.getUsername(account.getPrincipals());
    }

    protected String getUsername(PrincipalCollection principals) {
        return this.getAvailablePrincipal(principals).toString();
    }

    protected void add(SimpleAccount account) {
        String username = this.getUsername(account);
        this.USERS_LOCK.writeLock().lock();

        try {
            this.users.put(username, account);
        } finally {
            this.USERS_LOCK.writeLock().unlock();
        }

    }

    protected SimpleRole getRole(String rolename) {
        this.ROLES_LOCK.readLock().lock();

        SimpleRole var2;
        try {
            var2 = (SimpleRole)this.roles.get(rolename);
        } finally {
            this.ROLES_LOCK.readLock().unlock();
        }

        return var2;
    }

    public boolean roleExists(String name) {
        return this.getRole(name) != null;
    }

    public void addRole(String name) {
        this.add(new SimpleRole(name));
    }

    protected void add(SimpleRole role) {
        this.ROLES_LOCK.writeLock().lock();

        try {
            this.roles.put(role.getName(), role);
        } finally {
            this.ROLES_LOCK.writeLock().unlock();
        }

    }

    protected static Set<String> toSet(String delimited, String delimiter) {
        if (delimited != null && !delimited.trim().equals("")) {
            Set<String> values = new HashSet();
            String[] rolenamesArray = delimited.split(delimiter);
            String[] var4 = rolenamesArray;
            int var5 = rolenamesArray.length;

            for(int var6 = 0; var6 < var5; ++var6) {
                String s = var4[var6];
                String trimmed = s.trim();
                if (trimmed.length() > 0) {
                    values.add(trimmed);
                }
            }

            return values;
        } else {
            return null;
        }
    }

    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws NullPointerException , AuthenticationException{
        UsernamePasswordToken upToken = (UsernamePasswordToken)token;
        SimpleAccount account = this.getUser(upToken.getUsername());
        if (account != null) {
            if (account.isLocked()) {
                throw new LockedAccountException("Account [" + account + "] is locked.");
            }

            if (account.isCredentialsExpired()) {
                String msg = "The credentials for account [" + account + "] are expired";
                throw new ExpiredCredentialsException(msg);
            }
        }
        account.setCredentialsSalt(ByteSource.Util.bytes("MySelf"));
        return account;
    }

    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String username = this.getUsername(principals);
        this.USERS_LOCK.readLock().lock();

        AuthorizationInfo var3;
        try {
            var3 = (AuthorizationInfo)this.users.get(username);
        } finally {
            this.USERS_LOCK.readLock().unlock();
        }

        return var3;
    }

}
