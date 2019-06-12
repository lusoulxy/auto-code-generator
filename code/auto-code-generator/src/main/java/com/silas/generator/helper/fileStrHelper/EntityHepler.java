package com.silas.generator.helper.fileStrHelper;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.silas.generator.Config;
import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;
import com.silas.util.StringUtils;

public class EntityHepler implements CreateFileHelper {
	
	private Map<String,String> importStrs = new HashMap<String ,String>();
	
	// 1.生成实体类entity/EntityName.java文件
	public void createFile(){
		GeneratorUtil.createFile(getOutPutFile());
	}
	
	// 准备实体类entity/EntityName.java文件
	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "entity/" + entityName + ".java";
		// 类包名
		String packageStr = "package " + packagePath + ".entity;"+n2 ;
		// 类开始
		String classStart = n+"public class " + entityName + " {";
		// 属性定义
		String fieldStr = n+fieldStr();
		// 属性的Getter\Setter方法
		String getSetMothd = n+getSetMothd();
		// import导入类
		String importStr = importStr();
		// 类结束
		String classEnd = "}";
		// 拼接
		fileOutputStr = packageStr + importStr + classStart + fieldStr + getSetMothd + classEnd;

		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}

	//生成需要导入的相关import
	private String importStr() {
		String str = "";
		//遍历字段
		for(Column column : colList) {
			importStrs.put(column.getColumnTypeHelper().getImportStr(), column.getColumnTypeHelper().getImportStr());
		}
		Set<String> set = importStrs.keySet();
		for(String importStr:set) {
			str+=importStr;
		}
		return str;
	}
	//生成相关getter和setter方法
	private String getSetMothd() {
		String str = "";
		//遍历字段
		for(Column column : colList) {
			//getter方法
			String className =column.getColumnTypeHelper().getClassName();
			String entityField = column.getEntityField();
			String fieldUpperFirst = column.getEntityFieldUpperFisrt();
			str += n+tab+"public "+className+" get"+fieldUpperFirst+"(){";
			str += n+tab2+"return this."+entityField+";";
			str += n+tab+"}";
			
			//setter方法
			str += n+tab+"public void set"+fieldUpperFirst+"("+className+" "+entityField+"){";
			str += n+tab2+"this."+entityField+" = "+entityField+";";
			str += n+tab+"}";
		}
		return str;
	}
	//生成属性定义
	private String fieldStr() {
		String str = "";
		//遍历字段
		for(Column column : colList) {
			String className =column.getColumnTypeHelper().getClassName();
			if(Config.CODE_ID.equals("DHC_WEB")) {
				if(className.equals("Date")) {
					str+=n+"	@DateTimeFormat(pattern = \""+dateFormatPartten+"\")";
					importStrs.put(n+"import org.springframework.format.annotation.DateTimeFormat;", "");
				}
				str += n+tab+"private "+className+" "+column.getEntityField()+";//"+column.getRemark();
			}else {
				str += n+tab+"private "+className+" "+column.getEntityField()+";//"+column.getRemark();
			}
		}
		return str;
	}
}
