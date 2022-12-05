# covid_19_backend

新冠疫情综合信息平台后端项目

## 部署脚本
```bash
nohup java -jar covid_19_backend-0.0.1-SNAPSHOT.jar &
```

## 如何进行鉴权？

在前后端分离应用中，后端的某些接口必须要求用户拥有一定权限才可以访问，如：“订阅疫情信息”只能登录后使用。目前主流的鉴权方式是在登录时返回给前端一个token由它保存，每次前端请求时在请求头携带这一token，后端就可以确定这是哪个用户。

### 获得当前用户

在你的接口中，可以直接获取发起这个请求的用户实体而不用做任何处理（一切都在Shiro拦截器里面完成了）

```java
User user = (User)SecurityUtils.getSubject().getPrincipal();
```

### 设置接口权限

只需要在接口方法上添加相应的注解即可

允许用户或管理员访问（即需要登录）：

```java
@RequiresRoles(value={"admin","user"}, logical = Logical.OR)
```

只允许管理员访问：

```java
@RequiresRoles("admin")
```

