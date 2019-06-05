package com.silas.generator.helper.fileStrHelper;

import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;
import com.silas.util.StringUtils;

public class ServiceHelper implements CreateFileHelper{
	// 生成service/EntityNameService.java文件
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备service/.java文件
	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "service/" + entityName + "Service.java";
		// 类包名
		String packageStr = "package " + packagePath + ".service;" + n2;
		// import导入类
		String importStr = "import java.util.List;\r\n" + "import java.util.Map;\r\n"
				+ "import " + packagePath + ".entity." + entityName+";"+n
				+"import org.springframework.web.multipart.MultipartFile;"+n
				+primary_col.getColumnTypeHelper().getImportStr()+ n;
		// 类开始
		String classStart = n + "public interface " + entityName + "Service {" + n;

		String classBody = "	//保存记录，选择字段保存\r\n" + 
				"	public int save("+entityName+" "+StringUtils.getLowerCamelCase(entityName)+");\r\n" + 
				"\r\n" + 
				"	//根据主键更新记录，选择字段更新\r\n" + 
				"	public int update("+entityName+" "+StringUtils.getLowerCamelCase(entityName)+");\r\n" + 
				"\r\n" + 
				"	//根据主键删除\r\n" + 
				"	public int delect("+primary_col.getJavaType()+" "+primary_col.getEntityField()+");\r\n" + 
				"\r\n" + 
				"	//根据主键查询\r\n" + 
				"	public "+entityName+" findById("+primary_col.getJavaType()+" "+primary_col.getEntityField()+");\r\n" + 
				"\r\n" + 
				"	//根据map查询list，用于分页查询\r\n" + 
				"	public List<"+entityName+"> getListByMap(Map<String, Object> map);\r\n" + 
				"\r\n" + 
				"	//根据map查询总数\r\n" + 
				"	public int getTotalNumByMap(Map<String, Object> map);\r\n" + 
				"\r\n" + 
				"	//根据实体类查询list\r\n" + 
				"	public List<"+entityName+"> getListByEntity("+entityName+" "+StringUtils.getLowerCamelCase(entityName)+");" + n+
				"//导入excel\r\n" + 
				"	String importExcel(String fileName, MultipartFile file) throws Exception;";
		// 类结束
		String classEnd = "}";
		// 拼接
		fileOutputStr = packageStr + importStr + classStart + classBody + classEnd;
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}

}
