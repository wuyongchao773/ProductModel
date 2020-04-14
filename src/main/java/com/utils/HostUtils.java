package com.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/****
 * @Title 处理日志相关信息
 * @author wuyongchao
 * @date 2019-12-16 16:42:32
 *
 */
public class HostUtils {

	private Logger logger = LoggerFactory.getLogger(HostUtils.class);

	private static InetAddress address;

	static {
		try {
			address = InetAddress.getLocalHost();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
	}

	/***
	 * @Title 获取主机ip信息
	 * @author wuyongchao
	 * @date 2019-12-16 16:46:21
	 * @return 返回本地地址
	 */
	public static String getHostIp() {
		return address.getHostAddress();
	}

	/***
	 * @Title 获取当前系统名称
	 * @author wuyongchao
	 * @date 2019-12-16 16:46:32
	 * @return 返回本机系统名称
	 */
	public static String getHostName() {
		return address.getHostName();
	}

	/***
	 * @Title 获取注解系统名称
	 * @author wuyongchao
	 * @date 2019-12-16 16:48:21
	 * @return 返回本机系统名称
	 */
	public static String getHostSystem() {
		return System.getProperty("os.name");
	}
	
	/*
	*//***
	 * @Title 获取jdk版本信息
	 * @author wuyongchao
	 * @date 2019-12-16 16:54:11
	 * @return 返回本机jdk版本信息
	 *//*
	public static String getJdkVersion() {
		return System.getProperty("java.version");
	}*/

	/***
	 * @Title 获取操作的用户名称
	 * @author wuyongchao
	 * @date 2019-12-16 16:53:16
	 * @return
	 */
	public static String getUser() {
		return System.getProperty("user.name");
	}

}
