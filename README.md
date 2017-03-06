# 亿方云 Java6+ SDK

亿方云 Java 版本 SDK，集成亿方云 V1 系列API，具有强大的文件管理能力。该 SDK 可以在包括 Java6 以上的各个版本上运行。

## 安装

如果使用 Maven， 编辑项目中的 pom.xml 文件，把下面这些放到 \<dependencies\> 部分下：

```
<dependency>
    <groupId>com.fanglcloud</groupId>
    <artifactId>java-sdk</artifactId>
    <version>1.0.0</version>
</dependency>
```

如果使用 Gradle，编辑项目中的 build.gradle 文件，把下面这些放到 dependencies 部分：

```
dependencies {
    // ...
    compile 'com.fanglcloud:java-sdk:1.0.0'
}
```

当然，也可以直接下载 Java SDK JAR 包加入到项目中。

## 创建应用

创建完应用后就得到了 app_key 和 app_secret，这两个参数会在 SDK 中使用，请妥善保管，不要泄露给别人。

## 使用亿方云 API

所有用户必须先通过 OAuth2 授权你的应用，然后才能通过你的应用获取用户的相关信息。完成授权后会返回关联了用户亿方云账号的 access token 和 refresh token 给你，在请求中使用这些 token 才能拿到用户文件。

* 授权登录：[授权登录的简单web demo](examples/web-demo)

一旦获取了用户的 access token，就可以创建一个 YfyClient 并使用它进行 api 请求。

每一个用户初次登录都需要进行一遍授权流程，默认的 access token 过期时间为6小时，refresh token 过期时间为90天。access token 过期就需要 app 使用用户的 refresh token 获取新的 access token，如果 refresh token 也过期，则用户必须重走授权流程。SDK 中有 access token 自动刷新机制，推荐使用。用户的 access token 和 refresh token 需要持久化，方便重复使用。

## 从源码构建

> git clone git@192.168.0.234:platform-sdk/java-sdk.git
>
> cd java-sdk
>
> mvn install (or package)

得到的 jar 包就在 target 目录下。

## 运行示例

### web-demo

web-demo 是一个小型的 web app，包括了完整的 Oauth2 的授权和发送 api 过程。运行此 demo 时需进入企业控制台——企业设置——开放平台，将应用官网URL改成 http://localhost:8088/fangcloud-auth-finish。成功后修改 web-demo 的 resource 目录 下的 config.properties 文件，填入你的 client_id 和 client_secret 按照如下步骤运行 demo（默认会在运行目录下创建 web-demo.db 来存储用户信息）：

> cd java-sdk/examples
>
> mvn package
>
> cd web-demo/target/web-demo
>
> java -jar web-demo-1.0-SNAPSHOT.jar

### tutorial

tutorial 是一个简单的通过 access token 上传文件的 demo。

## 授权流程

亿方云开放平台API采用 OAuth2.0 协议进行授权。在SDK中提供了丰富的接口和简单的使用示例，方便开发者对接亿方云的OAuth流程。详细API说明，请参考[亿方云文档](https://open.fangcloud.com/wiki/#OAuth2)。

对接的 Demo 位于 example/web-demo 中，运行其中的 Main.java 即可启动web服务。在浏览器中输入 "[http://localhost:8088](http://localhost:8088)" 进入demo流程。在使用之前请确保本地8088端口未被占用。web-demo使用 jetty 框架进行搭建。

### 获取授权 url

```java
YfyWebAuth.Request authRequest = YfyWebAuth.newRequestBuilder()
        .withRedirectUri(getRedirectUri(request), getSessionStore(request))
        .withState("test")
        .build();
String authorizeUrl = new YfyWebAuth(new YfyRequestConfig()).authorize(authRequest);
```

### 授权完成后获取用户 access 信息

```java
YfyAuthFinish authFinish = new YfyWebAuth(new YfyRequestConfig()).finishFromRedirect(
                    getRedirectUri(request),
                    getSessionStore(request),
                    request.getParameterMap());
```

