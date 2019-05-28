package com.silas.generator;

import com.silas.util.StringUtils;

public class Column {
	private String tableName;// 所在的表名
	private String columName;// 字段名
	private String columType;// 字段类型
	private boolean isPk = false;// 是否为主键
	private String jdbcType;
	private String JavaType;
	private String classNameStr;
	
	
	// 小驼峰命名法
	public String getLowerCamelCaseName() {
		if (columName != null)
			return StringUtils.getLowerCamelCase(columName);
		else
			return null;
	}

	// 大驼峰命名法
	public String getUperCamelCaseName() {
		if (columName != null)
			return StringUtils.getUperCamelCase(columName);
		else
			return null;
	}

	
	
	public String getJdbcType() {
		return jdbcType;
	}

	public void setJdbcType(String jdbcType) {
		this.jdbcType = jdbcType;
	}

	public String getJavaType() {
		return JavaType;
	}

	public void setJavaType(String javaType) {
		if(javaType!=null) {
			this.classNameStr = javaType.substring(javaType.lastIndexOf("."));
		}
		this.JavaType = javaType;
	}

	public boolean isPk() {
		return isPk;
	}

	public void setPk(boolean isPk) {
		this.isPk = isPk;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public String getColumName() {
		return columName;
	}

	public void setColumName(String columName) {
		this.columName = columName;
	}

	public String getColumType() {
		return columType;
	}

	public void setColumType(String columType) {
		this.columType = columType;
	}

	@Override
	public String toString() {
		return "Column [columName=" + columName + ", columType=" + columType + "]";
	}
}
