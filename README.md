# 亿方云 Java6+ SDK

亿方云 Java 版本 SDK，具有强大的文件管理能力。该 SDK 可以在包括 Java6 以上的各个 Java 版本上运行。详细的 api 说明请参考 [亿方云api文档](https://open.fangcloud.com/wiki/v2)。

## 安装

**推荐下载源码，源码中有详细的调用注释可供参考**

如果使用 Maven，需要新加以下依赖：

```
<dependency>
    <groupId>com.fangcloud</groupId>
    <artifactId>fangcloud-java-sdk</artifactId>
    <version>2.2.1</version>
</dependency>
```

如果使用 Gradle，则对应加上以下依赖：

```
repositories {
    mavenCentral()
}

dependencies {
    // ...
    compile 'com.fanglcloud:fangcloud-java-sdk:2.2.1'
}
```

当然，也可以直接下载 Java SDK JAR 包加入到项目中。

## 创建应用

目前开放平台并不开放给所有普通用户使用，只开放给有特定商务合作的公司使用，后期会逐步开放。创建完应用后就得到了 app_key 和 app_secret，这两个参数会在 SDK 中使用，请妥善保管，不要泄露给其他人。**应用启动前必须先初始化 YfyAppInfo，即调用 YfyAppInfo.initAppInfo(appKey, appSecret)。**

## 使用亿方云 API

所有用户必须先通过 OAuth2 授权你的应用，然后才能通过你的应用获取用户的相关信息。完成授权后会返回关联了用户亿方云账号的 access token 和 refresh token 给你的应用，在具体请求中使用这些 token 才能正确使用 api。

* 授权登录：[授权登录的简单web demo](examples/web-demo)

一旦获取了用户的 access token，就可以创建一个 YfyClient 并使用它进行 api 请求。

每一个用户初次登录都需要进行一遍授权流程，默认的 access token 过期时间为6小时，refresh token 过期时间为90天。access token 过期就需要 app 使用用户的 refresh token 获取新的 access token，如果 refresh token 也过期，则用户必须重走授权流程。SDK 中有 access token 自动刷新机制，推荐使用。用户的 access token 和 refresh token 需要持久化，方便重复使用。

## 从源码构建

> git clone git@github.com:yifangyun/fangcloud-java-sdk.git
>
> cd java-sdk
>
> git checkout -b 2.2.1 v2.2.1
>
> mvn install -DskipTests=true

得到的 jar 包就在 target 目录下(单元测试需要设置正确测试环境变量,建议跳过)。

## 运行示例

### web-demo

web-demo 是一个小型的 web app，包括了完整的 Oauth2 的授权和发送 api 过程。运行此 demo 时需进入企业控制台——企业设置——开放平台，将应用官网URL改成 http://localhost:8088/fangcloud-auth-finish。 成功后修改 web-demo 的 resource 目录 下的 config.properties 文件，填入你的 client_id 和 client_secret 按照如下步骤运行 demo（默认会在运行目录下创建 web-demo.db 来存储用户信息）：

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

亿方云开放平台API采用 OAuth2.0 协议进行授权。在SDK中提供了丰富的接口和简单的使用示例，方便开发者对接亿方云的OAuth流程。详细API说明，请参考[亿方云OAuth文档](https://open.fangcloud.com/wiki/v2/#OAuth2)。

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

用户 access 信息都会保存在返回的 YfyAuthFinish 这个对象中，保存并使用其中的 access token 等信息构造 YfyClient 就可以通过 client 发送所有开放的 api 请求。

## 请求示例

### 应用有自己的账号体系并使用 client 缓存（推荐）

```java
// 泛型表示账号体系中的用户标识类型，最后一个参数传null表示关闭自动刷新
YfyClientFactory clientFactory = new YfyClientFactory<String>(100, new YfyRequestConfig(), new YfyRefreshListener<String>(){
  	@Override
  	public void onTokenRefreshed(String key, String accessToken, String refreshToken, long expireIn) {
    	// 在应用中更新或保存刷新后的用户access信息
  	}
});

// 如果缓存中有这个client，则返回，如果没有则创建并放入缓存
YfyClient client = clientFactory.getClient(identify, accessToken, refreshToken);
// 如果缓存中有这个client，则返回，如果没有则返回null
YfyClient client = clientFactory.getClient(identify);
YfyUser user = client.users.getSelf();
```

### 应用没有自己的账号体系（demo中使用）

```java
YfyClient client = new YfyClient(new YfyRequestConfig(), accessToken);
YfyUser user = client.users.getSelf();
```

