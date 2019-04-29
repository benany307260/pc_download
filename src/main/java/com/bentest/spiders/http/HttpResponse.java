package com.bentest.spiders.http;

import java.util.List;
import java.util.Map;

public class HttpResponse {
	
	private int code;
	
	private String content;
	
	private Map<String, List<String>> headers;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Map<String, List<String>> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, List<String>> headers) {
		this.headers = headers;
	}

	
}
