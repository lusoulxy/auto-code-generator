package com.silas.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Date;

import com.silas.generator.Config;
import com.silas.generator.helper.Column;
import com.silas.generator.helper.ColumnTypeHelper;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.fileStrHelper.EntityHepler;

public class GeneratorUtil {

	// 生成文件
	public static void createFile(OutPutFile outPutFile) {
		FileWriter writer = null;
		try {
			outPutFile.getFile().getParentFile().mkdirs();// 创建文件,以覆盖形式
			writer = new FileWriter(outPutFile.getFile());
			writer.write(outPutFile.getFileOutputStr());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		System.out.println("生成文件：" + outPutFile.getFileName());
	}

	// 根据文件完整名（包括路径）和要输出的文本内容获得File
	public static OutPutFile getOutPutFile(String fileFullName, String fileOutputStr) {
		OutPutFile outPutFile = new OutPutFile();
		File file = new File(fileFullName);
		outPutFile.setFile(file);
		outPutFile.setFileName(fileFullName);
		outPutFile.setFileOutputStr(fileOutputStr);
		return outPutFile;
	};

	public static String generatorFieldName(Column column) {
		String fieldName = null;
		if (column != null || column.getColumName() != null) {
			// TODO 待完善
			fieldName = column.getColumName().toLowerCase();
		} else {
			System.out.println("字段为空！");
			;
		}
		return fieldName;
	}

	public static String getPackage(String packagePath) {
		if(packagePath==null)
			return null;
		return "/" + packagePath.replace(".", "/") + "/";
	}

	// 首字母转大写
	public static String toUpperCaseFirstOne(String s) {
		if(s==null)
			return null;
		if (Character.isUpperCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	// 首字母转小写
	public static String toLowerCaseFirstOne(String s) {
		if(s==null)
			return null;
		if (Character.isLowerCase(s.charAt(0)))
			return s;
		else
			return (new StringBuilder()).append(Character.toLowerCase(s.charAt(0))).append(s.substring(1)).toString();
	}
	
	//根据TYPE_NAME，设置jdbcType
	public static String getJdbcType(String columType) {
		// 属性类型
		if (columType.equals("VARCHAR2") || columType.equals("VARCHAR")||columType.equals("NVARCHAR2")) {// 字符类型
			return "VARCHAR";
		} else if (columType.equals("NUMBER")) {
			return "DECIMAL";
		} else if (columType.equals("DATE")) {
			return "TIMESTAMP";
		}
		return null;
	}

	//根据TYPE_NAME，设置JavaType
	public static String getJavaType(String columType) {
		// 属性类型
		if (columType.equals("VARCHAR2") || columType.equals("VARCHAR")||columType.equals("NVARCHAR2")) {// 字符类型
			return "java.lang.String";
		} else if (columType.equals("NUMBER")) {
			return "java.math.BigDecimal";
		} else if (columType.equals("DATE")) {
			return "java.util.Date";
		}
		return null;
	}

	public static ColumnTypeHelper getColumnHelper(String columType) {
		return Config.JDBC_JAVA_MAP.get(columType);
	}

}
