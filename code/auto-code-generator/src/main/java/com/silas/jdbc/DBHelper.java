package com.silas.jdbc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBHelper {
	
	// 获得数据库连接
	public static Connection getConnector(DBConifguration dbConifguration) {
		return getConnector(dbConifguration.getDriverClassName(), dbConifguration.getUrl(), dbConifguration.getUsername(), dbConifguration.getPassword());
	}

	// 获得数据库连接
	public static Connection getConnector(String driverClassName, String url, String username, String password) {
		Connection connection = null;
		try {
			Class.forName(driverClassName).newInstance();
//			connection = DriverManager.getConnection(url, username, password);
			Properties props =new Properties();
			props.put("user", username);
			props.put("password", password);
			props.put("remarksReporting","true");
			connection = DriverManager.getConnection(url,props);
		} catch (Exception e) {
			e.printStackTrace();
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
		return connection;
	}

	//释放资源
	public static void release(ResultSet resultset) {
		 release(resultset,null,null);
	}
	public static void release(Statement statement) {
		 release(null,statement,null);
	}
	public static void release(Connection connection) {
		 release(null,null,connection);
	}
	public static void release(ResultSet resultset, Statement statement, Connection connection) {
		if (resultset != null) {
			try {
				resultset.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (statement != null) {
			try {
				statement.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
