package com.zhouht.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * Created by zhouht
 */
public class CustomRealm extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        //从数据库中获取用户名和密码
        String username = "zhouht";
        String password = "123456";

        //UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //token.get
        String inputUsername = (String)authenticationToken.getPrincipal();
        if(!inputUsername.equals(username)){
            throw new UnknownAccountException("用户不存在");
        }

        //String inputPassword = (String)authenticationToken.getCredentials();//TODO
        String inputPassword = new String((char[])authenticationToken.getCredentials());
        if(!inputPassword.equals(password)){
            throw new IncorrectCredentialsException("密码不正确");
        }

        String realName = this.getName();
        System.out.println(realName);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, realName);

        return info;
    }
}
