package com.product.controller;

import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.DisabledAccountException;
import org.apache.shiro.authc.ExpiredCredentialsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import com.alibaba.druid.util.StringUtils;
import com.product.annotation.LoginInterfaceAnnotation;
import com.product.model.MakeLog;
import com.product.service.MakeLogService;
import com.utils.CodeUtils;
import com.utils.CookieUtils;
import com.utils.LogUtils;
import com.utils.ResultUtils;
import com.utils.ValidateCode;

@Controller
public class LoginController {
	
	@Autowired
	private MakeLogService makeLogService;

	@RequestMapping(value = "/")
	@LoginInterfaceAnnotation
	public String index() {
		return "login";
	}

	@RequestMapping(value = "/getCheckCode")
	@LoginInterfaceAnnotation
	public void getCheckCode(HttpServletRequest request, HttpServletResponse response) {
		ValidateCode code = new ValidateCode(100, 30, 4, 30, 25, "validateCode");
		code.getCode(request, response);
	}

	@RequestMapping(value = "/userLogin", method = RequestMethod.POST)
	@LoginInterfaceAnnotation
	public @ResponseBody Map<String, Object> userLogin(HttpServletRequest request, HttpServletResponse response) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		// 前台传递的验证码
		String code = request.getParameter("code");
		String checkCode = String.valueOf(request.getSession().getAttribute("sessionKey"));
		if (StringUtils.isEmpty(username)) {
			responseMap =  ResultUtils.field("用户名不能为空");
		} else if (StringUtils.isEmpty(password)) {
			responseMap =  ResultUtils.field("密码不能为空");
		} else if (StringUtils.isEmpty(checkCode)) {
			responseMap =  ResultUtils.field("验证码不能为空");
		} else if (!checkCode.equals(code)) {
			responseMap =  ResultUtils.field("验证码输入有误");
		} else {
			// 这个地方使用shiro来进行验证
			UsernamePasswordToken passwordToken = new UsernamePasswordToken(username, password);
			Subject subject = SecurityUtils.getSubject();
			passwordToken.setRememberMe(true);
			try {
				subject.login(passwordToken);
				if (subject.isAuthenticated()) {
					CookieUtils.addCookie(CodeUtils.COOKIE_NAME,username,response);
					responseMap =  ResultUtils.success("登陆成功");
					//添加操作日志
					MakeLog makeLog = LogUtils.insertLog(username+"用户登陆成功",username,"info",request);
					makeLogService.insertLog(makeLog);
				}
			} catch (IncorrectCredentialsException e) {
				responseMap =  ResultUtils.field("登录密码错误.");
				MakeLog makeLog = LogUtils.insertLog(username+"登录密码错误.",username,"warning",request);
				makeLogService.insertLog(makeLog);
			} catch (LockedAccountException e) {
				responseMap =  ResultUtils.field("帐号已被锁定.");
				MakeLog makeLog = LogUtils.insertLog(username+"帐号已被锁定.",username,"warning",request);
				makeLogService.insertLog(makeLog);
			} catch (DisabledAccountException e) {
				responseMap =  ResultUtils.field("帐号已被禁用.");
				MakeLog makeLog = LogUtils.insertLog(username+"帐号已被禁用.",username,"warning",request);
				makeLogService.insertLog(makeLog);
			} catch (ExpiredCredentialsException e) {
				responseMap =  ResultUtils.field("帐号已过期. ");
				MakeLog makeLog = LogUtils.insertLog(username+"帐号已过期.",username,"warning",request);
				makeLogService.insertLog(makeLog);
			} catch (UnknownAccountException e) {
				responseMap =  ResultUtils.field("帐号不存在.");
				MakeLog makeLog = LogUtils.insertLog(username+"帐号不存在.",username,"warning",request);
				makeLogService.insertLog(makeLog);
			} catch (UnauthorizedException e) {
				responseMap =  ResultUtils.field("您没有得到相应的授权！");
				MakeLog makeLog = LogUtils.insertLog(username+"您没有得到相应的授权！",username,"warning",request);
				makeLogService.insertLog(makeLog);
			}
		}
		return responseMap;
	}
	
	@RequestMapping(value = "/logout",method = RequestMethod.GET)
	@LoginInterfaceAnnotation
	public String logout(HttpServletRequest request, HttpServletResponse response){
		String username = String.valueOf(CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request).get(CodeUtils.COOKIE_VALUE));
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		MakeLog makeLog = LogUtils.insertLog(username+"退出登陆！",username,"info",request);
		makeLogService.insertLog(makeLog);
		return "rederect: /";
	}
	
}