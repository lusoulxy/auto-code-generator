package com.silas.generator;

import java.util.List;

import com.silas.jdbc.DBConifguration;
import com.silas.util.StringUtils;

public abstract class Config {
//	public static String tableName = "C_CODING";
//	public static String entityName = "Coding";
	public static String tableName = "T_TEST_MY";//数据库表名
	public static String entityName = "MyTest";//对应实体类名
	public static String entityLowerName = "";//实体类名的小驼峰命名
	public static String path = "D:/temp";//代码文件输出路径
	public static String packagePath = "com.hzsh.test";//包名
	public static String module = "test";//模块名
	public static String moduleName = "测试";//模块中文名，用于生成注释与日志相关
	public static List<Column> colList ;//根据表名获取的列元数据 
	
	
	public static String tab = "\t";
	public static String tab2 = "\t\t";
	public static String tab3 = "\t\t\t";
	public static String tab4 = "\t\t\t\t";
	public static String n = "\n";
	public static String n2 = "\n\n";
	
	public static Column primary_col = new Column();//根据表名获取的主键
	
	public static DBConifguration dbConifguration =new DBConifguration();
	
	{
		String driverClassName = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@10.152.71.12:1521:lhdw"; // lhdw为数据库的SID
		String username = "prices";//prices,hzshcxc
		String password = "Prices1234";//Prices1234,cxcHZSH1234
		dbConifguration.setDriverClassName(driverClassName);
		dbConifguration.setPassword(password);
		dbConifguration.setUrl(url);
		dbConifguration.setUsername(username);
		
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
