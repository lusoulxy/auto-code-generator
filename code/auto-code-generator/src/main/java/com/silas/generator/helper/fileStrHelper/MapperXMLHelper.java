package com.silas.generator.helper.fileStrHelper;

import java.util.List;

import com.silas.generator.Config;
import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;

public class MapperXMLHelper implements CreateFileHelper{

	// 生成mapping/EntityNameMapper.xml文件
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备mapper/EntityNameMapper.xml文件
	public OutPutFile getOutPutFile() {
		// 完整文件名（包括路径）
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "mapping/" + entityName + "Mapper.xml";
		// 要输出的文本内容
		String fileOutputStr = getMappingXml(colList);
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}

	// 获得MappingXml
	public String getMappingXml(List<Column> colList) {
		String result = "";
		// 文件头
		String fileStar = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n"
				+ "<mapper namespace=\""+packagePath+".mapper."+entityName+"Mapper\">";
		// BaseResultMap
		String resultMap = resultMap(colList);

		// 所有字段拼接 Base_Column_List
		String baseColumnList = baseColumnList(colList);

		// 根据主键删除 deleteByPrimaryKey
		String deleteByPrimaryKey = deleteByPrimaryKey(colList);

		// 插入记录,含所有字段 insert
		String insert = insert(colList);

		// 插入记录，选择字段插入 insertSelective
		String insertSelective = insertSelective(colList);

		// 根据主键查询字段 selectByPrimaryKey
		String selectByPrimaryKey = selectByPrimaryKey(colList);

		// 根据主键更新记录，选择字段更新 updateByPrimaryKeySelective
		String updateByPrimaryKeySelective = updateByPrimaryKeySelective(colList);

		// 根据主键更新记录，所有字段更新 updateByPrimaryKey
		String updateByPrimaryKey = updateByPrimaryKey(colList);

		// 根据map查询list，用于分布查询 getListByMap
		String getListByMap = getListByMap(colList);

		// 根据map查询总数 getTotalNumByMap
		String getTotalNumByMap = getTotalNumByMap(colList);

		// 根据实体类查询list getListByEntity
		String getListByEntity = getListByEntity(colList);
		// 拼接

		result = fileStar 
				+ resultMap // 结果集映射
				+ baseColumnList // Base_Column_List
				+ getTotalNumByMap // 根据map查询总数 
				+ getListByMap // 根据map查询list，用于分页查询 
				+ getListByEntity // 根据实体类查询list 
				+ selectByPrimaryKey // 根据主键查询字段
				+ insert // 插入记录,含所有字段 insert
				+ insertSelective // 插入记录，选择字段插入
				+ updateByPrimaryKey // 根据主键更新记录，所有字段更新 
				+ updateByPrimaryKeySelective // 根据主键更新记录，选择字段更新
				+ deleteByPrimaryKey // 根据主键删除 deleteByPrimaryKey
				+ "</mapper>";

		return result;
	}
	// 根据实体类查询list getListByEntity
	static String getListByEntity(List<Column> colList) {
		String getListByEntity = n + "<!-- 根据实体类查询list -->" + n
				+ "	<select id=\"getListByEntity\" resultMap=\"BaseResultMap\">\r\n" + 
				"		select <include refid=\"Base_Column_List\"/> from "+tableName+"\r\n" + 
				"		<trim prefix=\"where\" suffixOverrides=\"AND\">\r\n";
		String whereStr = "";
		for(Column col:colList) {
			whereStr += t3+"<if test=\""+col.getEntityField()+"!=null\">\r\n" + 
					t4+col.getColumName()+" = #{"+col.getEntityField()+"} AND\r\n" + 
					t3+"</if>"+n;
		}
		whereStr += t2+"</trim>\r\n";
		getListByEntity += whereStr+
				"	</select>";
		return getListByEntity;
	}
	// 根据map查询总数 getTotalNumByMap
	static String getTotalNumByMap(List<Column> colList) {
		String getTotalNumByMap = n + "<!-- 根据map查询总数 -->" + n
				+ "	<select id=\"getTotalNumByMap\" resultType=\"java.lang.Integer\">\r\n" + 
				"		select count(*) from "+tableName+"\r\n" + 
				"		<trim prefix=\"where\" suffixOverrides=\"AND\">\r\n";
		String whereStr = "";
		for(Column col:colList) {
			whereStr += t3+"<if test=\""+col.getEntityField()+"!=null\">\r\n" + 
					t4+col.getColumName()+" = #{"+col.getEntityField()+"} AND\r\n" + 
					t3+"</if>"+n;
		}
		whereStr += t2+"</trim>\r\n";
		getTotalNumByMap += whereStr+
				"	</select>";
		return getTotalNumByMap;
	}
	// 根据map查询list，用于分页查询 getListByMap
	static String getListByMap(List<Column> colList) {
		String getListByMap =   n + "<!-- 根据map查询list，用于分页查询 -->" + n
				+ "<select id=\"getListByMap\" resultMap=\"BaseResultMap\">\r\n" + 
				"		select c.*\r\n" + 
				"		from (\r\n" + 
				"		select G.*, rownum rn from (\r\n" + 
				"		select * from "+tableName+" g\r\n" + 
				"		<trim prefix=\"where\" suffixOverrides=\"AND\">\r\n" ;
		
		String whereStr = "";
		for(Column col:colList) {
			whereStr += t3+"<if test=\""+col.getEntityField()+"!=null\">\r\n" + 
					t4+col.getColumName()+" = #{"+col.getEntityField()+"} AND\r\n" + 
					t3+"</if>"+n;
		}
		whereStr += t2+"</trim>\r\n";
		getListByMap +=whereStr+ 
				"		<if test=\"orderStr != null\">\r\n" + 
				"			order by ${orderStr}\r\n" + 
				"		</if>\r\n" + 
				"		) G where rownum <![CDATA[  <= ]]> #{last} ) c\r\n" + 
				"		where c.rn <![CDATA[  >= ]]> #{first}\r\n" + 
				"	</select>";
		return getListByMap;
	}
	
