package com.silas.generator;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.silas.jdbc.DBHelper;
import com.silas.util.Column;
import com.silas.util.EntityHepler;
import com.silas.util.GeneratorConfiguration;
import com.silas.util.GeneratorUtil;
import com.silas.util.ResultBody;

public class Generator extends GeneratorConfiguration{

	public static void main(String[] args) {
		new Generator().generateCode();
	}
	
	public ResultBody generateCode() {
		ResultBody resultBody = new ResultBody();
		// 1.获取数据库连接
		Connection conn = DBHelper.getConnector(dbConifguration);
		// 2.从数据库中读取表元数据
		List<Column> cloList = getTableColumsMetaData(conn, tableName);
		// 3.解析表的元数据，根据元数据生成对应代码文件
		createCodeFiles(cloList);
		// 4.释放数据库资源
		DBHelper.release(conn);
		System.out.println("done!");
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
			// 数据库表元数据
			resultSet = dBMetaData.getColumns(null, "%", tableName, "%");
			Column column = null;
			while (resultSet.next()) {
				column = new Column();
				column.setColumName(resultSet.getString("COLUMN_NAME"));//字段名
				column.setColumType(resultSet.getString("TYPE_NAME"));//字段类型
				list.add(column);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBHelper.release(resultSet, null, connection);
		}
		return list;
	}

//	Properties props =newProperties();
//
//	props.put("remarksReporting","true");
	
	// 获得数据库连接
	private Connection getConnector() {

		String driverClassName = "oracle.jdbc.OracleDriver";
		Connection connection = null;
		try {
			Class.forName(driverClassName).newInstance();
			String url = "jdbc:oracle:thin:@10.152.71.12:1521:lhdw"; // orcl为数据库的SID
			String user = "hzshcxc";
			String password = "cxcHZSH1234";
			connection = DriverManager.getConnection(url, user, password);
		} catch (Exception e) {
			DBHelper.release(null, null, connection);
			e.printStackTrace();
		}
		return connection;
	}
	
	// 解析表的字段元数据，根据元数据生成对应代码文件
	private void createCodeFiles(List<Column> colList) {
		// 1.生成实体类entity/EntityName.java文件
		createFile(getEntityFile(colList));
		// 2.生成mapper/EntityNameMapper.java文件
		createFile(getMapperJavaFile());
		// 3.生成mapping/EntityNameMapper.xml文件
		createFile(getMapperXMLFile(colList));
		// 4.生成service/EntityNameService.java文件
		// 5.生成service/EntityNameImpl.java文件
		// 6.生成controller/EntityNameController文件
		// 7.生成template/entityName/entity_name_update.html
		// 8.生成template/entityName/entity_name_list.html

	}


