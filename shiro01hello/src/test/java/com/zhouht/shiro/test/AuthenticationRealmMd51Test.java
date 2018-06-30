package com.zhouht.shiro.test;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * Created by zhouht
 */
public class AuthenticationRealmMd51Test {

    @Test
    public void testLoginAndLogout(){


        //创建Security工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-realm-md51.ini");
        //创建SecurityManager对象
        SecurityManager securityManager = factory.getInstance();
        //将securityManager设置到shiro的运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        //创建subject
        Subject subject = SecurityUtils.getSubject();

        //创建令牌
        String inputPassword = new SimpleHash("md5", "123456", "zhouht", 1024).toString();
        UsernamePasswordToken token = new UsernamePasswordToken("zhouht", inputPassword);

        //执行用户认证
        subject.login(token);

        //判断是否登录成功
        boolean authenticated = subject.isAuthenticated();
        System.out.println("是否登录成功：" + authenticated);


        //退出操作
        subject.logout();

        authenticated = subject.isAuthenticated();
        System.out.println("是否登录成功：" + authenticated);

    }
}
