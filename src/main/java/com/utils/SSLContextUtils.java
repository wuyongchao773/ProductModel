package com.utils;

import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;

/***
 * @TItle https请求工具类
 * @author wuyongchao
 * @date 2019-12-27 16:27:17
 */
public class SSLContextUtils extends DefaultHttpClient{

	SSLContextUtils() {
		try {
			//第一步初始化SSLContext
			SSLContext context = SSLContext.getInstance("TLS");	
			//绕过ssl验证
			X509TrustManager trustManager = new X509TrustManager() {
				
				@Override
				public X509Certificate[] getAcceptedIssuers() {
					return null;
				}
				
				@Override
				public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
				
				@Override
				public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				}
			};
			//初始化sslContext
			context.init(null,new TrustManager[] {trustManager},null);
			//创建SSL通讯工厂
			SSLSocketFactory socketFactory = new SSLSocketFactory(context,SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
			ClientConnectionManager ccm = this.getConnectionManager();
			SchemeRegistry registry = ccm.getSchemeRegistry();
			registry.register(new Scheme("https",443,socketFactory));
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
}
