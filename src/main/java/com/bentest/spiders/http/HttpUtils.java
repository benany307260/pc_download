package com.bentest.spiders.http;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Map;
 
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
 
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;
 
public class HttpUtils {
 
	public static String sendGet(boolean isHttps, String requestUrl, 
			Map<String, String> params, Map<String, String> headers, String charSet) {
		if (StringUtils.isBlank(requestUrl)) {
			return "";
		}
		if (StringUtils.isBlank(charSet)) {
			charSet = "UTF-8";
		}
		URL url = null;
		URLConnection conn = null;
		BufferedReader br = null;
 
		try {
			// 创建连接
			url = new URL(requestUrl + "?" + requestParamsBuild(params));
			if (isHttps) {
				conn = getHttpsUrlConnection(url);
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
 
			// 设置请求头通用属性
 
			// 指定客户端能够接收的内容类型
			conn.setRequestProperty("Accept", "*/*");
 
			// 设置连接的状态为长连接
			conn.setRequestProperty("Connection", "keep-alive");
 
			// 设置发送请求的客户机系统信息
			conn.setRequestProperty("User-Agent",
					"Mozilla/5.0 (Windows NT 6.1; WOW64; rv:60.0) Gecko/20100101 Firefox/60.0");
 
			// 设置请求头自定义属性
			if (null != headers && headers.size() > 0) {
 
				for (Map.Entry<String, String> entry : headers.entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
 
			// 设置其他属性
			// conn.setUseCaches(false);//不使用缓存
			// conn.setReadTimeout(10000);// 设置读取超时时间
			// conn.setConnectTimeout(10000);// 设置连接超时时间
 
			// 建立实际连接
			conn.connect();
 
			// 读取请求结果
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), charSet));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception exception) {
			return "";
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 
	}
	
	private static String getRequestUrlWithParam(String requestUrl, Map<String, String> paramsMap) {
		if(CollectionUtils.isEmpty(paramsMap)) {
			return requestUrl;
		}
	}
 
	public static String requestParamsBuild(Map<String, String> map) {
		String result = "";
		if (null != map && map.size() > 0) {
			StringBuffer sb = new StringBuffer();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				try {
					String value = URLEncoder.encode(entry.getValue(), "UTF-8");
					sb.append(entry.getKey() + "=" + value + "&");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
 
			result = sb.substring(0, sb.length() - 1);
		}
		return result;
	}
 
	private static HttpsURLConnection getHttpsUrlConnection(URL url) throws Exception {
		
		// 创建代理服务器  
        InetSocketAddress addr = new InetSocketAddress("119.178.169.25", 8878);  
        Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); //http 代理
		
		HttpsURLConnection httpsConn = (HttpsURLConnection) url.openConnection(proxy);
		// 创建SSLContext对象，并使用我们指定的信任管理器初始化
		TrustManager[] tm = { new X509TrustManager() {
			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// 检查客户端证书
			}
 
			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
				// 检查服务器端证书
			}
 
			public X509Certificate[] getAcceptedIssuers() {
				// 返回受信任的X509证书数组
				return null;
			}
		} };
		SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
		sslContext.init(null, tm, new java.security.SecureRandom());
		// 从上述SSLContext对象中得到SSLSocketFactory对象
		SSLSocketFactory ssf = sslContext.getSocketFactory();
		httpsConn.setSSLSocketFactory(ssf);
		return httpsConn;
 
	}
 
	public static byte[] getFileAsByte(boolean isHttps, String requestUrl) {
		if (StringUtils.isBlank(requestUrl)) {
			return new byte[0];
		}
		URL url = null;
		URLConnection conn = null;
		BufferedInputStream bi = null;
 
		try {
			// 创建连接
			url = new URL(requestUrl);
			if (isHttps) {
				conn = getHttpsUrlConnection(url);
			} else {
				conn = (HttpURLConnection) url.openConnection();
			}
 
			// 设置请求头通用属性
 
			// 指定客户端能够接收的内容类型
			conn.setRequestProperty("accept", "*/*");
 
			// 设置连接的状态为长连接
			conn.setRequestProperty("Connection", "keep-alive");
 
			// 设置发送请求的客户机系统信息
			conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 设置其他属性
			conn.setConnectTimeout(3000);// 设置连接超时时间
 
			conn.setDoOutput(true);
			conn.setDoInput(true);
 
			// 建立实际连接
			conn.connect();
 
			// 读取请求结果
			bi = new BufferedInputStream(conn.getInputStream());
			ByteArrayOutputStream outStream = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int len = 0;
			while ((len = bi.read(buffer)) != -1) {
				outStream.write(buffer, 0, len);
			}
			bi.close();
			byte[] data = outStream.toByteArray();
			return data;
		} catch (Exception exception) {
			return new byte[0];
		} finally {
			try {
				if (bi != null) {
					bi.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
 
	}
 
	public static void main(String[] args) {
		String requestUrl = "https://httpbin.org/get";
		String a = HttpUtils.sendGet(true, requestUrl, null, null, "utf-8");
		System.out.println(a);
	}
	
}