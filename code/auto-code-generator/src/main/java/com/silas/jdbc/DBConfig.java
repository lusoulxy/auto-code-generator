package com.silas.jdbc;

public class DBConfig {
	public static String DRIVERCLASSNAME ;//数据库驱动
	public static String URL;//数据连接URL
	public static String USERNAME ;//用户名
	public static String PASSWORK;//密码
	public static String DATABASE_NAME;//数据库实例名
	public static int DBTYPE;//数据库数据
	
	static {
		DBTYPE=1;
		DATABASE_NAME = "HZSH_APS10";
		
		DRIVERCLASSNAME = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
		URL = "jdbc:sqlserver://10.152.71.24:1433"; // lhdw为数据库的SID
		USERNAME = "ApsGuest";//prices,hzshcxc
		PASSWORK = "ApsGuest";//Prices1234,cxcHZSH1234
//		String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//		String url = "jdbc:sqlserver://10.152.71.35:1433;DatabaseName=Honeywell.MES"; 
//		String username = "SMTP01 ";
//		String password = "Hzsh@smtp01";
		
//		DRIVERCLASSNAME = "com.mysql.jdbc.Driver";
//		URL = "jdbc:mysql://localhost:3306/"+DATABASE_NAME+"?useUnicode=true&characterEncoding=utf8&serverTimezone=UTC"; // lhdw为数据库的SID
//		USERNAME = "root";//prices,hzshcxc
//		PASSWORK = "root=1314@=xhh";//Prices1234,cxcHZSH1234
	}
	

//	String driverClassName = "oracle.jdbc.OracleDriver";
//	String url = "jdbc:oracle:thin:@10.152.71.12:1521:lhdw"; // lhdw为数据库的SID
//	String username = "hzshcxc";//prices,hzshcxc
//	String password = "cxcHZSH1234";//Prices1234,cxcHZSH1234
	
//	 url: jdbc:sqlserver://10.152.71.192:1433;DatabaseName=Honeywell.MES
//	driver-class-name: com.microsoft.sqlserver.jdbc.SQLServerDriver
//	String driverClassName = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
//	String url = "jdbc:sqlserver://10.152.71.35:1433;DatabaseName=Honeywell.MES"; 
//	String username = "SMTP01 ";
//	String password = "Hzsh@smtp01";
	
}
