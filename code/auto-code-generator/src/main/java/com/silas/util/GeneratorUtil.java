package com.silas.util;

import java.util.Date;

public class GeneratorUtil {
	
	public static String generatorFieldName(Column column){
		String fieldName = null;
		if(column!=null||column.getColumName()!=null) {
			//TODO 待完善
			fieldName = column.getColumName().toLowerCase();
		}else {
			System.out.println("字段为空！");;
		}
		return fieldName;
	}

	public static String getPackage(String packagePath) {
		return "/"+packagePath.replace(".", "/")+"/";
	}

	
	public static EntityHepler getHepler(Column column) {
		EntityHepler helper = null;
		
		if(column!=null&&column.getColumType()!=null&&column.getColumName()!=null) {
			helper = new EntityHepler();
			String columType = column.getColumType();
			
			//TODO 待完善
			//属性名
			helper.setFieldName(column.getColumName().toLowerCase());
			
			//TODO 待完善
			//属性类型
			if(columType.equals("VARCHAR2")||columType.equals("VARCHAR")) {//字符类型
				helper.setTypeName("String");
				helper.setImportStr("");
			}else if(columType.equals("NUMBER")) {
				helper.setTypeName("BigDecimal");
				helper.setImportStr("import java.math.BigDecimal;\n");
			}else if(columType.equals("DATE")) {
				helper.setTypeName("DATE");
				helper.setImportStr("import java.util.Date;\n");
			}
			//getter和setter方法名
			helper.setMethodGetter("get"+toUpperCaseFirstOne(helper.getField()));
			helper.setMethodSetter("set"+toUpperCaseFirstOne(helper.getField()));
			
		}else {
			System.out.println("字段为空！");;
		}
		return helper;
	}
	
	//首字母转大写
	public static String toUpperCaseFirstOne(String s){
	  if(Character.isUpperCase(s.charAt(0)))
	    return s;
	  else
	    return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}

}
