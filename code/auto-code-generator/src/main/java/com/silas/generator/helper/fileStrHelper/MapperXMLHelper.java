package com.silas.generator.helper.fileStrHelper;

import java.util.List;

import com.silas.generator.config.Config;
import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.jdbc.DBConfig;
import com.silas.util.GeneratorUtil;
import com.silas.util.module.ModuleUtils;

public class MapperXMLHelper extends CreateFileHelper {
	//设置模块是否生成，和生成的顺序；value=0为不生成，数字越小排越前面
	{
		//key:方法名，value：true 则生成 false 则不生成
		toCreateModule.put("getListByMap", 10);//根据map查询list，用于分页查询
		toCreateModule.put("getTotalNumByMap", 11);//根据map查询总数
		toCreateModule.put("insert", 20);//插入记录,含所有字段
		toCreateModule.put("insertSelective", 30);//插入记录，选择字段插入
		toCreateModule.put("selectByPrimaryKey", 40);///根据主键查询
		toCreateModule.put("updateByPrimaryKey", 50);//根据主键更新记录，所有字段更新
		toCreateModule.put("updateByPrimaryKeySelective", 60);//根据主键，选择字段进行更新
		toCreateModule.put("getListByEntity", 90);//根据实体类查询list
		toCreateModule.put("deleteByPrimaryKey", 100);// 根据主键删除记录
		
		toCreateModule.put("baseColumnList", -10);// 所有字段拼接 Base_Column_List
		toCreateModule.put("resultMap", -20);// 结果集映射
	}
	/**
	 * 生成mapping/EntityNameMapper.xml文件
	 * @throws Exception 
	 */
	public void createFile() throws Exception {
		GeneratorUtil.createFile(getOutPutFile());
	}

	/**
	 * 封装要生成的mapping/EntityNameMapper.xml文件
	 * @throws Exception 
	 */
	public OutPutFile getOutPutFile() throws Exception {
		// 完整文件名（包括路径）
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "mapping/" + entityName + "Mapper.xml";
		// 要输出的文本内容
		String fileOutputStr = getOutPutContent();
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}
	@Override
	public String getOutPutContent() throws Exception {
		String result = "";
		// 文件头
		String start = 
				"<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n"
				+ "<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\r\n"
				+ "<mapper namespace=\"" + packagePath + ".mapper." + entityName + "Mapper\">";
		modules = getModules();
		String modulesStr = ModuleUtils.getModulesStr(modules);
		String end="</mapper>";
		// 拼接
		result = start + modulesStr+end;
		return result;
	}

	/**
	 * 获得要生成模块，进行封装
	 * @throws Exception 
	 */
	public List<Module> getModules() throws Exception {
		InnerModules innerModeules = new InnerModules();
		return getModules(innerModeules,modules);
	}
	
	public class InnerModules {
		// 根据实体类查询list getListByEntity
		public Module getListByEntity() {
			Module module = new Module();
			module.setName("getListByEntity");
			String str = n + "<!-- 根据实体类查询list -->" + n
					+ "	<select id=\"getListByEntity\" resultMap=\"BaseResultMap\">\r\n"
					+ "		select <include refid=\"Base_Column_List\"/> from " + tableName + "\r\n"
					+ "		<trim prefix=\"where\" suffixOverrides=\"AND\">\r\n";
			String whereStr = "";
			for (Column col : colList) {
				whereStr += t3 + "<if test=\"" + col.getEntityField() + "!=null\">\r\n" + t4 + col.getColumName() + " = #{"
						+ col.getEntityField() + "} AND\r\n" + t3 + "</if>" + n;
			}
			whereStr += t2 + "</trim>\r\n";
			str += whereStr + "	</select>";
			module.setFullStr(str);
			return module;
		}
		
		// 根据map查询总数 getTotalNumByMap
		public Module getTotalNumByMap() {
			Module module = new Module();
			module.setName("getTotalNumByMap");
			String str = n + "<!-- 根据map查询总数 -->" + n
					+ "	<select id=\"getTotalNumByMap\" resultType=\"java.lang.Integer\">\r\n"
					+ "		select count(*) from " + tableName + "\r\n"
					+ "		<trim prefix=\"where\" suffixOverrides=\"AND\">\r\n";
			String whereStr = "";
			for (Column col : colList) {
				whereStr += t3 + "<if test=\"" + col.getEntityField() + "!=null\">\r\n" + t4 + col.getColumName() + " = #{"
						+ col.getEntityField() + "} AND\r\n" + t3 + "</if>" + n;
			}
			whereStr += t2 + "</trim>\r\n";
			str += whereStr + "	</select>";
			module.setFullStr(str);
			return module;
		}
		
