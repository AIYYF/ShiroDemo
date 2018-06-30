package com.zhouht.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhouht
 */
public class CustomAuthorizationRealm extends AuthorizingRealm {

    /**
     * 认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
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


    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        System.out.println("授权。。。。。。。。。。。。。。。。。。。。。。。。。。");

        String username = (String)principalCollection.getPrimaryPrincipal();
        System.out.println("用户信息" + username);


        List<String> roleList = new ArrayList<String>();
        roleList.add("role1");
        roleList.add("role3");

        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        simpleAuthorizationInfo.addRoles(roleList);

        return simpleAuthorizationInfo;
    }

}
