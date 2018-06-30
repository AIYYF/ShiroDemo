package com.zhouht.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthenticatingRealm;
import org.apache.shiro.util.ByteSource;

/**
 * Created by zhouht
 */
public class CustomRealmMd5  extends AuthenticatingRealm {
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {


        //从数据库中获取用户名和密码
        String username = "zhouht";
        String password = "0f7f003c95af85ffba585646aa0d5033";

        //UsernamePasswordToken token = (UsernamePasswordToken)authenticationToken;
        //token.get
        String inputUsername = (String)authenticationToken.getPrincipal();
        if(!inputUsername.equals(username)){
            throw new UnknownAccountException("用户不存在");
        }

        String salt = username;

        //String inputPassword = (String)authenticationToken.getCredentials();//TODO
        String inputPassword = new String((char[])authenticationToken.getCredentials());

        inputPassword = new SimpleHash("md5", inputPassword, salt, 1024).toString();
        if(!inputPassword.equals(password)){
            throw new IncorrectCredentialsException("密码不正确");
        }


        ByteSource byteSource = ByteSource.Util.bytes(salt);
        String realName = this.getName();
        //System.out.println(realName);
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, byteSource, realName);

        return info;
    }
}
