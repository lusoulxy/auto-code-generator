package com.silas.generator.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.ColumnTypeHelper;
import com.silas.jdbc.DBConfig;
import com.silas.util.GeneratorUtil;

public class Config {
	//设置模块参数
	static {
		//添加 人物信息备注 表 模块代码	
		parenModuleName = "用户";//父模块
		tableName = "user_accout";//数据库表名
		entityName = "UserAccout";//对应实体类名
		packagePath = "com.silas.user";//包名
		module = "user";//模块
		moduleName = "用户信息";//模块文名，用于生成注释与日志相关
	}
	public static String entityLowerName = "";//实体类名的小驼峰命名
	public static String path = "D:/temp";//代码文件输出路径
	
	public static List<Column> colList ;//根据表名获取的列元数据 
	public static Column primary_col;//根据表名获取的主键
	public static String tableName;//数据库表名
	public static String entityName;//对应实体类名
	public static String packagePath;//包名
	public static String module;//大模块名
	public static String parenModuleName;//大模块名
	public static String moduleName;//模块中文名，用于生成注释与日志相关
	//JDBC与java类型对应哈希表
	public static Map<String,ColumnTypeHelper> JDBC_JAVA_MAP = new HashMap<String,ColumnTypeHelper>();

	//其他数据处理
	static {
		//实体类名的小驼峰命名
		entityLowerName=GeneratorUtil.toLowerCaseFirstOne(entityName);
		//模块名
		if(module==null) {
			module=entityName.toLowerCase();
		}
		//模块中文名
		if(moduleName==null) {
			moduleName=entityName;
		}
	}
	
	//数据库配置类
	static {//数据库参数配置
		//JDBC与java类型对应哈希表配置
		ColumnTypeHelper columnHelper = null;
		if(DBConfig.DRIVERCLASSNAME.equals("oracle.jdbc.OracleDriver")) {//
			DBConfig.DBTYPE=1;
			//JDBC与java类型对应哈希表设置
			columnHelper = new ColumnTypeHelper("java.lang.String");
			columnHelper.setImportStr("");
			columnHelper.setJdbcType("VARCHAR");
			JDBC_JAVA_MAP.put("VARCHAR", columnHelper);
			JDBC_JAVA_MAP.put("VARCHAR2", columnHelper);
			JDBC_JAVA_MAP.put("NVARCHAR2", columnHelper);
			columnHelper = new ColumnTypeHelper("java.math.BigDecimal");
			columnHelper.setImportStr("\nimport java.math.BigDecimal;");
			columnHelper.setJdbcType("DECIMAL");
			JDBC_JAVA_MAP.put("NUMBER", columnHelper);
			
			columnHelper = new ColumnTypeHelper("java.util.Date");
			columnHelper.setImportStr("\nimport java.util.Date;");
			columnHelper.setJdbcType("TIMESTAMP");
			JDBC_JAVA_MAP.put("DATE", columnHelper);
		}else if(DBConfig.DRIVERCLASSNAME.equals("com.mysql.jdbc.Driver")) {
			DBConfig.DBTYPE=2;
			//JDBC与java类型对应哈希表设置
			columnHelper = new ColumnTypeHelper("java.lang.String");
			columnHelper.setImportStr("");
			columnHelper.setJdbcType("VARCHAR");
			JDBC_JAVA_MAP.put("VARCHAR", columnHelper);
			JDBC_JAVA_MAP.put("CHAR", columnHelper);
			
			columnHelper = new ColumnTypeHelper("java.util.Date");
			columnHelper.setImportStr("\nimport java.util.Date;");
			columnHelper.setJdbcType("TIMESTAMP");
			JDBC_JAVA_MAP.put("DATE", columnHelper);
			JDBC_JAVA_MAP.put("DATETIME", columnHelper);

			columnHelper = new ColumnTypeHelper("java.lang.Integer");
			columnHelper.setImportStr("");
			columnHelper.setJdbcType("INTEGER");
			JDBC_JAVA_MAP.put("INT", columnHelper);

			columnHelper = new ColumnTypeHelper("java.math.BigDecimal");
			columnHelper.setImportStr("\nimport java.math.BigDecimal;");
			columnHelper.setJdbcType("DECIMAL");
			JDBC_JAVA_MAP.put("DECIMAL", columnHelper);
			
			columnHelper = new ColumnTypeHelper("java.lang.Boolean");
			columnHelper.setImportStr("");
			columnHelper.setJdbcType("BIT");
			JDBC_JAVA_MAP.put("BIT-1", columnHelper);
			
			
		}
	}
	
}
