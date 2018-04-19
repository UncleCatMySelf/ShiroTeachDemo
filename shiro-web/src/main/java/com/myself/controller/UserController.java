package com.myself.controller;

import com.myself.vo.User;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Author:UncleCatMySelf
 * @Email：zhupeijie_java@126.com
 * @QQ:1341933031
 * @Date:Created in 18:17 2018\4\17 0017
 */
@Controller
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @RequestMapping(value = "/subLogin",method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    public String subLogin(User user){
        logger.info("进入subLogin");
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(user.getUsername(),user.getPassword());
        try {
            if (user.getRememberMe() == null){
                user.setRememberMe(false);
            }
            token.setRememberMe(user.getRememberMe());
            subject.login(token);
        } catch (AuthenticationException e){
            return e.getMessage();
        }
        if (subject.hasRole("admin")){
            return "Have Admin permissions";
        }
        return "No Admin permissions";
    }

    /**
     * This note indicates that the current principal must have admin permission to access this API
     * Of course you can also use @Requirespermissions(XXX)
     * 此注解表明当前主体必须具备admin权限才能访问此api
     * 当然你也可以使用@RequiresPermissions(XXX)
     * @return
     */
    //@RequiresRoles("admin")
    @RequestMapping(value = "/testRole", method = RequestMethod.GET)
    @ResponseBody
    public String testRole(){
        logger.info("testRole");
        return "testRole SUCCESS";
    }


    //@RequiresRoles("admin1")
    @RequestMapping(value = "/testRole1", method = RequestMethod.GET)
    @ResponseBody
    public String testRole1(){
        logger.info("testRole1");
        return "testRole1 SUCCESS";
    }


    @RequestMapping(value = "/testPerms", method = RequestMethod.GET)
    @ResponseBody
    public String testPerms(){
        return "testPerms SUCCESS";
    }


    @RequestMapping(value = "/testPerms1", method = RequestMethod.GET)
    @ResponseBody
    public String testPerms1(){
        return "testPerms1 SUCCESS";
    }

}