	// 根据主键更新记录，所有字段更新 updateByPrimaryKey
	static String updateByPrimaryKey(List<Column> colList) {
		String updateByPrimaryKey =  n + "<!-- 根据主键更新记录，所有字段更新 -->" + n
				+ t1+"<update id=\"updateByPrimaryKey\""
				+ "		parameterType=\""+packagePath+".entity."+entityName+"\">\r\n" 
				+ t2+"update "+tableName+"\r\n"
				+ t2+"set ";
		String setStr = "";
		for(Column col:colList) {
			setStr +=col.getColumName()+" = #{"+col.getEntityField()+",jdbcType="+col.getJdbcType()+"},\r\n"+t2;
		}
		//去掉最后一个逗号
		setStr = setStr.substring(0,setStr.lastIndexOf(","));
		updateByPrimaryKey += setStr +n
				+"		where "+primary_col.getColumName()+" = #{"+primary_col.getEntityField()+",jdbcType="+primary_col.getJdbcType()+"}\r\n" + "	</update>";
		return updateByPrimaryKey;
	}

	// 根据主键更新记录，选择字段更新 updateByPrimaryKeySelective
	public String updateByPrimaryKeySelective(List<Column> colList) {
		String updateByPrimaryKeySelective = n + "<!-- 根据主键更新记录，选择字段更新 -->" + n
				+ "	<update id=\"updateByPrimaryKeySelective\" parameterType=\"" + packagePath + ".entity." + entityName
				+ "\">\r\n" + t2 + "update U_END_PRICE\r\n" + t2 + "<set>\r\n";
		String setSelective = "";
		for (Column col : colList) {
			setSelective +=t3+"<if test=\""+col.getEntityField()+" != null\">\r\n" + 
					t4+col.getColumName()+"=#{"+col.getEntityField()+",jdbcType="+col.getJdbcType()+"},\r\n" + 
					t3+"</if>"+n;
		}
		updateByPrimaryKeySelective+=setSelective
				+"		</set>\r\n"
				+ t2+"where "+primary_col.getColumName()
				+" = #{"+primary_col.getEntityField()
				+",jdbcType="+primary_col.getJdbcType()+"}\r\n" 
				+ "	</update>";
		return updateByPrimaryKeySelective;
	}
	
	// 根据主键查询字段 selectByPrimaryKey
	static String selectByPrimaryKey(List<Column> colList) {
		String selectByPrimaryKey = n + "<!-- 根据主键查询字段 -->" + n
				+ "	<select id=\"selectByPrimaryKey\" parameterType=\"" + primary_col.getJavaType() + "\"\r\n"
				+ "		resultMap=\"BaseResultMap\">\r\n" + "		select\r\n"
				+ "		<include refid=\"Base_Column_List\" />\r\n" + "		from " + tableName + "\r\n" + "		where "
				+ primary_col.getColumName() + " = #{"+primary_col.getEntityField()+",jdbcType="+primary_col.getJdbcType()+"}\r\n" + "	</select>";
		return selectByPrimaryKey;
	}