	// 准备mapper/EntityNameMapping.xml文件
	private OutPutFile getMapperXMLFile(List<Column> colList) {
		OutPutFile outPutFile = new OutPutFile();
		String fileOutputStr ="";
		
		String fileName = path + GeneratorUtil.getPackage(packagePath) + "entity/" + entityName + ".java";
		File file = new File(fileName);
		file.getParentFile().mkdirs();// 创建文件,以覆盖形式
		
		//文件头
		String fileStar = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n" + 
				"<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n" + 
				"<mapper namespace=\"com.hzsh.configuration.mapper.CodingMapper\">\\r\\n";
		//BaseResultMap
		String resultMap=
				"	<resultMap id=\"BaseResultMap\"\r\n" + 
				"		type=\"com.hzsh.configuration.entity.Coding\">\r\n" + 
				"		<id column=\"ID\" jdbcType=\"VARCHAR\" property=\"id\" />\r\n" + 
				"		<result column=\"CODE\" jdbcType=\"VARCHAR\" property=\"code\" />\r\n" + 
				"		<result column=\"NAME\" jdbcType=\"VARCHAR\" property=\"name\" />\r\n" + 
				"		<result column=\"CODETYPE\" jdbcType=\"VARCHAR\" property=\"codetype\" />\r\n" + 
				"		<result column=\"GROUPTYPE\" jdbcType=\"VARCHAR\" property=\"grouptype\" />\r\n" + 
				"		<result column=\"ORDERCODE\" jdbcType=\"DECIMAL\" property=\"ordercode\" />\r\n" + 
				"		<result column=\"FIRSTCODE\" jdbcType=\"VARCHAR\" property=\"firstcode\" />\r\n" + 
				"		<result column=\"FIRSTNAME\" jdbcType=\"VARCHAR\" property=\"firstname\" />\r\n" + 
				"		<result column=\"SECONDCODE\" jdbcType=\"VARCHAR\" property=\"secondcode\" />\r\n" + 
				"		<result column=\"SECONDNAME\" jdbcType=\"VARCHAR\" property=\"secondname\" />\r\n" + 
				"		<result column=\"SIMPLENAME\" jdbcType=\"VARCHAR\" property=\"simplename\" />\r\n" + 
				"		<result column=\"SIMPLENAME\" jdbcType=\"VARCHAR\" property=\"simplename\" />\r\n" + 
				"		<result column=\"MAPPINGCODE\" jdbcType=\"VARCHAR\" property=\"mappingcode\" />\r\n" + 
				"		<result column=\"FLOWCODE\" jdbcType=\"VARCHAR\" property=\"flowcode\" />\r\n" + 
				"	</resultMap>\r\n";
		
		//所有字段拼接 Base_Column_List
		String baseColumnList = "<sql id=\"Base_Column_List\">\r\n" + 
				"		ID, CODE, NAME, CODETYPE, GROUPTYPE, ORDERCODE, FIRSTCODE,\r\n" + 
				"		FIRSTNAME,SECONDCODE,SECONDNAME,SIMPLENAME,FLOWCODE,MAPPINGCODE\r\n" + 
				"	</sql>";
		
		//根据主键删除 deleteByPrimaryKey
		String deleteByPrimaryKey ="";

		//插入记录,含所有字段 insert

	    //插入记录，选择字段插入 insertSelective
	   
	    //根据主键查询字段 selectByPrimaryKey

	    //根据主键更新记录，选择字段更新 updateByPrimaryKeySelective

	    //根据主键更新记录，所有字段更新 updateByPrimaryKey
	    
		//根据map查询list，用于分布查询 getListByMap

		//根据map查询总数 getTotalNumByMap

		//根据实体类查询list getListByEntity
		
		//拼接
//		fileOutputStr = packageStr + importStr + classStart + fieldStr+metodGetSet+classEnd;
		
		outPutFile.file=file;
		outPutFile.filenName=fileName;
		outPutFile.fileOutputStr=fileOutputStr;
		return outPutFile;
	}

