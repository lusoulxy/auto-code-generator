package com.silas.generator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.ColumnTypeHelper;
import com.silas.jdbc.DBConifguration;
import com.silas.util.GeneratorUtil;

public class Config {
	{//设置模块参数
//		添加 ERP物料编码 模块代码	
		tableName = "C_ERP_CODE";//数据库表名
		entityName = "ErpCode";//对应实体类名
		packagePath = "com.hzsh.configuration";//包名
		module = "configuration";//大模块名
		parenModuleName = "配置管理";
		moduleName = "ERP物料编码";//模块中文名，用于生成注释与日志相关
	}
	public static String tableName;//数据库表名
	public static String entityName;//对应实体类名
	public static String packagePath;//包名
	public static String module;//大模块名
	public static String parenModuleName;//大模块名
	public static String moduleName;//模块中文名，用于生成注释与日志相关
	
	public static String CODE_ID="DHC_WEB";//代码id
	
	//数据库配置类
	public static DBConifguration dbConifguration =new DBConifguration();
	{//数据库参数配置
		String driverClassName = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@10.152.71.12:1521:lhdw"; // lhdw为数据库的SID
		String username = "prices";//prices,hzshcxc
		String password = "Prices1234";//Prices1234,cxcHZSH1234
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
		//判断数据库类型
		if(driverClassName.equals("oracle.jdbc.OracleDriver")) {
			DBConifguration.IS_ORACEL=true;
		}
	}
	
	public static String entityLowerName = "";//实体类名的小驼峰命名
	public static String path = "D:/temp";//代码文件输出路径
	public static List<Column> colList ;//根据表名获取的列元数据 
	public static Column primary_col = new Column();//根据表名获取的主键
	
	//JDBC与java类型对应哈希表
	public static Map<String,ColumnTypeHelper> JDBC_JAVA_MAP = new HashMap<String,ColumnTypeHelper>();
	{//JDBC与java类型对应哈希表配置
		if(DBConifguration.IS_ORACEL) {//
			ColumnTypeHelper columnHelper = null;
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
		}
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
