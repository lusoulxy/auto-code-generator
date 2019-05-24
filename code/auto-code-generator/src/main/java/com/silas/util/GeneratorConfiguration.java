package com.silas.util;

import com.silas.jdbc.DBConifguration;

public class GeneratorConfiguration {
	protected String tableName = "C_CODING";
	protected String entityName = "Coding";
	protected String path = "D:/temp";
	protected String packagePath = "com.silas.generator";
	
	
	protected String tab = "\t";
	protected String tab2 = "\t\t";
	protected String n = "\n";
	protected String n2 = "\n\n";
	
	protected DBConifguration dbConifguration =new DBConifguration();
	
	{
		String driverClassName = "oracle.jdbc.OracleDriver";
		String url = "jdbc:oracle:thin:@10.152.71.12:1521:lhdw"; // lhdw为数据库的SID
		String username = "hzshcxc";
		String password = "cxcHZSH1234";
		dbConifguration.setDriverClassName(driverClassName);
		dbConifguration.setPassword(password);
		dbConifguration.setUrl(url);
		dbConifguration.setUsername(username);
	}
}