	// 准备mapper/EntityNameMapper.java文件
	private OutPutFile getMapperJavaFile() {
		OutPutFile outPutFile = new OutPutFile();
		String fileOutputStr ="";
		
		String fileName = path + GeneratorUtil.getPackage(packagePath) + "mapper/" + entityName + "Mapper.java";
		File file = new File(fileName);
		file.getParentFile().mkdirs();// 创建文件,以覆盖形式
		//类包名
		String packageStr = "package "+packagePath+".mapper;"+n2;
		//import导入类
		String importStr = "import java.util.List;\r\n" + 
				"import java.util.Map;\r\n" + 
				"import org.apache.ibatis.annotations.Mapper;\r\n" + 
				"import "+packagePath+".entity."+entityName+";"+n2;
		//类开始
		String classStart ="@Mapper"+n+ "public interface "+entityName+"Mapper {"+n;
		
		String classBody = 
				"	//根据主键删除\r\n" + 
				"	int deleteByPrimaryKey(String id);\r\n" + 
				"\r\n" + 
				"	//插入记录,含所有字段\r\n" + 
				"    int insert("+entityName+" record);\r\n" + 
				"\r\n" + 
				"    //插入记录，选择字段插入\r\n" + 
				"    int insertSelective("+entityName+" record);\r\n" + 
				"   \r\n" + 
				"    //根据主键查询字段\r\n" + 
				"    "+entityName+" selectByPrimaryKey(String id);\r\n" + 
				"\r\n" + 
				"    //根据主键更新记录，选择字段更新\r\n" + 
				"    int updateByPrimaryKeySelective("+entityName+" record);\r\n" + 
				"\r\n" + 
				"    //根据主键更新记录，所有字段更新\r\n" + 
				"    int updateByPrimaryKey("+entityName+" record);\r\n" + 
				"    \r\n" + 
				"	//根据map查询list，用于分布查询\r\n" + 
				"	List<"+entityName+"> getListByMap(Map<String,Object> map);\r\n" + 
				"\r\n" + 
				"	//根据map查询总数\r\n" + 
				"	int getTotalNumByMap(Map<String, Object> map);\r\n" + 
				"\r\n" + 
				"	//根据实体类查询list\r\n" + 
				"	List<"+entityName+"> getListByEntity("+entityName+" searchBy);"+n;
		
		//类结束
		String classEnd = "}";
		//拼接
		fileOutputStr = packageStr + importStr + classStart + classBody+classEnd;
		
		outPutFile.file=file;
		outPutFile.filenName=fileName;
		outPutFile.fileOutputStr=fileOutputStr;
		return outPutFile;
	}

	
	// 准备实体类entity/EntityName.java文件
	public OutPutFile getEntityFile(List<Column> colList) {
		OutPutFile outPutFile = new OutPutFile();
		String fileOutputStr ="";
		
		String fileName = path + GeneratorUtil.getPackage(packagePath) + "entity/" + entityName + ".java";
		File file = new File(fileName);
		file.getParentFile().mkdirs();// 创建文件,以覆盖形式
		//类包名
		String packageStr = "package "+packagePath+".entity;"+n2;
		//import导入类
		StringBuilder importStr = new StringBuilder();
		//类开始
		String classStart = "public class "+entityName+" {"+n;
		//属性定义
		StringBuilder fieldStr = new StringBuilder();
		//属性的Getter\Setter方法
		StringBuilder metodGetSet = new StringBuilder();
		//遍历字段，生成属性字义与相关方法
		for(Column column : colList) {
			//根据字段生成相关字符
			EntityHepler helper = GeneratorUtil.getHepler(column);
			//生成属性定义
			fieldStr.append( tab+"private "+helper.getTypeName()+" "+helper.getField()+";"+n2);
			//生成getter方法
			metodGetSet.append( tab+"public String "+helper.getMethodGetter()+" {"+n);
			metodGetSet.append( tab2+"return id;"+n);
			metodGetSet.append( tab+"}"+n2);
			//生成setter方法
			metodGetSet.append( tab+"public void "+helper.getMethodSetter()+"(String "+helper.getField()+") {"+n);
			metodGetSet.append( tab2+"this."+helper.getField()+" = "+helper.getField()+" == null ? null : "+helper.getField()+".trim();"+n);
			metodGetSet.append( tab+"}"+n2);
		}
		//类结束
		String classEnd = "}";
		//拼接
		fileOutputStr = packageStr + importStr + classStart + fieldStr+metodGetSet+classEnd;
		
		outPutFile.file=file;
		outPutFile.filenName=fileName;
		outPutFile.fileOutputStr=fileOutputStr;
		return outPutFile;
	}
	
	//生成文件
	public void createFile(OutPutFile outPutFile) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(outPutFile.file);
			writer.write(outPutFile.fileOutputStr);
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
		System.out.println("生成文件："+outPutFile.filenName);
	}
	
	class OutPutFile {
		File file;
		String filenName;
		String fileOutputStr;
	}
}
