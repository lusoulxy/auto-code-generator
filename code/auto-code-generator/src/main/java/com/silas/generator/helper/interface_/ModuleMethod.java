package com.silas.generator.helper.interface_;

/**
 * 描述方法,用于自动生成方法定义
 * @author WuGuoDa
 * @version 时间：2019年6月13日 下午1:46:23
 */
public class ModuleMethod extends ModuleImport{
	private String methodName;//方法名
	private String methodFullStr;//方法定义，完整字符串
	
	
	public String getMethodName() {
		return methodName;
	}
	public void setMethodName(String methodName) {
		this.methodName = methodName;
	}

	public String getMethodFullStr() {
		return methodFullStr;
	}

	public void setMethodFullStr(String methodFullStr) {
		this.methodFullStr = methodFullStr;
	}
}
