package com.silas.generator.helper.interface_;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class CreateClassHelper extends CreateFileHelperV2{
	protected String entityPackage =packagePath + ".entity." + entityName;//实体类全名
	protected ModuleImport someImport = new ModuleImport("import");//记录要导入的包
	/**
	 * 获得所有要导入的包字符串拼接
	 */
	public String getImortStr(List<Module> modules) {
		String s = "";
		Map<String,String> map = new HashMap<String,String>();
		for(Module module:modules) {//合并所有Import,将重复的去掉
			map.putAll(((ModuleImport)module).getMethodImport());
		}
		for(String imp : map.keySet()) {//遍历Import，拼接字符串
			s +="import "+imp+";"+n;
		}
		return s;
	}
	
}
