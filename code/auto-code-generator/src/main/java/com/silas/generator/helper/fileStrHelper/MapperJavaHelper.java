package com.silas.generator.helper.fileStrHelper;

import java.io.File;
import java.util.List;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.Config;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;

public class MapperJavaHelper implements CreateFileHelper{

	// 生成mapper/EntityNameMapper.java文件
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备mapper/EntityNameMapper.java文件
	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "mapper/" + entityName + "Mapper.java";
		// 类包名
		String packageStr = "package " + packagePath + ".mapper;" + n2;
		// import导入类
		String importStr = "import java.util.List;\r\n" + "import java.util.Map;\r\n"
				+ "import org.apache.ibatis.annotations.Mapper;\r\n" + "import " + packagePath + ".entity." + entityName
				+ ";" +primary_col.getColumnTypeHelper().getImportStr()+ n;
		// 类开始
		String classStart = "@Mapper" + n + "public interface " + entityName + "Mapper {" + n;

		String classBody = "	//根据主键删除\r\n" + "	int deleteByPrimaryKey("+primary_col.getJavaType()+" "+primary_col.getEntityField()+");\r\n" + "\r\n"
				+ "	//插入记录,含所有字段\r\n" + "    int insert(" + entityName + " record);\r\n" + "\r\n"
				+ "    //插入记录，选择字段插入\r\n" + "    int insertSelective(" + entityName + " record);\r\n" + "   \r\n"
				+ "    //根据主键查询字段\r\n" + "    " + entityName + " selectByPrimaryKey("+primary_col.getJavaType()+" "+primary_col.getEntityField()+");\r\n" + "\r\n"
				+ "    //根据主键更新记录，选择字段更新\r\n" + "    int updateByPrimaryKeySelective(" + entityName + " record);\r\n"
				+ "\r\n" + "    //根据主键更新记录，所有字段更新\r\n" + "    int updateByPrimaryKey(" + entityName + " record);\r\n"
				+ "    \r\n" + "	//根据map查询list，用于分页查询\r\n" + "	List<" + entityName
				+ "> getListByMap(Map<String,Object> map);\r\n" + "\r\n" + "	//根据map查询总数\r\n"
				+ "	int getTotalNumByMap(Map<String, Object> map);\r\n" + "\r\n" + "	//根据实体类查询list\r\n" + "	List<"
				+ entityName + "> getListByEntity(" + entityName + " searchBy);" + n;

		// 类结束
		String classEnd = "}";
		// 拼接
		fileOutputStr = packageStr + importStr + classStart + classBody + classEnd;
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}

}
