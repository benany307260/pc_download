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
import org.apache.http.client.utils.URIBuilder;
import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import cn.hutool.core.util.StrUtil;
 
public class HttpUtils {
	
	private static Logger log = LoggerFactory.getLogger(HttpUtils.class);
 
	public static String sendGet(HttpRequest request) {
		
		if(request == null) {
			return "";
		}
		if (StringUtils.isBlank(request.getUrl())) {
			return "";
		}
		
		URLConnection conn = null;
		BufferedReader br = null;
 
		try {
			String urlAndParam = getRequestUrlWithParam(request.getUrl(), request.getParams());
			// 创建连接
			URL url = new URL(urlAndParam);
			
			if (request.isUseHttps()) {
				conn = getHttpsUrlConnection(url, request);
			} else {
				conn = getHttpUrlConnection(url, request);
			}
 
			// 设置请求头通用属性
 
			// 指定客户端能够接收的内容类型
			//conn.setRequestProperty("Accept", "*/*");
 
			// 设置连接的状态为长连接
			//conn.setRequestProperty("Connection", "keep-alive");
 
			// 设置发送请求的客户机系统信息
			//conn.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:60.0) Gecko/20100101 Firefox/60.0");
 
			// 设置请求头自定义属性
			if (!CollectionUtils.isEmpty(request.getHeaders())) {
				for (Map.Entry<String, String> entry : request.getHeaders().entrySet()) {
					conn.setRequestProperty(entry.getKey(), entry.getValue());
				}
			}
 
			// 设置其他属性
			conn.setUseCaches(request.isUseCaches());//不使用缓存
			conn.setReadTimeout(request.getReadTimeout());// 设置读取超时时间
			conn.setConnectTimeout(request.getConnTimeout());// 设置连接超时时间
 
			// 建立实际连接
			conn.connect();
 
			// 读取请求结果
			br = new BufferedReader(new InputStreamReader(conn.getInputStream(), request.getCharSet()));
			String line = null;
			StringBuilder sb = new StringBuilder();
			while ((line = br.readLine()) != null) {
				sb.append(line);
			}
			return sb.toString();
		} catch (Exception e) {
			log.error("发送get请求，异常。", e);
			return "";
		} finally {
			try {
				if (br != null) {
					br.close();
				}
			} catch (Exception e) {
				log.error("发送get请求，关闭资源，异常。", e);
			}
		}
 
	}
	
	private static String getRequestUrlWithParam(String requestUrl, Map<String, String> paramsMap) {
		if(StrUtil.isBlank(requestUrl)) {
			return "";
		}
		if(CollectionUtils.isEmpty(paramsMap)) {
			return requestUrl;
		}
		
		try {
			URIBuilder uri = new URIBuilder(requestUrl);
			for(String key : paramsMap.keySet()) {
				uri.setParameter(key, paramsMap.get(key));
			}
			return uri.build().toString();
		} catch (Exception e) {
			log.error("获取请求url并拼接参数，异常。requestUrl="+requestUrl+",params="+paramsMap.toString(), e);
			return "";
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
 
	private static HttpsURLConnection getHttpsUrlConnection(URL url, HttpRequest request) throws Exception {

		HttpsURLConnection httpsConn = null;
		if(request.isUseProxy()) {
			if(StringUtil.isBlank(request.getProxyIp())) {
				log.error("获取https连接，代理ip为空。");
				return null;
			}
			if(request.getProxyPort() < 1) {
				log.error("获取https连接，代理port为空。");
				return null;
			}
			// 创建代理服务器  
			InetSocketAddress addr = new InetSocketAddress(request.getProxyIp(), request.getProxyPort());  
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); //http 代理
			httpsConn = (HttpsURLConnection) url.openConnection(proxy);
		}else {
			httpsConn = (HttpsURLConnection) url.openConnection();
		}

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
	
	private static HttpURLConnection getHttpUrlConnection(URL url, HttpRequest request) throws Exception {

		HttpURLConnection httpsConn = null;
		if(request.isUseProxy()) {
			if(StringUtil.isBlank(request.getProxyIp())) {
				log.error("获取http连接，代理ip为空。");
				return null;
			}
			if(request.getProxyPort() < 1) {
				log.error("获取http连接，代理port为空。");
				return null;
			}
			// 创建代理服务器  
			InetSocketAddress addr = new InetSocketAddress(request.getProxyIp(), request.getProxyPort());  
			Proxy proxy = new Proxy(Proxy.Type.HTTP, addr); //http 代理
			httpsConn = (HttpURLConnection) url.openConnection(proxy);
		}else {
			httpsConn = (HttpURLConnection) url.openConnection();
		}
		
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