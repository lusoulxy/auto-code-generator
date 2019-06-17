package com.silas.generator.helper.fileStrHelper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.silas.generator.config.ImportC;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleMethod;
import com.silas.util.GeneratorUtil;
import com.silas.util.module.ModuleUtils;

public class RestControllerHelper extends CreateClassHelper{
	//生成的文件路径
	String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "controller/" + entityName + "RestController.java";
	//设置模块是否生成，和生成的顺序；value=0为不生成，数字越小排越前面
	{
		//key:方法名，value：true 则生成 false 则不生成
		toCreateModule.put("fields", 1);//fields 类的属性
		toCreateModule.put("save", 2);//save 保存（新增/更新）  记录
		toCreateModule.put("delectById", 3);//delectById 根据条件 删除 人物基本信息 记录
		toCreateModule.put("viewById", 4);//viewById 根据主键 查看记录详情
		toCreateModule.put("list", 5);//list 记录，默认以主键降序
	}

	// 生成mapper/EntityNameMapper.java文件
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备mapper/EntityNameMapper.java文件
	public OutPutFile getOutPutFile() {
		return GeneratorUtil.getOutPutFile(fileFullName, getOutPutContent());
	}

	@Override
	public String getOutPutContent() {
		String classDesciption = "";//类描述
		//1。包名
		String packageName =  "package " + packagePath + ".controller;" + n;
		//2。导入所依赖的包
		String importNeed = "";
		
		//3。开始
		String start =  n +
				 n + 
				"/**\r\n" + 
				" * "+moduleName+"模块 restful接口\r\n" + 
				" * @version 时间："+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"\r\n" + 
				" */\r\n" + 
				"@Controller\r\n" + 
				"@RequestMapping(\"/"+entityLowerName+"\")\r\n" + 
				"public class "+entityName+"RestController {\r\n" ;;
		
		//4.模块
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
	public List<Module> getModules() {
		InnerModules innerModeules = new InnerModules();
		return getModules(innerModeules,modules);
	}
	/**
	 * 
	 * @author WuGuoDa
	 * @version 时间：2019年6月14日 下午2:35:55
	 */
	public class InnerModules{
//		public ModuleMethod template(){
//			ModuleMethod method = new ModuleMethod();
//			//完整描述
//			String fullStr = n+
//					"";
//			method.setFullStr(fullStr);
//			//要导入的包
//			method.putImport(entityPackage);
//			return method;
//		}
		/*
		 * fields 类的属性
		 */
		public ModuleMethod fields(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	ResultBody resultBody;\r\n" + 
					"	@Autowired\r\n" + 
					"	private "+entityName+"Service "+entityLowerName+"Service;";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(packagePath+".service."+entityName);//服务层包
			method.putImport(ImportC.RESULTBODY);
			method.putImport(ImportC.AUTOWIRED);
			return method;
		}
		/*
		 * list 记录，默认以主键降序
		 */
		public ModuleMethod list(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 记录列表，默认以主键降序\r\n" + 
					"	 * @param searchMap\r\n" + 
					"	 * @return\r\n" + 
					"	 * @throws Exception\r\n" + 
					"	 */\r\n" + 
					"	@RequestMapping(\"/list\")\r\n" + 
					"	public ResultBody list(@RequestParamsTrim Map<String, Object> searchMap){\r\n" + 
					"		resultBody = new ResultBody();\r\n" + 
					"		resultBody.setMessage(\"操作成功\");\r\n" + 
					"		resultBody.setStatusCode(\"opt_success\");\r\n" + 
					"		try {\r\n" + 
					"			int size = 20;//每页记录数\r\n" + 
					"			int totalPages = 1;//总页数\r\n" + 
					"			int pageNum = 1;//当前页\r\n" + 
					"			int allnum = 0;//总记录数\r\n" + 
					"			if (searchMap.get(\"pageNum\") == null)//如果没有传入页数\r\n" + 
					"				searchMap.put(\"pageNum\", pageNum);//则默认第一页\r\n" + 
					"			else {//否则使用传入的页数\r\n" + 
					"				pageNum = Integer.parseInt(searchMap.get(\"pageNum\").toString());\r\n" + 
					"			}\r\n" + 
					"			searchMap.put(\"first\", (pageNum - 1) * size);//记录起始下标\r\n" + 
					"			searchMap.put(\"size\",  size);//记录结束下标\r\n" + 
					"			searchMap.put(\"limit\",  true);//分页查询\r\n" + 
					"			searchMap.put(\"orderStr\", \" id DESC\");//默认id排序\r\n" + 
					"			List<HumanBasicInfo> recordList = humanBasicInfoService.getListByMap(searchMap);//查询list\r\n" + 
					"			allnum = humanBasicInfoService.getTotalNumByMap(searchMap);// 查询总记录数\r\n" + 
					"			if (allnum > 0) {//如果有记录\r\n" + 
					"				totalPages = (allnum + size - 1) / size;//计算总页数\r\n" + 
					"			}\r\n" + 
					"			//设置返回前端的数据\r\n" + 
					"			resultBody.put(\"recordList\", recordList);\r\n" + 
					"			resultBody.put(\"pageNum\", pageNum);\r\n" + 
					"			resultBody.put(\"totalPages\", totalPages);\r\n" + 
					"			resultBody.put(\"allnum\", allnum);\r\n" + 
					"			resultBody.put(\"size\", size);\r\n" + 
					"		} catch (Exception e) {\r\n" + 
					"			resultBody.setMessage(e.getMessage());\r\n" + 
					"			resultBody.setStatusCode(\"opt_failure\");\r\n" + 
					"		}\r\n" + 
					"		resultBody.put(\"searchMap\", searchMap);\r\n" + 
					"		return resultBody;\r\n" + 
					"	}";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			method.putImport(ImportC.REQUESTMAPPING);
			method.putImport(ImportC.REQUESTPARAMSTRIM);
			method.putImport(ImportC.RESULTBODY);
			return method;
		}
		/*
		 * viewById 根据主键 查看记录详情
		 */
		public ModuleMethod viewById(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据主键 查看记录详情\r\n" + 
					"	 *\r\n" + 
					"	 * @param model\r\n" + 
					"	 * @return\r\n" + 
					"	 * @throws Exception\r\n" + 
					"	 */\r\n" + 
					"	@RequestMapping(\"/view/{id}\")\r\n" + 
					"	public ResultBody viewById(@PathVariable String id) {\r\n" + 
					"		resultBody = new ResultBody();\r\n" + 
					"		resultBody.setMessage(\"操作成功\");\r\n" + 
					"		resultBody.setMessage(\"opt_success\");\r\n" + 
					"		resultBody.put(\"params\",  id);\r\n" + 
					"		try {\r\n" + 
					"			resultBody.put(\"record\", "+entityLowerName+"Service.findById(id));\r\n" + 
					"		} catch (Exception e) {\r\n" + 
					"			resultBody.setMessage(\"操作失败\");\r\n" + 
					"			resultBody.setMessage(\"opt_failure\");\r\n" + 
					"		}\r\n" + 
					"		resultBody.put(\"operator\",\"/rest/"+entityLowerName+"/view/\"+id);\r\n" + 
					"		return resultBody;\r\n" + 
					"	}";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			method.putImport(ImportC.REQUESTMAPPING);
			method.putImport(ImportC.PATHVARIABLE);
			method.putImport(ImportC.RESULTBODY);
			return method;
		}
		
		/*
		 * delectById 根据主键 删除记录
		 */
		public ModuleMethod delectById(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 根据主键 删除记录\r\n" + 
					"	 * @param id \r\n" + 
					"	 * @param model\r\n" + 
					"	 * @return\r\n" + 
					"	 * @throws Exception\r\n" + 
					"	 */\r\n" + 
					"	@RequestMapping(\"/delect/{id}\")\r\n" + 
					"	public ResultBody delectById(@PathVariable String id) throws Exception {\r\n" + 
					"		resultBody = new ResultBody();\r\n" + 
					"		resultBody.setMessage(\"删除记录成功\");\r\n" + 
					"		resultBody.setStatusCode(\"delete_success\");\r\n" + 
					"		int suc = 0;\r\n" + 
					"		try {\r\n" + 
					"			"+entityName+" record = "+entityLowerName+"Service.findById(id);//先查询\r\n" + 
					"			if(record!=null) {\r\n" + 
					"				resultBody.put(\"record\", record);\r\n" + 
					"				suc = "+entityLowerName+"Service.delect(id);\r\n" + 
					"				if (suc == 1) {\r\n" + 
					"					resultBody.setMessage(\"删除记录:\" + id + \"成功\");\r\n" + 
					"				} else {\r\n" + 
					"					resultBody.setStatusCode(\"delectfailure\");\r\n" + 
					"					resultBody.setMessage(\"删除记录:\" + id + \"失败\");\r\n" + 
					"				}\r\n" + 
					"			}else{\r\n" + 
					"				throw new Exception(\"记录不存在！\");\r\n" + 
					"			}\r\n" + 
					"		} catch (Exception e) {\r\n" + 
					"			resultBody.setMessage(e.getMessage());\r\n" + 
					"			resultBody.setStatusCode(\"delete_failure\");\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		}\r\n" + 
					"		resultBody.put(\"record_id\", id);\r\n" + 
					"		resultBody.put(\"operator\",\"/rest/"+entityLowerName+"/delect/\"+id);\r\n" + 
					"		return resultBody;\r\n" + 
					"	}";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			method.putImport(ImportC.REQUESTMAPPING);
			method.putImport(ImportC.PATHVARIABLE);
			method.putImport(ImportC.RESULTBODY);
			return method;
		}
		
		/*
		 * save 保存（新增/更新）  记录
		 */
		public ModuleMethod save(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	/**\r\n" + 
					"	 * 保存（新增/更新） 人物基本信息  记录\r\n" + 
					"	 * @param record\r\n" + 
					"	 * @return\r\n" + 
					"	 * @throws Exception\r\n" + 
					"	 */\r\n" + 
					"	@RequestMapping(\"/save\")\r\n" + 
					"	public ResultBody save(@RequestParamsTrim "+entityName+" record) {\r\n" + 
					"		resultBody = new ResultBody();\r\n" + 
					"		resultBody.put(\"params\", record)\r\n;"+
					"		resultBody.setStatusCode(\"save_success\");\r\n" + 
					"		int suc=0;\r\n" + 
					"		try {\r\n" + 
					"			if(record.get"+primary_col.getEntityFieldUpperFisrt()+"()==null) {//新增操作\r\n" + 
					"				record.set"+primary_col.getEntityFieldUpperFisrt()+"(UUIDGen.getUUID());//生成UUID\r\n" + 
					"				record.setStatus(1);//默认1为未删除,0则已删除\r\n" + 
					"				record.setCreateTime(new Date());//创建时间\r\n" + 
					"				suc = "+entityLowerName+"Service.save(record);\r\n" + 
					"			}else {//更新操作\r\n" + 
					"				suc = "+entityLowerName+"Service.update(record)+1;\r\n" + 
					"			}\r\n" + 
					"			//结果检验\r\n" + 
					"			if (suc == 1) {\r\n" + 
					"				resultBody.setMessage(\"新增成功\");\r\n" + 
					"			} else if(suc == 2){\r\n" + 
					"				resultBody.setMessage(\"更新成功\");\r\n" + 
					"			}else {\r\n" + 
					"				resultBody.setMessage(\"操作失败\");\r\n" + 
					"				resultBody.setStatusCode(\"save_failure\");\r\n" + 
					"			}\r\n" + 
					"		} catch (Exception e) {\r\n" + 
					"			resultBody.setMessage(\"操作失败\");\r\n" + 
					"			resultBody.setMessage(e.getMessage());\r\n" + 
					"			resultBody.setStatusCode(\"save_failure\");\r\n" + 
					"			e.printStackTrace();\r\n" + 
					"		}\r\n" + 
					"		resultBody.put(\"record_id\", record.get"+primary_col.getEntityFieldUpperFisrt()+"());\r\n" + 
					"		resultBody.put(\"operator\",\"/rest/"+entityLowerName+"/save\");\r\n" + 
					"		return resultBody;\r\n" + 
					"	}";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			method.putImport(ImportC.REQUESTMAPPING);
			method.putImport(ImportC.REQUESTPARAMSTRIM);
			method.putImport(ImportC.UUIDGen);
			method.putImport(ImportC.RESULTBODY);
			return method;
		}
		
	}
}
