package com.ryan.shiro;

import com.alibaba.fastjson.JSON;
import com.ryan.pojo.UserDO;
import com.ryan.service.UserService;

import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 权限校验器
 * @author YoriChen
 * @date 2018/6/19
 */
public class LoginRealm extends AuthorizingRealm {

    private static final Logger logger = LogManager.getLogger(LoginRealm.class);

    @Autowired
    private UserService userService;

    /**
     * 获取身份验证相关信息
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userName = token.getPrincipal().toString();
        UserDO user = userService.selectUserInfoByUserName(userName);
        if (user != null){
            Subject subject = SecurityUtils.getSubject();
            subject.getSession().setAttribute("LOGIN_USER_ID",user.getUserId());
            logger.info("user authentication : {}", JSON.toJSON(user));
            return  new SimpleAuthenticationInfo(user.getUserName(),user.getPassWord(),getName()) ;
        }else{
            return  null ;
        }
    }

    /**
     * 授权
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}