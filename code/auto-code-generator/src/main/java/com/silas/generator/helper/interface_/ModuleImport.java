package com.silas.generator.helper.interface_;

import java.util.HashMap;
import java.util.Map;

/**
 * 包含需要导入包的模块
 * @author WuGuoDa
 * @version 时间：2019年6月13日 下午4:12:21
 */
public class ModuleImport implements Module{
	protected Map<String,String> methodImport = new HashMap<String,String>();//导入依赖的包

	public Map<String, String> getMethodImport() {
		return methodImport;
	}

	public void setMethodImport(Map<String, String> methodImport) {
		this.methodImport = methodImport;
	}
	public void putImport(String importStr) {
		if(importStr!=null&&!(importStr.trim().equals("")))//不为空
			methodImport.put(importStr, importStr);
	}
	
	public String getMethodImportStr() {
		String str = "";
		if(methodImport!=null&&methodImport.size()>0) {
			for(String importStr : methodImport.keySet()) {
				str += "import "+importStr+";\n";
			}
		}
		return str;
	}
	
}
