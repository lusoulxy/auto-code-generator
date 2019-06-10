package com.silas.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.ColumnTypeHelper;
import com.silas.jdbc.DBConifguration;
import com.silas.util.GeneratorUtil;

public class Config {
	static {//设置模块参数
		//		添加 销售月表统计表 模块代码	
		tableName = "human_basic_info";//数据库表名
		entityName = "HumanBasicInfo";//对应实体类名
		packagePath = "com.silas.human.basicInfo";//包名
		module = "human";
		parenModuleName = "人物信息";//大模块名
		moduleName = "基本信息";//模块中文名，用于生成注释与日志相关
	}
	public static String tableName;//数据库表名
	public static String entityName;//对应实体类名
	public static String packagePath;//包名
	public static String module;//大模块名
	public static String parenModuleName;//大模块名
	public static String moduleName;//模块中文名，用于生成注释与日志相关
	
	public static String CODE_ID="DHC_WEB";//代码id
	
	public final static String DATABASE_NAME = "social_network";
	
	//JDBC与java类型对应哈希表
	public static Map<String,ColumnTypeHelper> JDBC_JAVA_MAP = new HashMap<String,ColumnTypeHelper>();
	//数据库配置类
	public static DBConifguration dbConifguration =new DBConifguration();
	static {//数据库参数配置
		//mySql
		String driverClassName = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/"+DATABASE_NAME+"?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"; // lhdw为数据库的SID
		String username = "root";//prices,hzshcxc
		String password = "root=1314@=xhh";//Prices1234,cxcHZSH1234
		
		//oracle
//		String driverClassName = "oracle.jdbc.OracleDriver";
//		String url = "jdbc:oracle:thin:@10.152.71.12:1521:lhdw"; // lhdw为数据库的SID
//		String username = "hzshcxc";//prices,hzshcxc
//		String password = "cxcHZSH1234";//Prices1234,cxcHZSH1234
		
//		 url: jdbc:sqlserver://10.152.71.192:1433;DatabaseName=Honeywell.MES
//		driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
//		String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//		String url = "jdbc:sqlserver://10.152.71.35:1433;DatabaseName=Honeywell.MES"; 
//		String username = "SMTP01 ";
//		String password = "Hzsh@smtp01";
		dbConifguration.setDriverClassName(driverClassName);
		dbConifguration.setPassword(password);
		dbConifguration.setUrl(url);
		dbConifguration.setUsername(username);
		
		//JDBC与java类型对应哈希表配置
		ColumnTypeHelper columnHelper = null;
		if(driverClassName.equals("oracle.jdbc.OracleDriver")) {//
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
		}else if(driverClassName.equals("com.mysql.jdbc.Driver")) {
			//JDBC与java类型对应哈希表设置
			columnHelper = new ColumnTypeHelper("java.lang.String");
			columnHelper.setImportStr("");
			columnHelper.setJdbcType("VARCHAR");
			JDBC_JAVA_MAP.put("VARCHAR", columnHelper);
			
			columnHelper = new ColumnTypeHelper("java.util.Date");
			columnHelper.setImportStr("\nimport java.util.Date;");
			columnHelper.setJdbcType("TIMESTAMP");
			JDBC_JAVA_MAP.put("DATE", columnHelper);
			JDBC_JAVA_MAP.put("DATETIME", columnHelper);
			
		}
	}
	
	public static String entityLowerName = "";//实体类名的小驼峰命名
	public static String path = "D:/temp";//代码文件输出路径
	public static List<Column> colList ;//根据表名获取的列元数据 
	public static Column primary_col;//根据表名获取的主键
	
	{
	}
	
	//其他数据处理
	{
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
	
}
