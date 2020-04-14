package com.utils;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.product.model.MakeLog;

/***
 * @Title 日至操作工具类
 * @author wuyongchao
 * @date 2019-12-17 09:43:11
 *
 */
public class LogUtils {

	/***
	 * @Title 创建日至对象
	 * @param makeInfo  操作信息例如 新增用户操作
	 * @param userId    cookie中对应的用户
	 * @param logType   (info,error,warning)
	 * @return  创建好的日至对象
	 */
	public static MakeLog insertLog(String makeInfo,String userId,String logType,HttpServletRequest request) {
		return new MakeLog(makeInfo,userId,request.getRemoteUser()==null? "":request.getRemoteUser(), request.getRemoteAddr(),request.getRemoteHost(),HostUtils.getHostSystem(),logType, new Date());
	}
}
