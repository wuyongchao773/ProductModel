package com.commpond;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.StringUtils;
import org.springframework.web.servlet.LocaleResolver;

/***
 * @Title 自定义的方式目前还不知道怎么处理 --->目前先留着 后期会进行处理
 * @author Administrator
 *
 */
public class MyLocaleResolver implements LocaleResolver{

	/***
	 * @Title 获取参数
	 * @param zh_CH  //_左边是    语言    _右边是国家
	 * @desc 前台请求参数格式得是  http://xxx:8080/xxx.html?l=zh_CH
	 * @author wuyongchao
	 * @date 2019-12-26 10:02:32
	 */
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		//先获得默认语言
		Locale locale = Locale.getDefault();
		String l = request.getParameter("l");
		if(StringUtils.isEmpty(l)) {
			return locale;
		}
		String[] encoding = l.split("_");
		return new Locale(encoding[0],encoding[1]);
	}

	/***
	 * @Title 设置语言 用来做国际化
	 * @author wuyongchao
	 * @date 2019-12-26 10:01:23
	 */
	@Override
	public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
		// TODO Auto-generated method stub
		
	}

}
