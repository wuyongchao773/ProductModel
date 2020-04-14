package com.utils;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * @Title Cookie工具类 23:07:00
 * @author wuyongchao
 * @date 2019-04-16
 */
public class CookieUtils {

	/***
	 * @Title 添加cookie信息
	 * @author wuyongchao
	 * @date 2019-04-16 23:15:00
	 * @param cookieName  cookie名称
	 * @param cookieValue  cookie值
	 * @param response  响应实力
	 */
	public static void addCookie(String cookieName,String cookieValue,HttpServletResponse response){
		Cookie cookie = new Cookie(cookieName, cookieValue);
		cookie.setMaxAge(24 * 60 * 60 * 1000);
		response.addCookie(cookie);
	}
	
	/***
	 * @Title 获得cookie值
	 * @author wuyongchao
	 * @date 2019-04-16 23:20:00
	 * @param cookieName 要查询的cookie名称
	 * @param request  请求体
	 * @return
	 */
	public static Map<String,Object> getCookie(String cookieName,HttpServletRequest request){
		Map<String,Object> responseMap = new HashMap<String,Object>();
		Cookie[] cookie = request.getCookies();
		for(int i = 0 ; i < cookie.length; i++){
			if(cookie[i].getName().trim().equals(cookieName)){
				responseMap.put(CodeUtils.COOKIE_NAME,cookie[i].getName());
				responseMap.put(CodeUtils.COOKIE_VALUE,cookie[i].getValue());
				break;
			}
		}
		return responseMap;
	}
	
}
