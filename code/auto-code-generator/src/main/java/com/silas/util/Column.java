package com.silas.util;

public class Column {
	private String tableName ;//所在的表名
	private String columName ;//字段名
	private String columType ;//字段类型
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
