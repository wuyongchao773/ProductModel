package com.utils;

import java.util.UUID;

/***
 * @Title 生成id工具类
 * @author wuyongchao
 * @date 2019-04-15 00:15:00
 *
 */
public class IdGenerator {

	/***
	 * @Title 生成32位id
	 * @author wuyongchao
	 * @date 2019-04-15 00:16:00
	 */
	public static String getUuid(){
		UUID uuid = UUID.randomUUID();
		String randomUuid = uuid.toString();
		randomUuid = randomUuid.replace("-","");
		return randomUuid;
	}
	
}
