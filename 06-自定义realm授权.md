**1、ini中配置realm**

shiro-realm-authorization.ini

```
[main]
#自定义 realm
customRealm=com.zhouht.shirodemo.realm.CustomRealm2
#将realm设置到securityManager，相当 于spring中注入
securityManager.realms=$customRealm
```

**2、自定义realm**

从认证的realm拷贝，改变继承的抽象父类，添加新的方法

为了测试方便，可以定和上一节的例子中的ini文件一致的权限和角色信息

```
package com.zhouht.shirodemo.realm;

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
public class CustomRealm2 extends AuthorizingRealm {

    /**
     * 用于认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //参考CustomRealm,复制过来
    }
    /**
     * 用于授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
            PrincipalCollection principals) {
        //从 principals获取主身份信息
        //将getPrimaryPrincipal方法返回值转为真实身份类型
        // （在上边的doGetAuthenticationInfo认证通过并填充到SimpleAuthenticationInfo中的身份类型），
        String userCode = (String) principals.getPrimaryPrincipal();
        System.out.println("用户信息：" + userCode);

        //模拟从数据库获取到权限数据
        List<String> permissions = new ArrayList<String>();
        permissions.add("system:user:*");//用户管理的创建
        permissions.add("system:menu:*");//菜单管理的权限
        permissions.add("system:config:*");//系统配置的权限

        //模拟从数据库获取到角色数据
        List<String> roles = new ArrayList<String>();
        roles.add("role1");
        roles.add("role3");

        //初始化授权对象
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        //授权权限
        simpleAuthorizationInfo.addStringPermissions(permissions);
        //授权角色
        simpleAuthorizationInfo.addRoles(roles);

        return simpleAuthorizationInfo;
    }
}
```

**3、测试**

直接可以使用 AuthorizationTest 测试

注意：测试时会发现，每次进行权限和角色的比对时都会执行授权的代码，效率低。

```
// 创建SecurityManager工厂
//（v1）ini
//IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-authorization.ini");
//（v2）自定义
IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-realm-authorization.ini");
```

