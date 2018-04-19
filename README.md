<p><p> Demo analyzes and explains various characteristics of Shiro (Session management, Spring configuration, cache management, etc.) through each independent functional module.
At the same time, Apache Shiro is simple, flexible, and can be divorced from Spring (Spring Security can not be separated) and coarse-grained architecture features.
Note: Spring official website is also using Shiro to do security management. </p></p>
<p>本Demo通过每个独立的功能模块分析解释了Shiro的各种特性（Session管理、Spring配置、缓存管理等），
同时Apache Shiro具备简单、灵活、可脱离Spring（Spring Security不可脱离）、粒度较粗的一些架构特性。
注意：Spring官网也是用Shiro做安全管理。</p>

<h2>Shiro can be regarded as a powerful and flexible open source security framework of Apache, authentication, authorization, enterprise conversation management, security encryption, etc. </h2>
<h2>Shiro可以说是Apache的强大灵活的开源安全框架，认证、授权、企业会话管理、安全加密等。</h2>

The general process of authentication and authorization of Shiro is generally <br>.
1. Create SecurityManager (security authentication) <br>
2. The principal submits the authentication request <br>
3, SecurityManager authentication <br>
4, Authenticator authentication <br>
5, Realm validation (obtaining authentication data) <br>
Shiro的认证、授权的大致流程一般均为<br>
1、创建SecurityManager（安全认证）<br>
2、主体提交认证请求<br>
3、SecurityManager认证<br>
4、Authenticator认证<br>
5、Realm验证（获取认证数据）<br>

<table>
<tr>
<td>Shiro-test:AuthenticationTest</td>
<td> shows the basic authentication process of Shiro, </td>
</tr>
<tr>
<td>Shiro-test:IniRealmTest</td>
<td> shows the IniRealm implemented by Shiro to implement </td>
</tr>
<tr>
<td>Shiro-test:JdbcRealmTest</td>
<td> shows the JdbcRealm implemented by Shiro to implement </td>
</tr>
<tr>
<td>Shiro-test:CustomRealm</td>
<td> shows Shiro data encryption and salt implementation of </td>
</tr>
<tr>
<td>Shiro-test:MySelfRealmTest</td>
<td> shows the Shiro custom Realm implementation </td>
</tr>
</table>
<table>
    <tr>
        <td>Shiro-test:AuthenticationTest</td>
        <td>展示了Shiro基本的认证流程</td>
    </tr>
    <tr>
        <td>Shiro-test:IniRealmTest</td>
        <td>展示了Shiro自带的IniRealm实现</td>
    </tr>
    <tr>
        <td>Shiro-test:JdbcRealmTest</td>
        <td>展示了Shiro自带的JdbcRealm实现</td>
    </tr>
    <tr>
        <td>Shiro-test:CustomRealm</td>
        <td>展示了Shiro数据加密、盐实现</td>
    </tr>
    <tr>
        <td>Shiro-test:MySelfRealmTest</td>
        <td>展示了Shiro自定义Realm实现</td>
    </tr>
</table>

<h4>The shiro-web project is Shiro integrated SPring and session cache management </h4>
<h4>shiro-web项目是Shiro集成SPring及会话缓存管理</h4>

<p> Demo uses a basic user login as an example, and realizes shiroFilter integration Shiro and Spring through spring.xml configuration. </p>
<p>本Demo使用一个基本的用户登录作为例子，通过spring.xml配置实现shiroFilter集成Shiro和Spring。</p>

<p>Controller package is the basic entry of web request. It can configure Shiro validation path in sprng.xml, and cache package is custom Redis cache management.
The Dao package is database CRUD (MYSQL). Filter has made a custom annotated authentication. Session package uses Redis to do session management, and customize one.
The new SessionManager reduces the number of SessonManager reads and improves the performance. The Util package is a function encapsulation for RedisCRUD and so on. </p>
<p>Controller包是web请求的基本入口，可以在sprng.xml中配置Shiro验证的路径，cache包是自定义的Redis缓存管理，
dao包是数据库CRUD（MYSQL），filter做了一个自定义的注解型认证，session包中使用Redis做session管理，且自定义一个
新的SessionManager减少默认SessonManager读取次数，提高性能。Util包是对RedisCRUD等的功能封装。</p>

<p>There are many annotations in the  project, all of which are annotated in Chinese and English. If there are any questions, please leave a message. </p>
<p>项目中注释偏多且均为中英注释，如果有什么问题欢迎留言。</p>

<h4> Author: UncleCatMySelf</h4>
<h4>email:zhupeijie_java@126.com</h4>
<h5>what's the problem? Welcome to contact QQ:1341933031</h5>.
<h4>作者：UncleCatMySelf</h4>
<h4>email：zhupeijie_java@126.com</h4>
<h5>有什么问题，欢迎联系QQ：1341933031</h5>


<h3>系列问题修复（Series Problem fixes）</h3>
<span>Please change the related configuration after downloading, such as database connection, Redis connection, test class hard coded data, etc.</span>
<span>下载后请更改相关的配置，类似数据库连接、Redis连接、测试类硬编码数据等</span>
<h4>0.0.0.1<h4>
<h5>Update the SLF4J reference for the pom file, add logback-classic, logback-core two log components, configure Logback.xml, and implement console and file log output 
There may be a Pom file reference problem in the previous version-error: 
Could not initialize class Org.apache.shiro.spring.LifecycleBeanPostProcessor This update project function is normal, no exception, 
there are debugging a bug friend please contact me, thank you<h5>
<h5>更新pom文件的slf4j引用，增加logback-classic、logback-core两个日志组件，配置logback.xml,实现控制台及文件日志输出
上一版本可能存在pom文件引用问题-报错：Could not initialize class org.apache.shiro.spring.LifecycleBeanPostProcessor
本次更新项目功能均正常，暂无异常，有调试出bug的朋友请联系我，谢谢<h5>
<h4>0.0.0.5<h4>
<h5>Update the latest version of the SQL file, user login information, the original username, password is: myself 123456 if you want to customize the database to modify the original field content, the password is MD5 encryption, salt is your own definition of the user name. This update project function is normal, no exception, there are debugging a bug friend please contact me, thank you</h5>
<h5>更新sql文件最新版，用户登录信息，原始用户名、密码为：MySelf  123456  如要自定义需要修改数据库原始字段内容，密码为MD5加密，盐是你自己定义的用户名。本次更新项目功能均正常，暂无异常，有调试出bug的朋友请联系我，谢谢</h5>