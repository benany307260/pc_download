package com.montnets.rcos_service.controller.response;

import com.alibaba.fastjson.JSON;

public class Result<T> {
	
	private String result;
	
	private String msg;
	
	private T content;
	
	private String timestamp;
	
	private String sign;

	
	public Result(String result, String msg, T content)
	{
		this.result = result;
		this.msg = msg;
		this.content = content;
	}
	
	public static Result<String> success()
	{
		return new Result<String>("0", "success", "");
	}
	
	public static <T> Result<T> success(T content)
	{
		return new Result<T>("0", "success", content);
	}
	
	public static Result<String> failure(ErrorCode errorCode)
	{
		return new Result<String>(errorCode.getCode(), errorCode.getMessage(), "");
	}
	
	public static <T> Result<T> failure(ErrorCode errorCode, T content)
	{
		return new Result<T>(errorCode.getCode(), errorCode.getMessage(), content);
	}
	
	public static String failureReturnJson(ErrorCode errorCode, String msg)
	{
		Result<String> result = new Result<String>(errorCode.getCode(), msg, "");
		return JSON.toJSONString(result);
	}
	
	public static Result<String> failure(ErrorCode errorCode, String msg)
	{
		Result<String> result = new Result<String>(errorCode.getCode(), msg, "");
		return result;
	}
	

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public T getContent() {
		return content;
	}

	public void setContent(T content) {
		this.content = content;
	}

	public String getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}


	
	
}
