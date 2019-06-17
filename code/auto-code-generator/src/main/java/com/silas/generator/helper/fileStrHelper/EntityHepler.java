package com.silas.generator.helper.fileStrHelper;

import java.util.List;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleField;
import com.silas.generator.helper.interface_.ModuleMethod;
import com.silas.util.GeneratorUtil;
import com.silas.util.module.ModuleUtils;

public class EntityHepler extends CreateClassHelper {
	// 设置模块是否生成，和生成的顺序；value=0为不生成，数字越小排越前面
	String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "entity/" + entityName + ".java";
	/**
	 * 生成mapper/EntityNameMapper.java文件
	 */
	@Override
	public void createFile() throws Exception {
		GeneratorUtil.createFile(getOutPutFile());
	}
	/**
	 * 封装要生成的mapper/EntityNameMapper.java文件
	 */
	@Override
	public OutPutFile getOutPutFile() throws Exception {
		return GeneratorUtil.getOutPutFile(fileFullName, getOutPutContent());
	}

	/**
	 * 生成字符串文件
	 */
	@Override
	public String getOutPutContent() throws Exception {
		String classDesciption = "";// 类描述
		// 1。包名
		String packageName = "package " + packagePath + ".entity;" + n;
		// 2。导入所依赖的包
		String importNeed = "";
		// 3。开始
		String start = "public class "+entityName +" {" + n;
		// 4。方法
		modules = getModules();
		String methods = ModuleUtils.getModulesStr(modules);
		modules.add(someImport);
		importNeed = getImortStr(modules) + n;// 获得所有要导入的包
		// 5。结束
		String end = "}";
		// 6。拼接
		classDesciption = packageName + importNeed + start + methods + end;
		// 7。返回
		return classDesciption;
	}

	/**
	 * 获得要生成的方法，进行封装
	 * 
	 * @throws Exception
	 */
	public List<Module> getModules() throws Exception {
		int order=1;//排序号
		for (Column column : colList) {// 遍历字段
			// 1.获得属性module
			String field = "";
			ModuleField moduleField = new ModuleField();
			String className = column.getColumnTypeHelper().getClassName();
			String entityField = column.getEntityField();
			if (className.equals("Date")) {
				field += n + "	@DateTimeFormat(pattern = \"yyyy-MM-dd\")";
				moduleField.setName(column.getColumName());
				moduleField.putImport("org.springframework.format.annotation.DateTimeFormat");
			}
			field += n + t1 + "private " + className + " " + entityField + ";//" + column.getRemark();
			moduleField.setFullStr(field);
			moduleField.putImport(column.getImportStr());
			moduleField.setOrder(order);
			modules.add(moduleField);
			
			// 2.获得属性getter和setter方法module
			ModuleMethod modeleMthod = new ModuleMethod();
			// getter方法
			String fieldUpperFirst = column.getEntityFieldUpperFisrt();
			String method = "";
			method += n + t1 + "public " + className + " get" + fieldUpperFirst + "(){";
			method += n + t2 + "return this." + entityField + ";";
			method += n + t1 + "}";

			// setter方法
			method += n + t1 + "public void set" + fieldUpperFirst + "(" + className + " " + entityField + "){";
			method += n + t2 + "this." + entityField + " = " + entityField + ";";
			method += n + t1 + "}";
			modeleMthod.setFullStr(method);
			modeleMthod.putImport(column.getImportStr());
			modeleMthod.setOrder(colList.size()+order);
			modules.add(modeleMthod);
			order++;
		}
		return modules;
	}

}
