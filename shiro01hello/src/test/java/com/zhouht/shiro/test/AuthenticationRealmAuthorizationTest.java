package com.zhouht.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

import java.util.Arrays;

/**
 * Created by zhouht
 */
public class AuthenticationRealmAuthorizationTest {

    @Test
    public void testLoginAndLogout(){


        //创建Security工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-authorization-realm.ini");
        //创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        //将securityManager设置到shiro的运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建subject
        Subject subject = SecurityUtils.getSubject();

        //创建令牌
        UsernamePasswordToken token = new UsernamePasswordToken("zhouht", "123456");

        //执行用户认证
        subject.login(token);

        //判断是否登录成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("是否登录成功：" + authenticated);


        //校验授权操作===================
        //角色的判断
        boolean hasRole1 = subject.hasRole("role1");
        System.out.println(hasRole1);

        boolean[] booleans = subject.hasRoles(Arrays.asList("role1", "role2"));
        for (boolean aBoolean : booleans) {
            System.out.println(aBoolean);
        }

        boolean b = subject.hasAllRoles(Arrays.asList("role1", "role3"));
        System.out.println(b);

        subject.checkRole("role1");


        //资源的判断
        boolean permitted = subject.isPermitted("system:role:list");
        System.out.println(permitted);

    }
}
