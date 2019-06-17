package com.silas.generator.helper.fileStrHelper;

import java.util.List;

import com.silas.generator.config.ImportC;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleMethod;
import com.silas.util.GeneratorUtil;
import com.silas.util.module.ModuleUtils;

public class ServiceHelper extends CreateClassHelper{
	String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "service/" + entityName + "Service.java";
	//设置模块是否生成，和生成的顺序；value=0为不生成，数字越小排越前面
	{
		//key:方法名，value：true 则生成 false 则不生成
		toCreateModule.put("save", 2);//save 保存记录，选择字段保存
		toCreateModule.put("update", 3);//update 根据主键更新记录，选择字段更新 
		toCreateModule.put("delect", 4);///delect 根据主键删除
		toCreateModule.put("findById", 5);//findById 根据主键删除
		toCreateModule.put("getListByMap", 6);// getListByMap 根据map查询list，用于分页查询
		toCreateModule.put("getTotalNumByMap", 7);//getTotalNumByMap 根据map查询总数
		toCreateModule.put("getListByEntity", 8);//getListByEntity 根据实体类查询list
//		toCreateModule.put("importExcel", 9);//importExcel 导入excel
	}

	// 生成mapper/EntityNameMapper.java文件
	public void createFile()  throws Exception {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备mapper/EntityNameMapper.java文件
	public OutPutFile getOutPutFile()  throws Exception {
		return GeneratorUtil.getOutPutFile(fileFullName, getOutPutContent());
	}

	@Override
	public String getOutPutContent() throws Exception {
		String classDesciption = "";//类描述
		//1。包名
		String packageName =  "package " + packagePath + ".service;" + n2;
		//2。导入所依赖的包
		String importNeed = "";
		
		//3。开始
		String start =  n + "public interface " + entityName + "Service {" + n;
		
		//4。方法
		List<Module> modlues = getModules();
		String methods = ModuleUtils.getModulesStr(modlues);
		importNeed = getImortStr(modlues);
		//5。结束
		String end = "}";
		//6。拼接
		classDesciption = packageName + importNeed + start + methods + end;
		//7。返回
		return classDesciption;
	}

	@Override
	public List<Module> getModules()  throws Exception {
		InnerModules innerModeules = new InnerModules();
		return getModules(innerModeules,modules);
	}
	/**
	 * 
	 * @author WuGuoDa
	 * @version 时间：2019年6月14日 下午2:35:55
	 */
	public class InnerModules{
		/*
		 * importExcel 导入excel
		 */
		public ModuleMethod importExcel(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 导入excel\r\n" + 
					"	 * @param fileName\r\n" + 
					"	 * @param file\r\n" + 
					"	 * @return\r\n" + 
					"	 * @throws Exception\r\n" + 
					"	 */\r\n" + 
					"	String importExcel(String fileName, MultipartFile file) throws Exception;}" ;
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(ImportC.MULTIPARTFILE);
			method.putImport(entityPackage);
			return method;
		}
		/*
		 * getListByEntity 根据实体类查询
		 */
		public ModuleMethod getListByEntity(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据实体类查询list\r\n" + 
					"	 * @param record\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	public List<"+entityName+"> getListByEntity("+entityName+" record);" ;
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(ImportC.LIST);
			method.putImport(entityPackage);
			return method;
		}
		/*
		 * getTotalNumByMap 根据map查询总数
		 */
		public ModuleMethod getTotalNumByMap(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据map查询总数\r\n" + 
					"	 * @param map\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	public int getTotalNumByMap(Map<String, Object> map);" ;
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(ImportC.MAP);
			return method;
		}
		/*
		 * getListByMap 根据map查询list，用于分页查询
		 */
		public ModuleMethod getListByMap(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据map查询list，用于分页查询\r\n" + 
					"	 * @param map\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	public List<"+entityName+"> getListByMap(Map<String, Object> map);" ;
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(ImportC.MAP);
			method.putImport(ImportC.LIST);
			method.putImport(entityPackage);
			return method;
		}
		/*
		 * findById 根据主键删除
		 */
		public ModuleMethod findById(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据主键查询\r\n" + 
					"	 * @param id\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	public "+entityName+" findById("+primary_col.getJavaType()+" "+primary_col.getColumName()+");" ;
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(primary_col.getImportStr());
			method.putImport(entityPackage);
			return method;
		}
		/*
		 * delect 根据主键删除
		 */
		public ModuleMethod delect(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据主键删除\r\n" + 
					"	 * @param id\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	public int delect("+primary_col.getJavaType()+" "+primary_col.getEntityField()+");";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(primary_col.getImportStr());
			return method;
		}
		/*
		 * update 根据主键更新记录，选择字段更新 
		 */
		public ModuleMethod update(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"		/**\r\n" + 
					"	 * 根据主键更新记录，选择字段更新\r\n" + 
					"	 * @param record\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	public int update("+entityName+" record);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			return method;
		}
		/*
		 * save 保存记录，选择字段保存
		 */
		public ModuleMethod save(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 保存记录，选择字段保存\r\n" + 
					"	 * @param record\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	public int save("+entityName+" record);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			return method;
		}
		
	}
}
