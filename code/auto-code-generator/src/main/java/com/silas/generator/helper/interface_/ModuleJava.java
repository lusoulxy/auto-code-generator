package com.silas.generator.helper.interface_;

/**
 * 描述java属性或方法,用于自动生成定义
 * @author WuGuoDa
 * @version 时间：2019年6月13日 下午1:46:23
 */
public class ModuleJava extends ModuleImport{
	private String name;//名称
	private String fullStr;//定义 完整字符串
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFullStr() {
		return fullStr;
	}
	public void setFullStr(String fullStr) {
		this.fullStr = fullStr;
	}
}
