package com.silas.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silas.generator.helper.ControllerHelper;
import com.silas.generator.helper.EntityHepler;
import com.silas.generator.helper.MapperJavaHelper;
import com.silas.generator.helper.MapperXMLHelper;
import com.silas.generator.helper.RecordListHtmlHepler;
import com.silas.generator.helper.ServiceHelper;
import com.silas.generator.helper.ServiceImplHelper;
import com.silas.jdbc.DBHelper;
import com.silas.util.ResultBody;

public class Generator extends Config {
	public static void main(String[] args) {
		new Generator().generateCode();
	}

	// 自动生成代码
	public ResultBody generateCode() {
		ResultBody resultBody = new ResultBody();
		// 1.获取数据库连接
		Connection conn = DBHelper.getConnector(dbConifguration);
		if(conn==null) {
			System.out.println("无法连接数据库！");
		}else {
			// 2.从数据库中读取表元数据
			List<Column> colList = getTableColumsMetaData(conn, tableName);
			// 3.解析表的元数据，根据元数据生成对应代码文件
			if(colList!=null||colList.size()>0) {
				Config.colList=colList;
				createCodeFiles();
			}else {
				System.out.println("表不存在或无法获取到元数据！");
			}
			// 4.释放数据库资源
			DBHelper.release(conn);
			System.out.println("done!");
		}
		return resultBody;
	}

	// 从数据库中读取表元数据
	public List<Column> getTableColumsMetaData(Connection connection, String tableName) {
		// TODO Auto-generated method stub
		ResultSet resultSet = null;
		DatabaseMetaData dBMetaData = null;
		List<Column> list = new ArrayList<Column>();
		try {
			// 数据库元数据
			dBMetaData = connection.getMetaData();
			// 根据表名获得主键结果集
			resultSet = dBMetaData.getPrimaryKeys(null, null, tableName);
			// 根据结果集元数据打印内容
			while (resultSet.next()) {
				primary_col.setColumName(resultSet.getString("COLUMN_NAME"));
			}
			//释放资源
			DBHelper.release(resultSet);
			// 数据库表元数据
			resultSet = dBMetaData.getColumns(null, "%", tableName, "%");
			Column column = null;
			while (resultSet.next()) {
				column = new Column();
				column.setColumName(resultSet.getString("COLUMN_NAME"));// 字段名
				column.setColumType(resultSet.getString("TYPE_NAME"));// 字段类型
				//根据TYPE_NAME，设置JavaType
				column.setJavaType(GeneratorUtil.getJavaType(column.getColumType()));
				//根据TYPE_NAME，设置jdbcType
				column.setJdbcType(GeneratorUtil.getJdbcType(column.getColumType()));
				//判断是否为主键
				if(primary_col.getColumName()!=null&&this.primary_col.getColumName().equals(column.getColumName())) {
					column.setPk(true);
					primary_col =column;
				}
				list.add(column);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(resultSet, null, connection);
		}
		return list;
	}

	// 解析表的字段元数据，根据元数据生成对应代码文件
	private void createCodeFiles() {
		// 1.生成实体类entity/EntityName.java文件
		new EntityHepler().createFile();
		// 2.生成mapper/EntityNameMapper.java文件
		new MapperJavaHelper().createFile();
		// 3.生成mapping/EntityNameMapper.xml文件
		new MapperXMLHelper().createFile();
		// 4.生成service/EntityNameService.java文件
		new ServiceHelper().createFile();
		// 5.生成service/EntityNameImpl.java文件
		new ServiceImplHelper().createFile();
		// 6.生成controller/EntityNameController文件
		new ControllerHelper().createFile();
		// 7.生成template/entityName/add.html
		new RecordListHtmlHepler().createFile();
		// 8.生成template/entityName/updateview.html
		// 9.生成template/entityName/list.html

	}
	
}
