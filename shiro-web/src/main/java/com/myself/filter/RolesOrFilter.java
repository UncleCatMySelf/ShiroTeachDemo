package com.myself.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.AuthorizationFilter;

/**
 * Custom filters to access content pages with one of the permissions
 * 自定义过滤器，满足权限之一即可访问内容页面
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 11:29 2018\4\18 0018
 */
public class RolesOrFilter extends AuthorizationFilter{

    protected boolean isAccessAllowed(javax.servlet.ServletRequest servletRequest,
                                      javax.servlet.ServletResponse servletResponse, Object o) throws Exception {
        Subject subject = getSubject(servletRequest,servletResponse);
        String[] roles = (String[]) o;
        if (roles == null || roles.length == 0){
            return true;
        }
        for (String role : roles){
            if (subject.hasRole(role)){
                return true;
            }
        }
        return false;
    }
}
