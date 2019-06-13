package com.silas.generator.helper.fileStrHelper;

import java.util.List;

import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.util.GeneratorUtil;

public class ServiceHelperV2 implements CreateClassHelper{

	// 生成mapper/EntityNameMapper.java文件
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备mapper/EntityNameMapper.java文件
	public OutPutFile getOutPutFile() {
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "mapper/" + entityName + "Mapper.java";
		return GeneratorUtil.getOutPutFile(fileFullName, getOutPutContent());
	}

	@Override
	public String getOutPutContent() {
		String classDesciption = "";//类描述
		//1。包名
		String packageName =  "package " + packagePath + ".mapper;" + n2;
		//2。导入所依赖的包
		String importNeed = "";
		
		//3。开始
		String start = "@Mapper" + n + "public interface " + entityName + "Mapper {" + n;
		
		//4。方法
		List<Module> modlues = getModules();
		String methods = getModulesStr(modlues);
		importNeed = getImortStr(modlues);
		//5。结束
		String end = "}";
		//6。拼接
		classDesciption = packageName + importNeed + start + methods + end;
		//7。返回
		return classDesciption;
	}

	@Override
	public List<Module> getModules() {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public String getImortStr(List<Module> modules) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getModulesStr(List<Module> modules) {
		// TODO Auto-generated method stub
		return null;
	}

}
