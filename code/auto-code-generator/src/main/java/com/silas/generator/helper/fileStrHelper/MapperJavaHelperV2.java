package com.silas.generator.helper.fileStrHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import com.silas.generator.config.ImportC;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleImport;
import com.silas.generator.helper.interface_.ModuleMethod;
import com.silas.util.GeneratorUtil;
import com.silas.util.module.ModuleUtils;

public class MapperJavaHelperV2 extends CreateClassHelper{
	//设置模块是否生成，和生成的顺序；value=0为不生成，数字越小排越前面
	{
		//key:方法名，value：true 则生成 false 则不生成
		toCreateModule.put("insert", 2);//插入记录,含所有字段
		toCreateModule.put("insertSelective", 3);//插入记录，选择字段插入
		toCreateModule.put("selectByPrimaryKey", 4);///根据主键查询
		toCreateModule.put("updateByPrimaryKey", 5);//根据主键更新记录，所有字段更新
		toCreateModule.put("updateByPrimaryKeySelective", 6);//根据主键，选择字段进行更新
		toCreateModule.put("getListByMap", 7);//根据map查询list，用于分页查询
		toCreateModule.put("getTotalNumByMap", 8);//根据map查询总数
		toCreateModule.put("getListByEntity", 9);//根据实体类查询list
		toCreateModule.put("deleteByPrimaryKey", 10);// 根据主键删除记录
	}
	
	/**
	 * 生成mapper/EntityNameMapper.java文件
	 */
	@Override
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	/**
	 * 封装要生成的mapper/EntityNameMapper.java文件
	 */
	@Override
	public OutPutFile getOutPutFile() {
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "mapper/" + entityName + "Mapper.java";
		return GeneratorUtil.getOutPutFile(fileFullName, getOutPutContent());
	}

	/**
	 * 生成字符串文件
	 */
	@Override
	public String getOutPutContent() {
		
		String classDesciption = "";//类描述
		//1。包名
		String packageName =  "package " + packagePath + ".mapper;" + n2;
		//2。导入所依赖的包
		String importNeed = "";
		//3。开始
		String start = "@Mapper" + n + "public interface " + entityName + "Mapper {" + n;
		someImport.putImport(ImportC.MAPPER);
		//4。方法
		modules = getModules();
		String methods = ModuleUtils.getModulesStr(modules);
		modules.add(someImport);
		importNeed = getImortStr(modules)+n;//获得所有要导入的包
		//5。结束
		String end = "}";
		//6。拼接
		classDesciption = packageName + importNeed + start + methods + end;
		//7。返回
		return classDesciption;
	}

	/**
	 * 获得要生成的方法，进行封装
	 */
	public List<Module> getModules() {
		// TODO Auto-generated method stub
		InnerModules innerModeules = new InnerModules();
		return getModules(innerModeules,modules);
	}
	
	public class InnerModules{
		/*
		 * 根据实体类查询list
		 */
		public ModuleMethod getListByEntity(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据实体类查询list\r\n" + 
					"	 * @param searchBy\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	List<"+entityName+"> getListByEntity("+entityName+" entity);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			method.putImport(ImportC.LIST);
			return method;
		}
		/*
		 * 根据map查询总数
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
					"	int getTotalNumByMap(Map<String, Object> map);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			method.putImport(ImportC.MAP);
			return method;
		}
		/*
		 * 根据map查询list，用于分页查询
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
					"	List<"+entityName+"> getListByMap(Map<String,Object> map);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			method.putImport(ImportC.LIST);
			method.putImport(ImportC.MAP);
			return method;
		}
		
		/*
		 * 根据主键，更新选择字段
		 */
		public ModuleMethod updateByPrimaryKey(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"    /**\r\n" + 
					"     * 根据主键，更新所有字段\r\n" + 
					"     * @param record\r\n" + 
					"     * @return\r\n" + 
					"     */\r\n" + 
					"    int updateByPrimaryKey("+entityName+" record);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			return method;
		}
		
		/*
		 * 根据主键，更新选择字段
		 */
		public ModuleMethod updateByPrimaryKeySelective(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"    /**\r\n" + 
					"     * 根据主键，更新选择字段\r\n" + 
					"     * @param record\r\n" + 
					"     * @return\r\n" + 
					"     */\r\n" + 
					"    int updateByPrimaryKeySelective("+entityName+" record);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			return method;
		}
		
		/*
		 * 根据主键查询字段
		 */
		public ModuleMethod selectByPrimaryKey(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"    /**\r\n" + 
					"     * 根据主键查询字段\r\n" + 
					"     * @param id\r\n" + 
					"     * @return\r\n" + 
					"     */\r\n" + 
					"	"+entityName+" selectByPrimaryKey("+primary_col.getJavaType()+" "+primary_col.getEntityField()+");";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(primary_col.getImportStr());
			return method;
		}
		
		/*
		 * 插入记录，选择字段插入
		 */
		public ModuleMethod insertSelective(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"    /**\r\n" + 
					"     * 插入记录，选择字段插入\r\n" + 
					"     * @param record\r\n" + 
					"     * @return\r\n" + 
					"     */\r\n" + 
					"    int insertSelective("+entityName+" record);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			return method;
		}
		
		/*
		 * 插入记录,含所有字段
		 */
		public ModuleMethod insert(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 插入记录,含所有字段\r\n" + 
					"	 * @param record\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"    int insert("+entityName+" record);";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			return method;
		}
		
		/*
		 * 根据主键删除
		 */
		public ModuleMethod deleteByPrimaryKey(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据主键删除\r\n" + 
					"	 * @param id\r\n" + 
					"	 * @return\r\n" + 
					"	 */\r\n" + 
					"	int deleteByPrimaryKey("+primary_col.getJavaType()+" "+primary_col.getEntityField()+");";
			method.setFullStr(fullStr);
			//导入的主键依赖的包
			method.putImport(primary_col.getImportStr());
			return method;
			
		}
	}
}
