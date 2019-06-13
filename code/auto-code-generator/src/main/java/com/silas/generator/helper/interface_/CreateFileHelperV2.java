package com.silas.generator.helper.interface_;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silas.generator.Config;
import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;

public interface CreateFileHelperV2 {
	String recordName = "record";
	String tableName = Config.tableName;//数据库表名
	String entityName = Config.entityName;//对应实体类名
	String entityLowerName = Config.entityLowerName;//实体类名的小驼峰命名
	String path = Config.path;//代码文件输出路径
	String packagePath = Config.packagePath;//包名
	String module = Config.module;//模块名
	String parenModuleName = Config.parenModuleName;//大模块中文名
	String moduleName = Config.moduleName;//模块中文名，用于生成注释与日志相关
	
	List<Column> colList = Config.colList;//根据表名获取的列元数据 
	Column primary_col = Config.primary_col;//根据表名获取的主键
	
	String dateFormatPartten="yyyy-MM-dd HH:mm:ss";
	String t1 = "\t";
	String t2 = "\t\t";
	String t3 = "\t\t\t";
	String t4 = "\t\t\t\t";
	String t5 = "\t\t\t\t\t";
	String t6 = "\t\t\t\t\t\t";
	String t7 = "\t\t\t\t\t\t\t";
	String t8 = "\t\t\t\t\t\t\t\t";
	String t9 = "\t\t\t\t\t\t\t\t\t";
	String n = "\n";
	String n2 = "\n\n";
	
	public void createFile();//生成文件
	
	public OutPutFile getOutPutFile();//封装文件
	
	public String getOutPutContent();//获得要输出的文件内容
	
	public List<Module> getModules();//模块内容
	
	public String getModulesStr(List<Module> modules);//模块内容
	
	
}
