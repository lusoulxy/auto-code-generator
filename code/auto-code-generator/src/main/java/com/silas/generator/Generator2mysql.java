package com.silas.generator;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.fileStrHelper.ControllerHelper;
import com.silas.generator.helper.fileStrHelper.EntityHepler;
import com.silas.generator.helper.fileStrHelper.MapperJavaHelper;
import com.silas.generator.helper.fileStrHelper.MapperXMLHelper2Mysql;
import com.silas.generator.helper.fileStrHelper.RecordAddHtmlHepler;
import com.silas.generator.helper.fileStrHelper.RecordImportViewHtmlHepler;
import com.silas.generator.helper.fileStrHelper.RecordListHtmlHepler;
import com.silas.generator.helper.fileStrHelper.RecordSaveViewHtmlHepler;
import com.silas.generator.helper.fileStrHelper.RecordViewHtmlHepler;
import com.silas.generator.helper.fileStrHelper.ServiceHelper;
import com.silas.generator.helper.fileStrHelper.ServiceImplHelper;
import com.silas.jdbc.DBConifguration;
import com.silas.jdbc.DBHelper;
import com.silas.util.ResultBody;

public class Generator2mysql{

	// 自动生成代码
	public ResultBody generateCode() {
		ResultBody resultBody = new ResultBody();
		// 1.获取数据库连接
		Connection conn = DBHelper.getConnector(Config.dbConifguration);
		if(conn==null) {
			System.out.println("无法连接数据库！");
		}else {
			// 2.从数据库中读取表元数据
			List<Column> colList = getTableColumsMetaData(conn, Config.tableName);
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
			resultSet = dBMetaData.getPrimaryKeys(Config.DATABASE_NAME, Config.DATABASE_NAME, tableName);
			// 根据主键结果集设置表主键
			while (resultSet.next()) {
				Config.primary_col = new Column();
				Config.primary_col.setColumName(resultSet.getString("COLUMN_NAME"));
			}
			//释放资源
			DBHelper.release(resultSet);
			// 数据库表字段元数据
			resultSet = dBMetaData.getColumns(null, "%", tableName, "%");
			Column column = null;
//			ResultSetMetaData setMeta = resultSet.getMetaData();
//			for(int i=1;i<setMeta.getColumnCount();i++) {
//				System.out.println(setMeta.getColumnName(i));
//			}
			while (resultSet.next()) {
				column = new Column();
//				column.setTableName(resultSet.getString("TABLE_NAME"));//表名
				String columnName = resultSet.getString("COLUMN_NAME");
				System.out.print(columnName+",");
				column.setColumName(columnName);// 字段名
				String typeName = resultSet.getString("TYPE_NAME");// 字段类型
				column.setColumType(typeName);
				String remark = resultSet.getString("REMARKS");
				String columSize = resultSet.getString("COLUMN_SIZE");
				column.setColumSize(columSize);
				if(remark==null||remark.equals(""))
					remark=columnName;
				remark = remark.replaceAll("\n", " ");//去掉所有换行
				column.setRemark(remark);//字段注释
				System.out.println(column);
				//根据TYPE_NAME，设置JavaType，设置jdbcType
				if(typeName.equals("BIT")) {
					column.setColumnHelper(Config.JDBC_JAVA_MAP.get(typeName+"-"+columSize));
				}else {
					column.setColumnHelper(Config.JDBC_JAVA_MAP.get(typeName));
				}
				//判断是否为主键
				if(Config.primary_col.getColumName()!=null&&Config.primary_col.getColumName().equals(column.getColumName())) {
					column.setPk(true);
					//暂时默认ORACLE的数据库主键为VARCHAR2则主键自增 ，待完善 TODO
					if(typeName.equals("VARCHAR2")&&DBConifguration.IS_ORACEL) {
						column.setPkAuto(true);//主键自动生成
					}if(Config.dbConifguration.getDriverClassName().equals("com.mysql.jdbc.Driver")) {
						column.setPkAuto(true);//mysql默认主键自动生成
					}
					Config.primary_col =column;
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
//		// 2.生成mapper/EntityNameMapper.java文件
		new MapperJavaHelper().createFile();
		// 3.生成mapping/EntityNameMapper.xml文件
		new MapperXMLHelper2Mysql().createFile();
		// 4.生成service/EntityNameService.java文件
		new ServiceHelper().createFile();
		// 5.生成service/EntityNameImpl.java文件
		new ServiceImplHelper().createFile();
		// 6.生成controller/EntityNameController文件
		new ControllerHelper().createFile();
		// 7.生成template/entityName/list.html
		new RecordListHtmlHepler().createFile();
		// 8.生成template/entityName/updateView.html
		new RecordViewHtmlHepler().createFile();
		// 9.生成template/entityName/add.html
		new RecordAddHtmlHepler().createFile();
		// 10.生成template/entityName/importView.html
		new RecordImportViewHtmlHepler().createFile();
		// 11.生成template/entityName/save_view.html
		new RecordSaveViewHtmlHepler().createFile();
	}
	
}
