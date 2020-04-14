package com.product.interceptor;

import java.lang.reflect.Method;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.shiro.SecurityUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.product.annotation.LoginInterfaceAnnotation;
import com.utils.CodeUtils;
import com.utils.CookieUtils;

public class LoginInterceprot extends HandlerInterceptorAdapter{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		Boolean isLogin = false;
		//优先判断用户是否已经登陆 
		Map<String,Object> cookie = CookieUtils.getCookie(CodeUtils.COOKIE_NAME, request);
		String cookieValue = String.valueOf(cookie.get(CodeUtils.COOKIE_VALUE));
		System.out.println(SecurityUtils.getSubject().isAuthenticated());
		if(cookieValue.trim().equals("")) {
			response.sendRedirect(request.getServletPath()+"/page/login.html");
			isLogin =  false;
		}else {
			//在进行判断请求的方法 中是否有用户登陆的注解 
			if(handler instanceof HandlerMethod) {
				HandlerMethod handlerMethod = (HandlerMethod)handler;
				Method method = handlerMethod.getMethod();
				if(method.isAnnotationPresent(LoginInterfaceAnnotation.class)) {
					isLogin = true;
				}else {
					response.sendRedirect(request.getServletContext()+"/page/login.html");
					isLogin = false;
				}
			}
		}
		return isLogin;
	}
}
