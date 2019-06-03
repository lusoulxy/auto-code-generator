package com.silas.generator.helper;

import com.silas.util.StringUtils;

public class Column {
	private String tableName;// 所在的表名
	private String columName;// 字段名
	private String columType;// 字段类型
	private boolean isPk = false;// 是否为主键
	private boolean isPkAuto = false;///是否自动生成主键
	private String remark;//注释

	private ColumnTypeHelper columnTypeHelper;
	

	public boolean isPkAuto() {
		return isPkAuto;
	}

	public void setPkAuto(boolean isPkAuto) {
		this.isPkAuto = isPkAuto;
	}

	public String getEntityField() {
		return StringUtils.getLowerCamelCase(columName);
	}
	
	public ColumnTypeHelper getColumnTypeHelper() {
		return columnTypeHelper;
	}

	public void setColumnHelper(ColumnTypeHelper columnTypeHelper) {
		this.columnTypeHelper = columnTypeHelper;
	}


	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

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

	public String getJdbcType() {
		// TODO Auto-generated method stub
		return getColumnTypeHelper().getJdbcType();
	}

	public String getJavaType() {
		// TODO Auto-generated method stub
		return getColumnTypeHelper().getClassName();
	}
}
