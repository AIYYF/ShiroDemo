# **1、数据源配置：ini**

shiro-realm.ini 中指定自定义数据源

```
[main]
#自定义 realm
customRealm=com.zhouht.shirodemo.realm.CustomRealm
#将realm设置到securityManager，相当 于spring中注入
securityManager.realms=$customRealm
```

## 2、编写自定义数据源类

```
package com.zhouht.shirodemo.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.AuthenticatingRealm;

/**
 * Created by zhouht
 */
public class CustomRealm extends AuthenticatingRealm{

    /**
     * 用于认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {

        //已知的正确的用户名和密码
        String username = "zhouht";
        String password = "123456";

        //获取用户输入：用户名
        String inputUsername = (String)authenticationToken.getPrincipal();
        if(!inputUsername.equals(username)){
            throw new UnknownAccountException("用户不存在");
        }

        //获取用户输入：密码（用户凭证，如果这里不判断，那么会由后面的SimpleAuthenticationInfo的构造方法进行自动认证）
        String inputPassword = new String((char[]) authenticationToken.getCredentials());
        if(!inputPassword.equals(password)) {
            throw new IncorrectCredentialsException("密码不正确");
        }

        //获取用认证信息
        //注意：（1）username处可以传递任何正确的用户信息的形式，例如：用户名，用户对象等，主要是为了后续的用户授权中可以将这个值取出来，
        //      作进一步的信息获取或处理
        //      （2）password处传递的是数据库中存储的密码，不要传递用户输入的密码
        String realName = this.getName();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(username, password, realName);

        return info;
    }
}
```

## 3、测试类中使用自定义数据源

```
//  （v2）自定义realm
IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
```

执行测试

## **4、身份验证的基本流程 **

（1）收集用户身份/凭证，即如用户名/密码 （2）调用subject.login进行登录，如果失败将返回相应的authenticationException异常， 根据异常提示用户错误信息；否则登录成功。 （3）创建自定义的Realm类，继承org.apache.shiro.realm.Authorizingrealm类， 实现doGetAuthenticationInfo()方法**![img](file:///D:/Documents/My Knowledge/temp/76fa2210-2587-436c-9c0f-a8151f466b39/128/index_files/00425e76-79f8-44e3-a30d-bcc808743468.jpg)**