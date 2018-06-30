# **1、下载shiro**

下载地址： <http://shiro.apache.org/download.html>

## **2、创建maven项目**

shiro01hello

## **3、pom**

官网上有maven的坐标

测试环境可以使用shiro-all.jar，加载所有jar

maven环境的依赖：

```
<dependencies>
    <dependency>
        <groupId>org.apache.shiro</groupId>
        <artifactId>shiro-all</artifactId>
        <version>1.3.2</version>
    </dependency>

    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-log4j12</artifactId>
        <version>1.7.21</version>
    </dependency>
    <!--<dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jcl-over-slf4j</artifactId>
        <version>1.6.4</version>
    </dependency>-->
    <dependency>
        <groupId>junit</groupId>
        <artifactId>junit</artifactId>
        <version>4.12</version>
    </dependency>
</dependencies>
```



## **4、复制配置文件**

1）log4j.properties

2）shiro.ini（参考配置）

 

## **5、创建一个简单的测试用例：**

1）数据源配置：ini

shiro-first.ini 中配置用户的账号和密码（身份信息和凭证）

```
#对用户基本信息进行配置
[users]
#用户账号和密码
zhouht=123456
lisi=123456
```

2） 测试用例（Test）

创建相关对象，将用户输入和数据源中的身份信息进行比对

```
package com.zhouht.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.mgt.SecurityManager;
import org.junit.Test;

/**
 * Created by zhouht
 */
public class AuthenticationTest {
    @Test
    public void testLoginAndLogout() {

        // （v1）创建securityManager工厂，通过ini配置文件创建securityManager工厂
        IniSecurityManagerFactory factory = new IniSecurityManagerFactory("classpath:shiro-first.ini");

        // 创建SecurityManager
        SecurityManager securityManager = factory.getInstance();
        // 将securityManager设置当前的运行环境中
        SecurityUtils.setSecurityManager(securityManager);
        // 从SecurityUtils里边创建一个subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备token（令牌）
        // 这里的账号和密码 将来是由用户输入进去
        UsernamePasswordToken token = new UsernamePasswordToken("helen", "123456");
        // 执行认证提交
        subject.login(token);

        // 是否认证通过
        boolean isAuthenticated = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticated);

        // 退出操作
        subject.logout();

        // 是否认证通过
        isAuthenticated = subject.isAuthenticated();
        System.out.println("是否认证通过：" + isAuthenticated);
    }

}
```

（3）执行测试

****

## **6、程序分析：**

**从应用程序角度的来观察如何使用Shiro完成工作 **

1、应用代码通过Subject来进行认证和授权，而Subject又委托给SecurityManager； 

2、我们需要给Shiro的SecurityManager注入Realm，从而让SecurityManager能得到合法的用户及其权限进行判断。 

![img](file:///D:/Documents/My Knowledge/temp/e66c475a-c763-4097-85a0-b0219938fe7f/128/index_files/4a5f51ff-ef16-4a61-a834-9a9b06da850b.jpg)

 可以看到：应用代码直接交互的对象是Subject，也就是说Shiro的对外API核心就是Subject；

其每个API的含义： 

**Subject：**主体，代表了当前“用户”，这个用户不一定是一个具体的人，与当前应用交互的任何东西都是Subject，如网络爬虫，机器人 等；即一个抽象概念；所有Subject 都绑定到SecurityManager，与Subject的所有交互都会委托给SecurityManager；可以把Subject认 为是一个门面；SecurityManager才是实际的执行者； 

**SecurityManager：**安全管理器；即所有与安全有关的操作都会与SecurityManager 交互；且它管理着所有Subject；可以看出它是Shiro 的 核心，它负责与后边介绍的其他组件进行交互，如果学习过SpringMVC，你可以把它看成DispatcherServlet前端控制器； 

**Realm：**域，Shiro从从Realm获取安全数据（如用户、角色、权限），就是说SecurityManager要验证用户身份，那么它需要从Realm获 取相应的用户进行比较以确定用户身份是否合法；也需要从Realm得到用户相应的角色/权限进行验证用户是否能进行操作；可以把Realm 看成DataSource，即安全数据源。 

也就是说对于我们而言，最简单的一个Shiro 应用： 

1、应用代码通过Subject来进行认证和授权，而Subject又委托给SecurityManager； 

2、我们需要给Shiro 的SecurityManager 注入Realm，从而让SecurityManager 能得到合法 的用户及其权限进行判断。

## **7、Shiro的内部架构**

![img](file:///D:/Documents/My Knowledge/temp/e66c475a-c763-4097-85a0-b0219938fe7f/128/index_files/c4cdad02-aefd-4cae-9bf2-e3ad3b37b8f1.jpg)

**Subject：**主体，可以看到主体可以是任何可以与应用交互的“用户”； 

**SecurityManager ：**相当于SpringMVC 中的DispatcherServlet 或者Struts2 中的FilterDispatcher；是Shiro的心脏；所有具体的交互都通过SecurityManager进行 控制；它管理着所有Subject、且负责进行认证和授权、及会话、缓存的管理。 

**Authenticator： **认证器，负责主体认证的，这是一个扩展点，如果用户觉得Shiro 默认的不好，可以自定义实现；其需要认证策略（Authentication Strategy）， 即什么情况下算用户认证通过了； 

**Authrizer：**授权器，或者访问控制器，用来决定主体是否有权限进行相应的操作；即控制着用户能访问应用中的哪些功能； 

**Realm：**可以有1个或多个Realm，可以认为是安全实体数据源，即用于获取安全实体的；可以是JDBC 实现，也可以是LDAP 实现，或者内存实现等等；由用户 提供；注意：Shiro不知道你的用户/权限存储在哪及以何种格式存储；所以我们一般在应用中都需要实现自己的Realm； 

**SessionManager：**如果写过Servlet就应该知道Session的概念，Session需要有人去管理它的生命周期，这个组件就是SessionManager；而Shiro 并不仅仅可 以用在Web 环境，也可以用在如普通的JavaSE 环境、EJB 等环境；所以，Shiro 就抽象了一个自己的Session来管理主体与应用之间交互的数据；这样的话， 比如我们在Web 环境用，刚开始是一台Web 服务器；接着又上了台EJB 服务器；这时想把两台服务器的会话数据放到一个地方，这个时候就可以实现自己的分布式会话（如把数据放到Memcached服务器）； 

**SessionDAO：**DAO 大家都用过，数据访问对象，用于会话的CRUD，比如我们想把Session保存到数据库，那么可以实现自己的SessionDAO，通过如JDBC 写 到数据库；比如想把Session 放到Memcached 中，可以实现自己的Memcached SessionDAO；另外SessionDAO中可以使用Cache进行缓存，以提高性能； 

**CacheManager：**缓存控制器，来管理如用户、角色、权限等的缓存的；因为这些数据基本上很少去改变，放到缓存中后可以提高访问的性能 

**Cryptography：**密码模块，Shiro 提高了一些常见的加密组件用于如密码加密/解密的。