		// 根据map查询list，用于分页查询 getListByMap
		public Module getListByMap() throws Exception {
			Module module = new Module();
			module.setName("getListByMap");
			if(DBConfig.DBTYPE==1) {//oracle版本
				String str = n + "<!-- 根据map查询list，用于分页查询 -->" + n
						+ "	<select id=\"getListByMap\" resultMap=\"BaseResultMap\">\r\n" + "		select c.*\r\n"
						+ "		from (\r\n" + "		select G.*, rownum rn from (\r\n" + "		select * from " + tableName
						+ " g\r\n" + "		<trim prefix=\"where\" suffixOverrides=\"AND\">\r\n";
				
				String whereStr = "";
				for (Column col : colList) {
					whereStr += t3 + "<if test=\"" + col.getEntityField() + "!=null\">\r\n" + t4 + col.getColumName() + " = #{"
							+ col.getEntityField() + "} AND\r\n" + t3 + "</if>" + n;
				}
				whereStr += t2 + "</trim>\r\n";
				str += whereStr + "		<if test=\"orderStr != null\">\r\n" + "			order by ${orderStr}\r\n"
						+ "		</if>\r\n" + "		) G where rownum <![CDATA[  <= ]]> #{last} ) c\r\n"
						+ "		where c.rn <![CDATA[  >= ]]> #{first}\r\n" + "	</select>";
				module.setFullStr(str);
			}else if(DBConfig.DBTYPE==2) {//mysql版本
				String str = n + "\t<!-- 根据map查询总数 -->" + n+
						"\t<select id=\"getListByMap\" resultMap=\"BaseResultMap\">\n" +
						"\t\tselect * from "+tableName+" \n" +
						"\t\t<trim prefix=\"where\" suffixOverrides=\"AND\">\n";
				String whereStr = "";
				for(Column col:colList) {
					whereStr += t3+"<if test=\""+col.getEntityField()+"!=null\">\r\n" +
							t4+col.getColumName()+" = #{"+col.getEntityField()+"} AND\r\n" +
							t3+"</if>"+n;
				}
				whereStr +=
						"\t\t</trim>\n" ;
				str +=	whereStr+
						"\t\t<if test=\"orderStr != null\">\n" +
						"\t\t\torder by ${orderStr}\n" +
						"\t\t</if>\n" +
						"\t\t<if test=\"limit != null\">\n" +
						"\t\t\t limit #{first},#{size}\n" +
						"\t\t</if>\n" +
						"\t</select>";
				module.setFullStr(str);
			}else {
				throw new Exception("驱动错误，或者未支持此驱动："+DBConfig.DRIVERCLASSNAME);
			}
			return module;
		}
		// 根据主键更新记录，所有字段更新 updateByPrimaryKey
		public Module updateByPrimaryKey() {
			Module module = new Module();
			module.setName("updateByPrimaryKey");
			String str = n + "<!-- 根据主键更新记录，所有字段更新 -->" + n + t1 + "<update id=\"updateByPrimaryKey\""
					+ "		parameterType=\"" + packagePath + ".entity." + entityName + "\">\r\n" + t2 + "update "
					+ tableName + "\r\n" + t2 + "set ";
			String setStr = "";
			for (Column col : colList) {
				setStr += col.getColumName() + " = #{" + col.getEntityField() + ",jdbcType=" + col.getJdbcType() + "},\r\n"
						+ t2;
			}
			// 去掉最后一个逗号
			setStr = setStr.substring(0, setStr.lastIndexOf(","));
			str += setStr + n + "		where " + primary_col.getColumName() + " = #{"
					+ primary_col.getEntityField() + ",jdbcType=" + primary_col.getJdbcType() + "}\r\n" + "	</update>";
			module.setFullStr(str);
			return module;
		}
		// 根据主键更新记录，选择字段更新 updateByPrimaryKeySelective
		public Module updateByPrimaryKeySelective() {
			Module module = new Module();
			module.setName("updateByPrimaryKeySelective");
			String str = n + "<!-- 根据主键更新记录，选择字段更新 -->" + n
					+ "	<update id=\"updateByPrimaryKeySelective\" parameterType=\"" + packagePath + ".entity." + entityName
					+ "\">\r\n" + t2 + "update "+tableName+"\r\n" + t2 + "<set>\r\n";
			String setSelective = "";
			for (Column col : colList) {
				setSelective += t3 + "<if test=\"" + col.getEntityField() + " != null\">\r\n" + t4 + col.getColumName()
						+ "=#{" + col.getEntityField() + ",jdbcType=" + col.getJdbcType() + "},\r\n" + t3 + "</if>" + n;
			}
			str += setSelective + "		</set>\r\n" + t2 + "where " + primary_col.getColumName()
					+ " = #{" + primary_col.getEntityField() + ",jdbcType=" + primary_col.getJdbcType() + "}\r\n"
					+ "	</update>";
			module.setFullStr(str);
			return module;
		}
		// 根据主键查询字段 selectByPrimaryKey
		public Module selectByPrimaryKey() {
			Module module = new Module();
			module.setName("selectByPrimaryKey");
			String str = n + "<!-- 根据主键查询字段 -->" + n + "	<select id=\"selectByPrimaryKey\" parameterType=\""
					+ primary_col.getJavaType() + "\"\r\n" + "		resultMap=\"BaseResultMap\">\r\n" + "		select\r\n"
					+ "		<include refid=\"Base_Column_List\" />\r\n" + "		from " + tableName + "\r\n" + "		where "
					+ primary_col.getColumName() + " = #{" + primary_col.getEntityField() + ",jdbcType="
					+ primary_col.getJdbcType() + "}\r\n" + "	</select>";
			module.setFullStr(str);
			return module;
		}
		// 插入记录，选择字段插入 insertSelective
		public Module insertSelective() {
			Module module = new Module();
			module.setName("insertSelective");
			String str = n + "<!-- 插入记录，选择字段插入 -->" + n + "	<insert id=\"insertSelective\" parameterType=\""
					+ packagePath + ".entity." + entityName + "\">\r\n" + "		insert into " + tableName + "\r\n";
			// 赋值字段拼接
			String columnStr = t2 + "<trim prefix=\"(\" suffix=\")\" suffixOverrides=\",\">\r\n";
			String valuesStr = t2 + "<trim prefix=\"values (\" suffix=\")\" suffixOverrides=\",\">\r\n";
			for (Column col : colList) {
				columnStr += t3 + "<if test=\"" + col.getEntityField() + " != null\">\r\n" + t4 + ""
						+ col.getColumName() + ",\r\n" + t3 + "</if>" + n;
				valuesStr += t3 + "<if test=\"" + col.getEntityField() + " != null\">\r\n" + t4 + "#{"
						+ col.getEntityField() + ",jdbcType=" + col.getJdbcType() + "},\r\n" + t3 + "</if>" + n;
			}
			columnStr += t2 + "</trim>" + n;
			valuesStr += t2 + "</trim>" + n;
			str += columnStr + valuesStr + "	</insert>";
			module.setFullStr(str);
			return module;
		}
		// 插入记录,含所有字段 insert
		public Module insert() {
			Module module = new Module();
			module.setName("insert");
			String str = n + "<!-- 插入记录,含所有字段 -->" + n + "	<insert id=\"insert\"	parameterType=\"" + packagePath
					+ ".entity." + entityName + "\">	" + n + "		insert into " + tableName + " " + n;
			// 赋值字段拼接
			String baseColumnList = t2 + "(";
			String valuesStr = t2 + "(";
			for (Column col : colList) {
				baseColumnList += col.getColumName() + ", ";
				valuesStr += "#{" + col.getEntityField() + ",jdbcType=" + col.getJdbcType() + "}, ";
			}
			// 去掉多余的逗号
			baseColumnList = baseColumnList.substring(0, baseColumnList.lastIndexOf(", "));
			valuesStr = valuesStr.substring(0, valuesStr.lastIndexOf(", "));

			// 赋值字段结束
			baseColumnList += ")" + n;
			valuesStr += ")" + n;

			// 拼接
			str += baseColumnList + t2 + "values \n" + valuesStr;
			// 结束
			str += t1 + "</insert>";
			module.setFullStr(str);
			return module;
		}
		// 根据主键删除 deleteByPrimaryKey
		public Module deleteByPrimaryKey() {
			Module module = new Module();
			module.setName("deleteByPrimaryKey");
			String str = n + "<!-- 根据主键删除 -->" + n + "	<delete id=\"deleteByPrimaryKey\" parameterType=\""
					+ primary_col.getJavaType() + "\">\r\n" + "		delete from\r\n" + "		" + tableName + "\r\n"
					+ "		where " + primary_col.getColumName() + " = #{id,jdbcType=" + primary_col.getJdbcType() + "}\r\n"
					+ "	</delete>" + n;
			module.setFullStr(str);
			return module;
		}

