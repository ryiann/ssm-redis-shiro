package com.ryan.controller;

import com.ryan.enums.ResponseCode;
import com.ryan.service.UserService;
import com.ryan.utils.BaseController;
import com.ryan.utils.MapUtil;
import com.ryan.vo.UserVO;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

/**
 * 登录与登出
 * @author YoriChen
 * @date 2018/5/21
 */
@Controller
public class AccountController extends BaseController {

    @Autowired
    UserService userService;

    @GetMapping("/login")
    public String login(){
        return View("login");
    }

    @PostMapping("/login")
    public String loginSubmit(Model model, @Valid UserVO vo, BindingResult bindingResult) {
        String reqMeg = null;

        if(!bindingResult.hasErrors()){
            // 得到Subject及创建用户名/密码身份验证Token(用户身份凭证)
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(vo.getUserName().trim().toLowerCase(),vo.getPassWord());
            token.setRememberMe(vo.getRemember());

            // 用户是否为已认证用户
            if(subject.isAuthenticated()){
                subject.logout();
            }

            try {
                subject.login(token);
                return RedirectTo("/product/list");
            }catch (UnknownAccountException uae) {
                // 用户名不存在
                reqMeg = ResponseCode.LOGIN_NO_USER.getMessage();
                logger.info("There is no user with username of {}", token.getPrincipal());
            } catch (IncorrectCredentialsException ice) {
                // 密码错误
                reqMeg = ResponseCode.LOGIN_PASSWORD_INCORRECT.getMessage();
                logger.info("Password for account {} was incorrect!", token.getPrincipal());
            } catch (LockedAccountException lae) {
                // 用户被锁定，不能登录
                reqMeg = ResponseCode.LOGIN_UNLOCK.getMessage();
                logger.info("The account for username {} Please contact your administrator to unlock it.", token.getPrincipal());
            }catch (AuthenticationException ae) {
                // 其他错误
                reqMeg = ResponseCode.LOGIN_ERROR.getMessage();
                logger.info("Unexpected condition");
            }finally {
                model.addAttribute("errorMsg",reqMeg);
            }
        }else{
            model.addAttribute("errors", MapUtil.objectErrorsToMap(bindingResult.getAllErrors()));
        }
        vo.setPassWord("");
        return View("login",model,vo);
    }

    @GetMapping("/register")
    public String register(){
        return View("register");
    }

    @GetMapping("/logout")
    public String logout(){
        Subject subject = SecurityUtils.getSubject();
        if(subject.isAuthenticated()){
            subject.logout();
        }
        return RedirectTo("/login");
    }

}