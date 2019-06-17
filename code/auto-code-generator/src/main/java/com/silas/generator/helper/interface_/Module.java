package com.silas.generator.helper.interface_;

/**
 * 描述java属性或方法,用于自动生成定义
 * @author WuGuoDa
 * @version 时间：2019年6月13日 下午1:46:23
 */
public class Module implements Comparable<Module>{
	private String name;//名称
	private String fullStr;//定义 完整字符串
	Integer order = 0;//排序序号
	
	public Module() {
	}
	public Module(String name) {
		this.name = name;
	}
	
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
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
	@Override
	public int compareTo(Module o) {
		if(order==0&&o.getOrder()==0&&name!=null&&o.getName()!=null) {
			return name.compareTo(o.getName());
		}
		return order-o.getOrder();
	}
}