		// 结果集映射
		public Module resultMap() {
			Module module = new Module();
			module.setName("str");
			String str = n + "<!-- 结果集映射 -->" + n + "	<resultMap id=\"BaseResultMap\"	type=\"" + packagePath
					+ ".entity." + entityName + "\">\r\n";
			if (primary_col != null) {
				str += "		<id column=\"" + primary_col.getColumName() + "\" jdbcType=\""
						+ primary_col.getJdbcType() + "\" property=\"" + primary_col.getEntityField() + "\" />\r\n";
			}
			for (Column col : colList) {
				if (!col.isPk()) {
					str += "		<result column=\"" + col.getColumName() + "\" jdbcType=\"" + col.getJdbcType()
							+ "\" property=\"" + col.getEntityField() + "\" />\r\n";
				}
			}
			str += "	</resultMap>";
			module.setFullStr(str);
			return module;
		}
		// 所有字段拼接 Base_Column_List
		public Module baseColumnList() {
			Module module = new Module();
			module.setName("baseColumnList");
			String str = n + "<!-- 所有字段拼接 -->" + n + "	<sql id=\"Base_Column_List\">\r\n" + t1;
			// 遍历字段
			for (Column col : colList) {
				str += col.getColumName() + ", ";
			}
			// 去掉多余的逗号
			str = str.substring(0, str.lastIndexOf(", "));
			str += n + "	</sql>";
			module.setFullStr(str);
			return module;
		}
	}


}
