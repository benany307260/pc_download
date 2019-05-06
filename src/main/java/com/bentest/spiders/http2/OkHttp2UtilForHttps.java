package com.bentest.spiders.http2;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import com.bentest.spiders.http.HttpRequest;
import com.bentest.spiders.http.HttpResponse;

import cn.hutool.core.util.StrUtil;
import okhttp3.ConnectionPool;
import okhttp3.Headers;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttp2UtilForHttps{
	private Logger log = LoggerFactory.getLogger(this.getClass());
    
	private OkHttpClient  okHttpClient = null;
	
	public boolean initOkHttpClient() {
		OkHttpConfig config = new OkHttpConfig();
		return initOkHttpClient(config);
	}
	
	public boolean initOkHttpClient(String proxyIp, int proxyPort) {
		OkHttpConfig config = new OkHttpConfig();
		config.setUseProxy(true);
		config.setProxyIp(proxyIp);
		config.setProxyPort(proxyPort);
		
		return initOkHttpClient(config);
	}

	public boolean initOkHttpClient(OkHttpConfig config) {
		
		try {
			OkHttpClient.Builder builder = new OkHttpClient.Builder();
			builder.sslSocketFactory(sslSocketFactory(), x509TrustManager());
			builder.hostnameVerifier(hostnameVerifier());
			builder.connectionPool(connectionPool());
			builder.retryOnConnectionFailure(false);
			builder.connectTimeout(config.getConnTimeout(), TimeUnit.SECONDS);
			builder.readTimeout(config.getReadTimeout(), TimeUnit.SECONDS);
			builder.writeTimeout(config.getWriteTimeout(),TimeUnit.SECONDS);
			builder.followRedirects(false);//禁制OkHttp的重定向操作，自己处理重定向
			builder.followSslRedirects(false);
			
			if(config.isUseProxy()) {
				if(StrUtil.isBlank(config.getProxyIp())) {
					return false;
				}
				if(config.getProxyPort() == 0) {
					return false;
				}
				Proxy proxy = proxy(config.getProxyIp(), config.getProxyPort());
				builder.proxy(proxy);
			}
			
			/*OkHttpClient client = new OkHttpClient.Builder()
			        .sslSocketFactory(sslSocketFactory(), x509TrustManager())
			        .hostnameVerifier(hostnameVerifier())
			        .connectionPool(connectionPool())
			        .proxy(proxy())
			        .retryOnConnectionFailure(false)//是否开启缓存
			        .connectTimeout(30, TimeUnit.SECONDS)
			        .readTimeout(30, TimeUnit.SECONDS)
			        .writeTimeout(30,TimeUnit.SECONDS)
			        .build();*/
			
			okHttpClient = builder.build();
			return true;
		} catch (Exception e) {
			log.error("初始化okhttpclient，异常。", e);
			return false;
		}
    }
	
	private Proxy proxy(String ip, int port) {
		// 创建代理服务器  
		InetSocketAddress addr = new InetSocketAddress(ip, port);
		//http 代理
		Proxy proxy = new Proxy(Proxy.Type.HTTP, addr);
		return proxy;
	}
	
	private ConnectionPool connectionPool() {
		ConnectionPool connectionPool = new ConnectionPool(5, 1, TimeUnit.SECONDS);
		return connectionPool;
	}
	
	private HostnameVerifier hostnameVerifier() {
		HostnameVerifier hostnameVerifier = new HostnameVerifier() {
            @Override
            public boolean verify(String hostname, SSLSession session) {
                return true;
            }
        };
        return hostnameVerifier;
	}
	
	private X509TrustManager x509TrustManager() {
        return new X509TrustManager() {
            @Override
            public void checkClientTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public void checkServerTrusted(X509Certificate[] x509Certificates, String s) throws CertificateException {
            }
            @Override
            public X509Certificate[] getAcceptedIssuers() {
                return new X509Certificate[0];
            }
        };
    }

    private SSLSocketFactory sslSocketFactory() {
        try {
            //信任任何链接
            //SSLContext sslContext = SSLContext.getInstance("SSL");
            SSLContext sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, new TrustManager[]{x509TrustManager()}, new SecureRandom());
            return sslContext.getSocketFactory();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    /**
     * get
     * @param url     请求的url
     * @param queries 请求的参数，在浏览器？后面的数据，没有可以传null
     * @return
     */
    public HttpResponse get(HttpRequest requestParam) {
    	
    	if(requestParam == null) {
			log.error("okhttp发送get请求，request为null。");
			return null;
		}
		if (StringUtils.isBlank(requestParam.getUrl())) {
			log.error("okhttp发送get请求，url为空。");
			return null;
		}
		if(okHttpClient == null) {
			log.error("okhttp发送get请求，okHttpClient为空。");
			return null;
		}
    	
		log.info("okhttp发送get请求。"+requestParam.toString());
		
        String urlAndParam = getRequestUrlWithParam(requestParam.getUrl(), requestParam.getParams());
        
        //构建请求
        Request.Builder requestBuilder = new Request.Builder().url(urlAndParam);
        
        //setRequestHeader(requestParam);
        
        // 设置请求头
		if (!CollectionUtils.isEmpty(requestParam.getHeadersForH2())) {
			for (Map.Entry<String, String> entry : requestParam.getHeadersForH2().entrySet()) {
				if(StrUtil.isBlank(entry.getValue())) {
					continue;
				}
				requestBuilder.addHeader(entry.getKey(), entry.getValue());
			}
		}
        
        Request request = requestBuilder.build();
        Response response = null;
        try { 
            response = okHttpClient.newCall(request).execute();
            if(response == null) {
            	log.error("okhttp发送get请求，响应为null。");
    			return null;
            }
            HttpResponse responseParam = new HttpResponse();
            
            String host = getRequestHost(request);
            responseParam.setHost(host);
            String scheme = getRequestScheme(request);
            responseParam.setScheme(scheme);
            
            Map<String, List<String>> headers = getHeaders(response.headers());
            responseParam.setHeaders(headers);
            log.info("okhttp发送get请求，响应。"+response.toString()+",headers:"+response.headers().toString());
            
            int status = response.code();
            responseParam.setCode(status);
            
            String body = null;
            if (response.isSuccessful()) {
            	body = response.body().string();
            }
            responseParam.setContent(body);
            
            return responseParam;
        } catch (Exception e) {
        	log.error("okhttp发送get请求，异常。", e);
            return null;
        } finally {
            if (response != null) {
                response.close();
            }
        }
    }
    
    /*private boolean setRequestHeader(HttpRequest requestParam) {
    	Map<String, String> headersForH2 = requestParam.getHeadersForH2();
    	// 设置请求头
		if (CollectionUtils.isEmpty(headersForH2)) {
			headersForH2 = requestParam.initHeaderForH2();
		}
		
		try {
			URL url = new URL(requestParam.getUrl());
			String host = url.getHost();
			String path = url.getPath();
			
			headersForH2.put(HeaderConstant.NAME_AUTHORITY, host);
			headersForH2.put(HeaderConstant.NAME_PATH, path);
			requestParam.setHeadersForH2(headersForH2);
			return true;
		} catch (MalformedURLException e) {
			log.error("okhttp设置请求header，异常。", e);
			return false;
		}
		
    }*/
    
    private String getRequestHost(Request request) {
    	if(request == null) {
    		return null;
    	}
    	HttpUrl httpUrl = request.url();
    	if(httpUrl == null) {
    		return null;
    	}
    	String host = httpUrl.host();
    	if(StrUtil.isBlank(host)) {
    		return null;
    	}
    	return host;
    }
    
    private String getRequestScheme(Request request) {
    	if(request == null) {
    		return null;
    	}
    	HttpUrl httpUrl = request.url();
    	if(httpUrl == null) {
    		return null;
    	}
    	String scheme = httpUrl.scheme();
    	if(StrUtil.isBlank(scheme)) {
    		return null;
    	}
    	return scheme;
    }
    
    private Map<String, List<String>> getHeaders(Headers okhttpHeaders){
    	if(okhttpHeaders == null) {
    		return null;
    	}
    	
    	try {
			Map<String, List<String>> headerMap = new HashMap<>();
			
			int headersLength = okhttpHeaders.size();
			for (int i = 0; i < headersLength; i++){
				String headerName = okhttpHeaders.name(i);
				String headerValue = okhttpHeaders.value(i);
				//System.out.println(headerName+": "+headerValue);
				if(StrUtil.isBlank(headerName)) {
					continue;
				}
				
				List<String> valueList;
				if(headerMap.containsKey(headerName)) {
					valueList = headerMap.get(headerName);
				}
				else {
					valueList = new ArrayList<>();
					headerMap.put(headerName, valueList);
				}
				
				valueList.add(headerValue);
			}
			
			return headerMap;
		} catch (Exception e) {
			log.error("okhttp获取header，异常。", e);
			return null;
		}
    }
    
    private String getRequestUrlWithParam(String requestUrl, Map<String, String> paramsMap) {
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
    
    public static void main(String[] args) {
    	
    	//String url = "https://httpbin.org/get";
    	String url = "https://nghttp2.org/httpbin/get";
    	//String url = "https://www.baidu.com/";
    	//String url = "https://www.ustc.edu.cn/";
    	//String url = "https://www.yale.edu/";
    	
    	HttpRequest request = new HttpRequest();
		request.setUrl(url);
		
		OkHttp2UtilForHttps okHttpUtil2 = new OkHttp2UtilForHttps();
		okHttpUtil2.initOkHttpClient();
		//okHttpUtil2.initOkHttpClient("1.58.10.231", 8118); 
		//okHttpUtil2.initOkHttpClient("115.85.206.193", 3012);
		HttpResponse resp = okHttpUtil2.get(request);
		System.out.println(resp.getContent());
	}

}
