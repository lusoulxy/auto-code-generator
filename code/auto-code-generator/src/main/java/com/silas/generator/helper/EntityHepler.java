package com.silas.generator.helper;

import java.util.List;
import com.silas.generator.Column;
import com.silas.generator.Config;
import com.silas.generator.GeneratorUtil;
import com.silas.generator.OutPutFile;

public class EntityHepler implements CreateFileHelper {
	
	private String field ;//属性名
	private String className ;//类名
	private String MethodGetter ;
	private String MethodSetter ;
	private String importStr ;
	private String metodGetSet;
	
	// 1.生成实体类entity/EntityName.java文件
	public void createFile(){
		GeneratorUtil.createFile(getOutPutFile());
	}
	
	// 准备实体类entity/EntityName.java文件
	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "entity/" + entityName + ".java";
		// 类包名
		String packageStr = "package " + packagePath + ".entity;" + n2;
		// import导入类
		StringBuilder importStr = new StringBuilder();
		// 类开始
		String classStart = "public class " + entityName + " {" + n;
		// 属性定义
		StringBuilder fieldStr = new StringBuilder();
		// 属性的Getter\Setter方法
		StringBuilder metodGetSet = new StringBuilder();
		// 遍历字段，生成属性字义与相关方法
		for (Column column : colList) {
			// 根据字段生成相关字符
			EntityHepler helper = getHepler(column);
			// 生成属性定义
			fieldStr.append(tab + "private " + helper.getClassName() + " " + helper.getField() + ";" + n2);
			// 生成getter方法
			metodGetSet.append(helper.getMetodGetSet());
			importStr.append(helper.getImportStr()) ;
		}
		// 类结束
		String classEnd = "}";
		// 拼接
		fileOutputStr = packageStr + importStr + classStart + fieldStr + metodGetSet + classEnd;

		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}

	
	
	public String getImportStr() {
		return importStr;
	}
	public void setImportStr(String importStr) {
		this.importStr = importStr;
	}
	public String getField() {
		return field;
	}
	public void setField(String field) {
		this.field = field;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
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
	
	public EntityHepler getHepler(Column column) {
		EntityHepler helper = null;

		if (column != null && column.getColumType() != null && column.getColumName() != null) {
			helper = new EntityHepler();
			String columType = column.getColumType();
			// TODO 待完善
			// 属性名
			helper.setField(column.getColumName().toLowerCase());
			// getter和setter方法名
			helper.setMethodGetter("get" + GeneratorUtil.toUpperCaseFirstOne(helper.getField()));
			helper.setMethodSetter("set" + GeneratorUtil.toUpperCaseFirstOne(helper.getField()));


			// TODO 待完善
			// 属性的Getter\Setter方法
			StringBuilder metodGetSet = new StringBuilder();
			// 属性类型
			if (columType.equals("VARCHAR2") || columType.equals("VARCHAR")|| columType.equals("NVARCHAR2")) {// 字符类型
				helper.setClassName("String");
				helper.setImportStr("");
				// 生成getter方法
				metodGetSet.append(tab + "public String " + helper.getMethodGetter() + "() {" + n);
				metodGetSet.append(tab2 + "return " + helper.getField() + ";" + n);
				metodGetSet.append(tab + "}" + n2);
				// 生成setter方法
				metodGetSet.append(tab + "public void " + helper.getMethodSetter() + "(String " + helper.getField() + ") {" + n);
				metodGetSet.append(tab2 + "this." + helper.getField() + " = " + helper.getField() + " == null ? null : "
						+ helper.getField() + ".trim();" + n);
				metodGetSet.append(tab + "}" + n2);

			} else if (columType.equals("NUMBER")) {
				helper.setClassName("BigDecimal");
				helper.setImportStr("import java.math.BigDecimal;\n");
				
				// 生成getter方法
				metodGetSet.append(tab + "public BigDecimal " + helper.getMethodGetter() + "() {" + n);
				metodGetSet.append(tab2 + "return " + helper.getField() + ";" + n);
				metodGetSet.append(tab + "}" + n2);
				// 生成setter方法
				metodGetSet.append(tab + "public void " + helper.getMethodSetter() + "(BigDecimal " + helper.getField() + ") {" + n);
				metodGetSet.append(tab2 + "this." + helper.getField() + " = " + helper.getField() + ";" + n);
				metodGetSet.append(tab + "}" + n2);
			} else if (columType.equals("DATE")) {
				helper.setClassName("Date");
				helper.setImportStr("import java.util.Date;\n");
				
				// 生成getter方法
				metodGetSet.append(tab + "public Date " + helper.getMethodGetter() + "() {" + n);
				metodGetSet.append(tab2 + "return " + helper.getField() + ";" + n);
				metodGetSet.append(tab + "}" + n2);
				// 生成setter方法
				metodGetSet.append(tab + "public void " + helper.getMethodSetter() + "(Date " + helper.getField() + ") {" + n);
				metodGetSet.append(tab2 + "this." + helper.getField() + " = " + helper.getField() + ";" + n);
				metodGetSet.append(tab + "}" + n2);
			}
			helper.setMetodGetSet(metodGetSet.toString());

		} else {
			System.out.println("字段为空！");
			;
		}
		return helper;
	}

	public String getMetodGetSet() {
		return metodGetSet;
	}

	public void setMetodGetSet(String metodGetSet) {
		this.metodGetSet = metodGetSet;
	}
	
	
}
