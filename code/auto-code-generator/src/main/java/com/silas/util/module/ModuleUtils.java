package com.silas.util.module;

import java.util.Collections;
import java.util.List;

import com.silas.generator.helper.interface_.Module;

public class ModuleUtils {
	/**
	 * 获得文件模块内容，此处为所有方法定义
	 */
	public static String getModulesStr(List<Module> modules) {
		Collections.sort(modules);
		String s = "";
		for(Module module:modules) {
			s += module.getFullStr()+"\n";
		}
		return s;
	}

}
