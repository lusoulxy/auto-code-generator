package com.silas.generator.helper.fileStrHelper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.ImportC;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleImport;
import com.silas.generator.helper.interface_.ModuleMethod;
import com.silas.util.GeneratorUtil;

public class MapperJavaHelperV2 implements CreateClassHelper{
	ModuleImport someImport = new ModuleImport();//记录要导入的包
	List<Module> modules = new ArrayList<Module>();;//要使用的模块
	//设置方法是否生成
	Map<String,Boolean> methodCreateMap = new HashMap<String,Boolean>();
	{
		//key:方法名，value：true 则生成 false 则不生成
		methodCreateMap.put("deleteByPrimaryKey", true);// 根据主键删除记录
		methodCreateMap.put("insert", true);//插入记录,含所有字段
		methodCreateMap.put("insertSelective", true);//插入记录，选择字段插入
		methodCreateMap.put("selectByPrimaryKey", true);///根据主键查询
		methodCreateMap.put("updateByPrimaryKey", true);//根据主键更新记录，所有字段更新
		methodCreateMap.put("updateByPrimaryKeySelective", true);//根据主键，选择字段进行更新
		methodCreateMap.put("getListByMap", true);//根据map查询list，用于分页查询
		methodCreateMap.put("getTotalNumByMap", true);//根据map查询总数
		methodCreateMap.put("getListByEntity", true);//根据实体类查询list
	}
	
	/**
	 * 生成mapper/EntityNameMapper.java文件
	 */
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	/**
	 * 封装要生成的mapper/EntityNameMapper.java文件
	 */
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
		String methods = getModulesStr(modules);
		
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
	@Override
	public List<Module> getModules() {
		// TODO Auto-generated method stub
		MapperJavaMethods mapperJavaMethods = new MapperJavaMethods();
		Class clazz = mapperJavaMethods.getClass();
		Method [] methods = clazz.getMethods();
		for(Method method : methods) {
			if(method.getName()==null||(methodCreateMap.get(method.getName())==null))
				continue;
			if(methodCreateMap.get(method.getName())) {
				try {
					ModuleMethod ModuleMethod= (ModuleMethod) method.invoke(mapperJavaMethods,null);
					modules.add(ModuleMethod);
				} catch (IllegalAccessException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		return modules;
	}

	/**
	 * 获得文件模块内容，此处为所有方法定义
	 */
	@Override
	public String getModulesStr(List<Module> modules) {
		String s = "";
		for(Module module:modules) {
			if(module instanceof ModuleMethod) {
				s += ((ModuleMethod)module).getMethodFullStr()+n;
			}
		}
		return s;
	}

	/**
	 * 获得所有要导入的包字符串拼接
	 */
	@Override
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
	
	class MapperJavaMethods{
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
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
			method.setMethodFullStr(fullStr);
			//导入的主键依赖的包
			method.putImport(primary_col.getImportStr());
			return method;
			
		}
	}
}
