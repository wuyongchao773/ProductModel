package com.utils;

import java.util.HashMap;
import java.util.Map;

public class ResultUtils {

	private static Map<String,Object> maps = new HashMap<String,Object>();
	
	public static Map<String,Object> success(Object message){
		maps.put("message",message);
		maps.put("code","200");
		return maps;
	}
	
	public static Map<String,Object> field(Object message){
		maps.put("message",message);
		maps.put("code","999");
		return maps;
	}
	
	public static Map<String,Object> tableSuccess(Object data,int count){
		maps.put("code",0);
		maps.put("msg", "");
		maps.put("count",count);
		maps.put("data",net.sf.json.JSONArray.fromObject(data));
		return maps;
	}
}
