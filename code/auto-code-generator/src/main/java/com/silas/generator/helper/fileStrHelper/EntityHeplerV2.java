package com.silas.generator.helper.fileStrHelper;

import java.util.ArrayList;
import java.util.List;

import com.silas.generator.Config;
import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleImport;
import com.silas.util.GeneratorUtil;

public class EntityHeplerV2 implements CreateClassHelper {
	ModuleImport someImport = new ModuleImport();//记录要导入的包
	List<Module> modules = new ArrayList<Module>();;//要使用的模块

	/**
	 * 生成entity/EntityName.java文件
	 */
	@Override
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}
	/**
	 * 装要生成的 实体类entity/EntityName.java文件
	 */
	@Override
	public OutPutFile getOutPutFile() {
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "entity/" + entityName + ".java";
		return GeneratorUtil.getOutPutFile(fileFullName, getOutPutContent());
	}

	/**
	 * 生成字符串文件
	 */
	@Override
	public String getOutPutContent() {
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

		return fileOutputStr;
	}

	/**
	 * 获得
	 */
	@Override
	public List<Module> getModules() {
		//1.获得属性module
		String str = "";
		//遍历字段
		for(Column column : colList) {
			String className =column.getColumnTypeHelper().getClassName();
			if(className.equals("Date")) {
				str+=n+"	@DateTimeFormat(pattern = \"yyyy-MM-dd\")";
				importStrs.put(n+"import org.springframework.format.annotation.DateTimeFormat;", "");
			}
			str += n+t1+"private "+className+" "+column.getEntityField()+";//"+column.getRemark();
		}
		//2.获得属性getter和setter方法module
		
		return null;
	}

	@Override
	public String getModulesStr(List<Module> modules) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getImortStr(List<Module> modules) {
		// TODO Auto-generated method stub
		return null;
	}

}
