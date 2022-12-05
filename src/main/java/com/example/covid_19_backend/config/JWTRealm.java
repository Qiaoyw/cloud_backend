package com.example.covid_19_backend.config;

import com.auth0.jwt.exceptions.TokenExpiredException;
import com.example.covid_19_backend.pojo.User;
import com.example.covid_19_backend.service.UserService;
import com.example.covid_19_backend.util.JWTToken;
import com.example.covid_19_backend.util.JWTUtil;
import com.example.covid_19_backend.util.RedisUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
@Slf4j
public class JWTRealm extends AuthorizingRealm {

    private UserService userService;
    private RedisUtil redisUtil;

    //根据token判断此Authenticator是否使用该realm
    //必须重写不然shiro会报错
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JWTToken;
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如@RequiresRoles,@RequiresPermissions之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User user = (User)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //查询数据库来获取用户的角色
        info.addRole("user");
        //查询数据库来获取用户的权限
        return info;
    }


    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可，在需要用户认证和鉴权的时候才会调用
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String jwt = (String) token.getCredentials();
        String uid = null;
        try {
            uid = JWTUtil.getUsername(jwt);
        } catch (Exception e) {
            throw new AuthenticationException("token非法，不是规范的token，可能被篡改了，或者过期了");
        }
        if (uid == null) {
            throw new AuthenticationException("token中无用户名");
        }
        // TODO: 添加按用户ID查询用户
        User user = userService.getUserByEmail(uid);
        if (user == null) {
            throw new AuthenticationException("该用户不存在");
        }
        //开始认证，只要AccessToken没有过期，或者refreshToken的时间节点和AccessToken一致即可
        if (redisUtil.hasKey(uid)) {
            //判断AccessToken有无过期
            if (!JWTUtil.verify(jwt)) {
                throw new TokenExpiredException("token认证失效，token过期，重新登陆");
            } else {
                //判断AccessToken和refreshToken的时间节点是否一致
                long current = (long) redisUtil.get(uid);
                if (current == JWTUtil.getExpire(jwt)) {
                    return new SimpleAuthenticationInfo(user, jwt, "JWTRealm");
                } else {
                    throw new AuthenticationException("token已经失效，请重新登录！");
                }
            }
        } else {
            throw new AuthenticationException("token过期或者Token错误！！");
        }
    }

    private String code2Role(int code) {
        switch (code) {
            case 0: return "admin";
            case 1: return "user";
            default: return "unknown";
        }
    }
}