	// 插入记录，选择字段插入 insertSelective
	static String insertSelective(List<Column> colList) {
		String insertSelective = n + "<!-- 插入记录，选择字段插入 -->" + n
				+ "	<insert id=\"insertSelective\" parameterType=\""+packagePath+".entity."+entityName+"\">\r\n"
				+ "		insert into " + tableName + "\r\n";
		// 赋值字段拼接
		String columnStr = t2 + "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
		String valuesStr = t2 + "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\r\n";
		for (Column col : colList) {
			if (col.isPkAuto()) {// 若为主键
				columnStr += t3 + col.getColumName() + "," + n;
				valuesStr += t3 + "sys_guid()," + n;// 使用GUID,此为ORACLE数据库

			} else {
				columnStr += t3 + "<if test=\"" + col.getEntityField() + " != null\">\r\n" + t4 + ""
						+ col.getColumName() + ",\r\n" + t3 + "</if>" + n;
				valuesStr += t3 + "<if test=\"" + col.getEntityField() + " != null\">\r\n" + t4 + "#{"
						+ col.getEntityField() + ",jdbcType=" + col.getJdbcType() + "},\r\n" + t3 + "</if>" + n;
			}
		}
		columnStr += t2 + "</trim>" + n;
		valuesStr += t2 + "</trim>" + n;
		insertSelective += columnStr + valuesStr + "	</insert>";
		return insertSelective;
	}

	// 插入记录,含所有字段 insert
	static String insert(List<Column> colList) {
		String insert = n + "<!-- 插入记录,含所有字段 -->" + n + "	<insert id=\"insert\"	parameterType=\"" + packagePath
				+ ".entity." + entityName + "\">	" + n + "		insert into "+tableName+" " + n;
		// 赋值字段拼接
		String baseColumnList = t2 + "(";
		String valuesStr = t2 + "(";
		for (Column col : colList) {
			baseColumnList += col.getColumName() + ", ";
			if (col.isPkAuto()) {
				valuesStr += "sys_guid(), ";
			} else {
				valuesStr += "#{" + col.getEntityField() + ",jdbcType=" + col.getJdbcType() + "}, ";
			}
		}
		// 去掉多余的逗号
		baseColumnList = baseColumnList.substring(0, baseColumnList.lastIndexOf(", "));
		valuesStr = valuesStr.substring(0, valuesStr.lastIndexOf(", "));

		// 赋值字段结束
		baseColumnList += ")" + n;
		valuesStr += ")" + n;

		// 拼接
		insert += baseColumnList + t2 + "values \n" + valuesStr;
		// 结束
		insert += t1 + "</insert>";
		return insert;
	}

	// 根据主键删除 deleteByPrimaryKey
	static String deleteByPrimaryKey(List<Column> colList) {
		String deleteByPrimaryKey = n + "<!-- 根据主键删除 -->" + n + "	<delete id=\"deleteByPrimaryKey\" parameterType=\""
				+ primary_col.getJavaType() + "\">\r\n" + "		delete from\r\n" + "		" + tableName + "\r\n"
				+ "		where " + primary_col.getColumName() + " = #{id,jdbcType=" + primary_col.getJdbcType() + "}\r\n"
				+ "	</delete>" + n;
		return deleteByPrimaryKey;
	}

	// 结果集映射
	static String resultMap(List<Column> colList) {
		String resultMap = n + "<!-- 结果集映射 -->" + n + "	<resultMap id=\"BaseResultMap\"	type=\"" + packagePath
				+ ".entity." + entityName + "\">\r\n";
		if (primary_col != null) {
			resultMap += "		<id column=\"" + primary_col.getColumName() + "\" jdbcType=\""
					+ primary_col.getJdbcType() + "\" property=\""+primary_col.getEntityField()+"\" />\r\n";
		}
		for (Column col : colList) {
			if (!col.isPk()) {
				resultMap += "		<result column=\"" + col.getColumName() + "\" jdbcType=\"" + col.getJdbcType()
						+ "\" property=\"" + col.getEntityField() + "\" />\r\n";
			}
		}
		resultMap += "	</resultMap>" + n;
		return resultMap;
	}

	// 所有字段拼接 Base_Column_List
	static String baseColumnList(List<Column> colList) {
		String baseColumnList = n + "<!-- 所有字段拼接 -->" + n + "	<sql id=\"Base_Column_List\">\r\n" + t1;
		// 遍历字段
		for (Column col : colList) {
			baseColumnList += col.getColumName() + ", ";
		}
		// 去掉多余的逗号
		baseColumnList = baseColumnList.substring(0, baseColumnList.lastIndexOf(", "));
		baseColumnList += n + "	</sql>" + n;
		return baseColumnList;
	}

}
