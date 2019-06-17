package com.silas.generator.helper.fileStrHelper;

import java.util.ArrayList;
import java.util.List;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleField;
import com.silas.generator.helper.interface_.ModuleImport;
import com.silas.generator.helper.interface_.ModuleMethod;
import com.silas.util.GeneratorUtil;
import com.silas.util.module.ModuleUtils;

public class EntityHeplerV2 extends CreateClassHelper {
	ModuleImport someImport = new ModuleImport("import");// 记录要导入的包
	List<Module> modules = new ArrayList<Module>();;// 要使用的模块

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
		String classDesciption = "";
		// 类包名
		String packageStr = "package " + packagePath + ".entity;" + n2;
		// 类开始
		String classStart = n + "public class " + entityName + " {";
		// 属性定义
		String fieldStr = n + getFieldStr();
		// 属性的Getter\Setter方法
		String methodStr = n + getMethodStr();
		// import导入类
		String importStr = getImortStr(modules);
		// 类结束
		String classEnd = "}";
		// 拼接
		classDesciption = packageStr + importStr + classStart + fieldStr + methodStr + classEnd;
		return classDesciption;
	}

	private String getMethodStr() {
		List<Module> moduleMethods = new ArrayList<Module>();
		for(Module module : modules) {
			if(module instanceof ModuleMethod) {//找出method模块
				moduleMethods.add(module);
			}
		}
		return ModuleUtils.getModulesStr(moduleMethods);
	}

	/**
	 * 返回属性定义 字符串形式
	 * @return
	 */
	private String getFieldStr() {
		List<Module> moduleFileds = new ArrayList<Module>();
		for(Module module : modules) {
			if(module instanceof ModuleField) {
				moduleFileds.add(module);
			}
		}
		return ModuleUtils.getModulesStr(moduleFileds);
	}

	/**
	 * 获得
	 */
	public List<Module> getModules() {
		// 1.获得属性module
		String str = "";
		// 遍历字段
		for (Column column : colList) {
			ModuleField module = new ModuleField();
			String className = column.getColumnTypeHelper().getClassName();
			if (className.equals("Date")) {
				str += n + "	@DateTimeFormat(pattern = \"yyyy-MM-dd\")";
				module.setName(column.getColumName());
				module.putImport("org.springframework.format.annotation.DateTimeFormat");
			}
			str += n + t1 + "private " + className + " " + column.getEntityField() + ";//" + column.getRemark();
			module.setFullStr(str);
			module.putImport(column.getImportStr());
			modules.add(module);
		}
		// 2.获得属性getter和setter方法module
		str = "";
		// 遍历字段
		for (Column column : colList) {
			ModuleMethod module = new ModuleMethod();
			// getter方法
			String className = column.getColumnTypeHelper().getClassName();
			String entityField = column.getEntityField();
			String fieldUpperFirst = column.getEntityFieldUpperFisrt();
			str += n + t1 + "public " + className + " get" + fieldUpperFirst + "(){";
			str += n + t2 + "return this." + entityField + ";";
			str += n + t1 + "}";

			// setter方法
			str += n + t1 + "public void set" + fieldUpperFirst + "(" + className + " " + entityField + "){";
			str += n + t2 + "this." + entityField + " = " + entityField + ";";
			str += n + t1 + "}";
			module.putImport(column.getImportStr());
			modules.add(module);
		}

		return modules;
	}


}
