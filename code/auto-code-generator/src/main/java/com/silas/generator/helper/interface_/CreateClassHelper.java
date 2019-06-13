package com.silas.generator.helper.interface_;

import java.util.List;

public interface CreateClassHelper extends CreateFileHelperV2{
	
	String entityPackage =packagePath + ".entity." + entityName;
	
	public String getImortStr(List<Module> modules);//需要导入的依赖包
	
	
}
