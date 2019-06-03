package com.silas.generator.helper.interface_;

import java.util.List;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;

public interface CreateFileHelper {
	String recordName = "record";
	String tableName = Config.tableName;//数据库表名
	String entityName = Config.entityName;//对应实体类名
	String entityLowerName = Config.entityLowerName;//实体类名的小驼峰命名
	String path = Config.path;//代码文件输出路径
	String packagePath = Config.packagePath;//包名
	String module = Config.module;//模块名
	String moduleName = Config.moduleName;//模块中文名，用于生成注释与日志相关
	
	List<Column> colList = Config.colList;//根据表名获取的列元数据 
	Column primary_col = Config.primary_col;//根据表名获取的主键
	
	String tab = "\t";
	String tab2 = "\t\t";
	String tab3 = "\t\t\t";
	String tab4 = "\t\t\t\t";
	String tab5 = "\t\t\t\t\t";
	String tab6 = "\t\t\t\t\t\t";
	String tab7 = "\t\t\t\t\t\t\t";
	String tab8 = "\t\t\t\t\t\t\t\t";
	String tab9 = "\t\t\t\t\t\t\t\t\t";
	String n = "\n";
	String n2 = "\n\n";
	
	public void createFile() ;//生成文件到输出路径
	
	public OutPutFile getOutPutFile();//获得要输出的文件
	
}
