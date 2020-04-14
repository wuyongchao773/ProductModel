package com.utils;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import com.alibaba.fastjson.JSONObject;

/***
 * @Title HTTPS工具类
 * @author wuyongchao
 * @date 2019-12-27 16:45:32
 */
public class HttpsUtils {
	
	/***
	 * @Title httpsGet请求工具类
	 * @author wuyongchao
	 * @date 2019-12-27 16:45:32
	 * @param url  请求的url
	 * @return
	 */
	@SuppressWarnings("resource")
	public JSONObject httpsGet(String url) {
		JSONObject jsonObject = null;
		try {
			HttpClient client = new SSLContextUtils();
			HttpGet get = new HttpGet(url);
			HttpResponse httpResponse = client.execute(get);
			jsonObject = JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity(),"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
	/***
	 * @Title httpsPost请求
	 * @author wuyongchao
	 * @param url    请求地址
	 * @param object   传递的对象
	 * @return
	 */
	@SuppressWarnings("resource")
	public JSONObject httpPost(String url,JSONObject object) {
		JSONObject jsonObject = null;
		try {
			HttpClient client = new SSLContextUtils();
			HttpPost post = new HttpPost(url);
			StringEntity entity = new StringEntity(object.toJSONString());
			post.setEntity(entity);
			HttpResponse httpResponse = client.execute(post);
			jsonObject = JSONObject.parseObject(EntityUtils.toString(httpResponse.getEntity(),"utf-8"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return jsonObject;
	}
}
