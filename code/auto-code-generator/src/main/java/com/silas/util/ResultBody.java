package com.silas.util;

import java.util.Map;

public class ResultBody {
	
	private String statusCode;//结果状态码
	private String message;//返回结果提示信息
	private Map<String,Object> resultMap;//
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Map<String, Object> getResultMap() {
		return resultMap;
	}
	public void setResultMap(Map<String, Object> resultMap) {
		this.resultMap = resultMap;
	}
	
	

}
