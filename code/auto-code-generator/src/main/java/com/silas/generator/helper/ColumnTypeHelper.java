package com.silas.generator.helper;

public class ColumnTypeHelper {
	
	private String className;//类名
	private String classFullName;//类完整名
	private String importStr;//根据类名，导入包
	private String jdbcType;//JDBC类型
	
	public ColumnTypeHelper() {
	}
	
	public ColumnTypeHelper(String classFullName) {
		this.classFullName = classFullName;
		this.className = classFullName.substring(classFullName.lastIndexOf(".")+1);
	}
	
	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassFullName() {
		return classFullName;
	}
	public void setClassFullName(String classFullName) {
		this.classFullName = classFullName;
	}
	public String getImportStr() {
		return importStr;
	}
	public void setImportStr(String importStr) {
		this.importStr = importStr;
	}
	
}
