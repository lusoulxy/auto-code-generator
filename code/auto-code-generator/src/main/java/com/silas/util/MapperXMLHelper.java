package com.silas.util;

import java.util.List;

public class MapperXMLHelper {
	
	public static GeneratorConfiguration config;
	

	static String getResultMap(){
		return null;
	}

	public static String getMappingXml(GeneratorConfiguration config, List<Column> colList) {
		String result = "";

		// 文件头
		String fileStar = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n"
				+ "<mapper namespace=\"com.hzsh.configuration.mapper.CodingMapper\">\\r\\n";
		// BaseResultMap
		String resultMap = "	<resultMap id=\"BaseResultMap\"\r\n"
				+ "		type=\"com.hzsh.configuration.entity.Coding\">\r\n"
				+ "		<id column=\"ID\" jdbcType=\"VARCHAR\" property=\"id\" />\r\n"
				+ "		<result column=\"CODE\" jdbcType=\"VARCHAR\" property=\"code\" />\r\n"
				+ "		<result column=\"NAME\" jdbcType=\"VARCHAR\" property=\"name\" />\r\n"
				+ "		<result column=\"CODETYPE\" jdbcType=\"VARCHAR\" property=\"codetype\" />\r\n"
				+ "		<result column=\"GROUPTYPE\" jdbcType=\"VARCHAR\" property=\"grouptype\" />\r\n"
				+ "		<result column=\"ORDERCODE\" jdbcType=\"DECIMAL\" property=\"ordercode\" />\r\n"
				+ "		<result column=\"FIRSTCODE\" jdbcType=\"VARCHAR\" property=\"firstcode\" />\r\n"
				+ "		<result column=\"FIRSTNAME\" jdbcType=\"VARCHAR\" property=\"firstname\" />\r\n"
				+ "		<result column=\"SECONDCODE\" jdbcType=\"VARCHAR\" property=\"secondcode\" />\r\n"
				+ "		<result column=\"SECONDNAME\" jdbcType=\"VARCHAR\" property=\"secondname\" />\r\n"
				+ "		<result column=\"SIMPLENAME\" jdbcType=\"VARCHAR\" property=\"simplename\" />\r\n"
				+ "		<result column=\"SIMPLENAME\" jdbcType=\"VARCHAR\" property=\"simplename\" />\r\n"
				+ "		<result column=\"MAPPINGCODE\" jdbcType=\"VARCHAR\" property=\"mappingcode\" />\r\n"
				+ "		<result column=\"FLOWCODE\" jdbcType=\"VARCHAR\" property=\"flowcode\" />\r\n"
				+ "	</resultMap>\r\n";

		// 所有字段拼接 Base_Column_List
		String baseColumnList = "<sql id=\"Base_Column_List\">\r\n"
				+ "		ID, CODE, NAME, CODETYPE, GROUPTYPE, ORDERCODE, FIRSTCODE,\r\n"
				+ "		FIRSTNAME,SECONDCODE,SECONDNAME,SIMPLENAME,FLOWCODE,MAPPINGCODE\r\n" + "	</sql>";

		// 根据主键删除 deleteByPrimaryKey
		String deleteByPrimaryKey = "";

		// 插入记录,含所有字段 insert

		// 插入记录，选择字段插入 insertSelective

		// 根据主键查询字段 selectByPrimaryKey

		// 根据主键更新记录，选择字段更新 updateByPrimaryKeySelective

		// 根据主键更新记录，所有字段更新 updateByPrimaryKey

		// 根据map查询list，用于分布查询 getListByMap

		// 根据map查询总数 getTotalNumByMap

		// 根据实体类查询list getListByEntity

		// 拼接

		return result;
	}
	

	public static GeneratorConfiguration getConfig() {
		return config;
	}


	public static void setConfig(GeneratorConfiguration config) {
		MapperXMLHelper.config = config;
	}


}
