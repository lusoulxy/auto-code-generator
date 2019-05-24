package com.silas.util;

public class EntityHepler {
	private String field ;
	private String typeName ;
	private String MethodGetter ;
	private String MethodSetter ;
	private String importStr ;
	
	public String getImportStr() {
		return importStr;
	}
	public void setImportStr(String importStr) {
		this.importStr = importStr;
	}
	public String getField() {
		return field;
	}
	public void setFieldName(String field) {
		this.field = field;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public String getMethodGetter() {
		return MethodGetter;
	}
	public void setMethodGetter(String methodGetter) {
		MethodGetter = methodGetter;
	}
	public String getMethodSetter() {
		return MethodSetter;
	}
	public void setMethodSetter(String methodSetter) {
		MethodSetter = methodSetter;
	}
}
