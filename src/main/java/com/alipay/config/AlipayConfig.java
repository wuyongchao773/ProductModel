package com.alipay.config;

public class AlipayConfig {
	// 商户appid
	public static String APPID = "2016092600597564";
	// 私钥 pkcs8格式的
	public static String RSA_PRIVATE_KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCA+h4t+TGO7hiTXfqDZx+Z0bHKMRRwJ8cxuXmneBYhcAScrfVFWFX++e80k5yargzb585TuugoJRgYqcqH7V2chNFnfRspxi6GI0FC6B7n8KChe4gI2EP+6ymXqVLHlDv51goixHE8DMZzNyH+R+Jp9mHCC2mCbdRJZcW8vJsX+T4O84oloXXrr4OuaoZtbdfN7C8iAKz9BAC+WbwLcm6qtf5mhsRaIWbQj9rVEgYAuinXOw8y3uKHjcBSkEsBCfJ9r/sbdAHCcMeE5v4X6sVIpg4NWpkZ2to7D1OKpSUJoxxYsYL9Z0NIU9UnpNzphEPg7eXSFb2NIcYpmXklEi1dAgMBAAECggEAcjiPN5mjSPgU7ZVhwLM36iopsG0ss3KW1rNySFxyBOTGQNdSCjqD1g4TyFUdcGLJYop0T/SLmtnW8CiWAzC0IWQCawsBXkpzczmPlygoDrIsHzZeL68O8JfkOrqqY7MkNkpWqZmcylhp+ykNQrr4iWy4AHfI1chcIzAtIMtK8rv3N8Ej6bA0YgRiuUH1ZepdC0B/NN7XF6Y1Jk13K8ce3NCVWkKOraNq+LyarnqpWuxgWk3lVulQF7bkuH/vcuin/0/2+t81KHmjoo5OIM0P496vqR6aH9/Jrasx62o1erUhzJSAFzo9wXLIeqxT7XbL/kfaNwixBcw4UVDxPzHxIQKBgQC7gaNFRPeg0gYPuaSV8Hsl1P2UUC4x5eIGdJOQowTFFXuNK8KD3R3iZWmSKadcEzrWVwpwaxGt16PyAuWAILgjZcuLK/16U9QwRBA4/t8vohVRFQZMlA3Uu0hmIJDQLf89gu7QCHWbh/jQ1UvLRlvKKHfZZs9FGzdsQsEClF3C+QKBgQCwFzLnOkg2ML2Kfz5jK45bnnEJCkfTni+/yh8+fzWBX8WBkWVJfW6uFkK5GMgXGhivXK6Bvq54nSCt2ITdL7AByqDHPVgaXxGeUGwziSDA5QrOMxf1LWdjoOqJrtEy+7IAIRL3/ww2g3q1gKmXHmOWXTcL5ilNKw8P3jijhRFyhQKBgFfw4DNvujgZ6K/dYJzWEdvXfireRBww9gYTDNaMLoOEpNK8EFmqDgcNiyr7EDw15KV8YzAAd2UyOvkZdKvYLKSlbIZUVmjWAjG06mrg1qpPeXHyy2aFFcuNHtKPOXv55rjhvMGjBg2xAvhm4UpwMEKrIjK7bkC6GA9Va5KbJTJ5AoGACN/ycdLvJsQIGX3WQBtG3nk6yzDYc+LqFylxr62rnaydAK26RBztQhrfHAMSuo9XJSdvqBxbILsSlZBvCdLIdR6oQcJmuVWHRspY2bQUkYQ5qjDLCVFTf6LxjyTfaEllLDp+gT86d1jjC1jDs1kVvdNO3euosznzVWJmyl7/hR0CgYBlfHsCu6g6R8369n1+As3CUlgyOT3pEwZtwpFohwRwYnwzcGzXQzs3QatRdFWP5L5i7zTPXMIJOhBf4oP7WVk0/F0LDlnaiEEfi1AfUPStYIBOkJVM84q+SWQzMJxz2ZeCFpdrDjaM+w0oDyKr6ChUifAA6YCjpc4U7g0THJEdeQ==";
	// 服务器异步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	public static String notify_url = "http://47.94.106.18/ProductModel/notify_url.jsp";
	// 页面跳转同步通知页面路径 需http://或者https://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问 商户可以自定义同步跳转地址
	public static String return_url = "http://47.94.106.18/ProductModel/return_url.jsp";
	// 请求网关地址
	//public static String URL = "https://openapi.alipay.com/gateway.do";
	public static String URL = "https://openapi.alipaydev.com/gateway.do";
	// 编码
	public static String CHARSET = "UTF-8";
	// 返回格式
	public static String FORMAT = "json";
	// 支付宝公钥
	public static String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAgPoeLfkxju4Yk136g2cfmdGxyjEUcCfHMbl5p3gWIXAEnK31RVhV/vnvNJOcmq4M2+fOU7roKCUYGKnKh+1dnITRZ30bKcYuhiNBQuge5/CgoXuICNhD/uspl6lSx5Q7+dYKIsRxPAzGczch/kfiafZhwgtpgm3USWXFvLybF/k+DvOKJaF166+DrmqGbW3XzewvIgCs/QQAvlm8C3JuqrX+ZobEWiFm0I/a1RIGALop1zsPMt7ih43AUpBLAQnyfa/7G3QBwnDHhOb+F+rFSKYODVqZGdraOw9TiqUlCaMcWLGC/WdDSFPVJ6Tc6YRD4O3l0hW9jSHGKZl5JRItXQIDAQAB";
	// 日志记录目录
	public static String log_path = "/log";
	// RSA2
	public static String SIGNTYPE = "RSA2";
}
