package com.silas.generator.helper;

import com.silas.util.StringUtils;

public class Column {
	private String tableName;// 所在的表名
	private String columName;// 字段名
	private String columType;// 字段类型
	private String columSize;// 长度
	private boolean isPk = false;// 是否为主键
	private boolean isPkAuto = false;///是否自动生成主键
	private String remark;//注释

	private ColumnTypeHelper columnTypeHelper;
	
	public boolean isNotShow() {
		if(remark!=null) {
			return remark.indexOf("-not_show")>0;
		}else 
			return false;
	}
	
	public String getColumSize() {
		return columSize;
	}

	public void setColumSize(String columSize) {
		this.columSize = columSize;
	}

	public boolean isPkAuto() {
		return isPkAuto;
	}

	public void setPkAuto(boolean isPkAuto) {
		this.isPkAuto = isPkAuto;
	}

	public String getEntityField() {
		return StringUtils.getLowerCamelCase(columName.toLowerCase());
	}
	
	public ColumnTypeHelper getColumnTypeHelper() {
		return columnTypeHelper;
	}

	public void setColumnHelper(ColumnTypeHelper columnTypeHelper) {
		this.columnTypeHelper = columnTypeHelper;
	}

	public String getRemarkOrigin() {
		return remark;//
	}
	
	public String getRemark() {
		return remark.replaceAll("-not_show", "");//去掉-not_show字眼
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

	public String getJdbcType() {
		// TODO Auto-generated method stub
		return getColumnTypeHelper().getJdbcType();
	}

	public String getJavaType() {
		// TODO Auto-generated method stub
		return getColumnTypeHelper().getClassName();
	}
	
	public String getEntityFieldUpperFisrt() {
		return StringUtils.toUpperCaseFirstOne(StringUtils.getLowerCamelCase(columName.toLowerCase()));
	}

	@Override
	public String toString() {
		return "Column [tableName=" + tableName + ", columName=" + columName + ", columType=" + columType
				+ ", columSize=" + columSize + ", isPk=" + isPk + ", isPkAuto=" + isPkAuto + ", remark=" + remark
				+ ", columnTypeHelper=" + columnTypeHelper + "]";
	}
	
	
}
