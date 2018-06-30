# **1、Apache Shiro是什么**

Apache Shiro 是功能强大并且容易集成的开源权限框架，它 能够完成认证、授权、加密、会话管理、与Web集成、缓存等。 

认证和授权为权限控制的核心，简单来说， “认证”就是证明你是谁？ Web 应用程序一般做法通过表单提交用户名及密码达到认证目的。

 “授权”即是否允许已认证用户访问受保护资源。

## **2、为何对 Shiro 情有独钟 **

Spring Security和Shiro ？ 下面对两者略微比较： 

1、简单性，Shiro 在使用上较 Spring Security 更简单，更容易 理解。适合于入门。 

2、灵活性，Shiro 可运行在 Web、EJB、IoC、Google App Engine 等任何应用环境，却不依赖这些环境。而 Spring Security 只能与 Spring 一起集成使用。 

3、可插拔，Shiro 干净的 API 和设计模式使它可以方便地与许多的其它框架和应用进行集成。Shiro 可以与诸如 Spring、Grails、 Wicket、Tapestry、Mule、Apache Camel、Vaadin 这类第三方框 架无缝集成。 Spring Security 在这方面就显得有些捉衿见肘。

## **3、基本功能**

![img](file:///D:/Documents/My Knowledge/temp/b9f81ac7-ad34-4adc-b4c2-cb2f73a4f2dd/128/index_files/48cae49c-8924-4a4e-8efd-bdbf38f07c97.jpg)

Authentication：身份认证/登录，验证用户是不是拥有相应的身份； 

Authorization： 授权，即权限验证，验证某个已认证的用户是否拥有某个权限；即判断用户是否能做事情，常见的如：验证某个用户是否拥有某个角色。或者细 粒度的验证某个用户对某个资源是否具有某个权限； 

Session Manager：会话管理，即用户登录后就是一次会话，在没有退出之前，它的所有信息都在会话中；会话可以是普通JavaSE环境的，也可以是如Web环境 的； 

Cryptography： 加密，保护数据的安全性，如密码加密存储到数据库，而不是明文存储； 

Web Support：Web支持，可以非常容易的集成到Web环境； 

Caching：缓存，比如用户登录后，其用户信息、拥有的角色/权限不必每次去查，这样可以提高效率； 

Concurrency： shiro支持多线程应用的并发验证，即如在一个线程中开启另一个线程，能把权限自动传播过去； 

Testing：提供测试支持； 

Run As：允许一个用户假装为另一个用户（如果他们允许）的身份进行访问；

Remember Me：记住我，这个是非常常见的功能，即一次登录后，下次再来的话不用登录了。 

注意：Shiro不会去维护用户、维护权限；这些需要我们自己去设计/提供；然后通过相应的接口注入给Shiro即可。

扩展阅读

<http://jinnianshilongnian.iteye.com/blog/2018398>

 