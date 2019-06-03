package com.silas.generator.helper.fileStrHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.Config;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;

public class ControllerHelper implements CreateFileHelper{
	
	// 生成controller/EntityNameController.java文件
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备controller/EntityNameController.java文件
	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "controller/" + entityName + "Controller.java";
		// 类包名
		String packageStr = "package " + packagePath + ".controller;" + n;
		// import导入类
		String importStr = importStr()+n;
		// 类开始
		String classStart = n + 
							"/**\r\n" + 
							" * "+moduleName+"模块\r\n" + 
							" * @version 时间："+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"\r\n" + 
							" */\r\n" + 
							"@Controller\r\n" + 
							"@RequestMapping(\"/"+entityName+"\")\r\n" + 
							"public class "+entityName+"Controller {\r\n" ;
		//类内容
		String classBody = 
				"\r\n" + 
				"	@Autowired\r\n" + 
				"	private "+entityName+"Service "+entityLowerName+"Service;\r\n" + 
				"	@Autowired\r\n" + 
				"	HttpServletRequest request;\r\n" + 
				"	@Autowired\r\n" + 
				"	private SystemLogService systemLogService;\r\n" + 
				"\r\n" + 
				"	SystemLog systemLog = new SystemLog();// 日志对象\r\n" ;
		//类方法
		String addViewMethodStr	 = addViewMethodStr();// 生成新增记录查看页的方法
		String saveMethodStr= saveMethodStr();// 生成新增记录的方法
		String updateViewMethodStr = updateViewMethodStr();// 生成更新记录查看页的方法
		String updateMethodStr = updateMethodStr();// 生成更新记录的方法
		String lisMethodStr = listMethodStr();// 生成记录列表页的方法
		String deleteMethodStr = deleteMethodStr();// 生成删除记录的方法
		
		classBody += addViewMethodStr+saveMethodStr+updateViewMethodStr+
				updateMethodStr+lisMethodStr+deleteMethodStr;
		// 类结束
		String classEnd = "}";
		// 拼接
		fileOutputStr = packageStr + importStr + classStart + classBody + classEnd;
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}
	
	//生成所需导入的包importStr
	public String importStr(){
		String str = n+
				"import java.util.Date;\r\n" + 
				"import java.util.List;\r\n" + 
				"import java.util.Map;\r\n" + 
				"\r\n" + 
				"import javax.servlet.http.HttpServletRequest;\r\n" + 
				"\r\n" + 
				"import org.springframework.beans.factory.annotation.Autowired;\r\n" + 
				"import org.springframework.stereotype.Controller;\r\n" + 
				"import org.springframework.ui.Model;\r\n" + 
				"import org.springframework.web.bind.annotation.GetMapping;\r\n" + 
				"import org.springframework.web.bind.annotation.ModelAttribute;\r\n" + 
				"import org.springframework.web.bind.annotation.PathVariable;\r\n" + 
				"import org.springframework.web.bind.annotation.PostMapping;\r\n" + 
				"import org.springframework.web.bind.annotation.RequestMapping;\r\n" + 
				"import org.springframework.web.bind.annotation.RequestParam;\r\n" + 
				"\r\n" + 
				"import com.hzsh.configuration.entity.SystemLog;\r\n" + 
				"import com.hzsh.configuration.service.SystemLogService;\r\n" + 
				"import "+packagePath+".entity."+entityName+";\r\n" + 
				"import "+packagePath+".service."+entityName+"Service;"+
				primary_col.getColumnTypeHelper().getImportStr();
		return str;
	}
	
	// 生成新增记录查看页的方法
	public String addViewMethodStr(){
		String str = n+
				"	/**\r\n" + 
				"	 * 新增 "+moduleName+"记录 查看页\r\n" + 
				"	 * \r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@GetMapping(\"/add\")\r\n" + 
				"	public String addView(Model model) throws Exception {\r\n" + 
				"		model.addAttribute(\""+entityLowerName+"\", new "+entityName+"());\r\n" + 
				"		return \""+module+"/"+entityLowerName+"/add\";\r\n" + 
				"	}";
		return str;
	}
	
	// 生成新增记录的方法
	public String saveMethodStr(){
		String str = n+
				"	/**\r\n" + 
				"	 * 新增 "+moduleName+"记录 \r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@PostMapping(\"/save\")\r\n" + 
				"	public String save(@ModelAttribute "+entityName+" "+entityLowerName+") {\r\n" + 
				"		int suc = 0;\r\n" + 
				"		String message = \"\";//所回前端的消息\r\n" + 
				"		String message1 = \"\";//保存日志的消息\r\n" + 
				"		systemLog.setCreatetime(new Date());\r\n" + 
				"		try {\r\n" + 
				"			suc = "+entityLowerName+"Service.save("+entityLowerName+");\r\n" + 
				"			if (suc == 1) {\r\n" + 
				"				message = \"savesuccess\";\r\n" + 
				"				message1 = \"新增成功\";\r\n" + 
				"			} else {\r\n" + 
				"				message = \"savefailure\";\r\n" + 
				"				message1 = \"新增失败\";\r\n" + 
				"			}\r\n" + 
				"		} catch (Exception e) {\r\n" + 
				"			message = e.getMessage();\r\n" + 
				"			message1 = e.getMessage();\r\n" + 
				"			e.printStackTrace();\r\n" + 
				"		}\r\n" + 
				"		systemLog.setAction(\"新增"+moduleName+"记录\");\r\n" + 
				"		systemLog.setEndtime(new Date());\r\n" + 
				"		systemLog.setRemark(message1);\r\n" + 
				"		systemLogService.save(systemLog);// 保持日志\r\n" + 
				"		return \"redirect:/"+entityName+"/list?message=\" + message;\r\n" + 
				"	}";
		return str;
	}
	
	// 生成更新记录查看页的方法
	public String updateViewMethodStr(){
		String str = n+
				"	/**\r\n" + 
				"	 * 更新 "+moduleName+"记录 查看页\r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@GetMapping(\"/updateview/{"+primary_col.getEntityField()+"}\")\r\n" + 
				"	public String updateview(@PathVariable(name = \""+primary_col.getEntityField()+"\") "+primary_col.getJavaType()+" "+primary_col.getEntityField()+", Model model) throws Exception {\r\n" + 
				"\r\n" + 
				"		"+entityName+" "+entityLowerName+" = new "+entityName+"();\r\n" + 
				"\r\n" + 
				"		"+entityLowerName+" = "+entityLowerName+"Service.findById("+primary_col.getEntityField()+");\r\n" + 
				"\r\n" + 
				"		model.addAttribute(\""+entityLowerName+"\", "+entityLowerName+");\r\n" + 
				"		return \""+module+"/"+entityLowerName+"/updateview\";\r\n" + 
				"	}";
		return str;
	}
	
	// 生成更新记录的方法
	public String updateMethodStr(){
		String str = n+
				"	/**\r\n" + 
				"	 * 更新 "+moduleName+" 记录\r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@PostMapping(\"/update\")\r\n" + 
				"	public String upate(@ModelAttribute "+entityName+" "+entityLowerName+", Model model) throws Exception {\r\n" + 
				"		int suc = 0;\r\n" + 
				"		String message = \"\";\r\n" + 
				"		String message1 = \"\";\r\n" + 
				"		systemLog.setCreatetime(new Date());\r\n" + 
				"		try {\r\n" + 
				"			suc = "+entityLowerName+"Service.update("+entityLowerName+");\r\n" + 
				"			if (suc == 1) {\r\n" + 
				"				message = \"updatesuccess\";\r\n" + 
				"				message1 = \"更新成功\";\r\n" + 
				"			} else {\r\n" + 
				"				message = \"updatefailure\";\r\n" + 
				"				message1 = \"更新失败\";\r\n" + 
				"			}\r\n" + 
				"		} catch (Exception e) {\r\n" + 
				"			message = e.getMessage();\r\n" + 
				"			message1 = e.getMessage();\r\n" + 
				"			e.printStackTrace();\r\n" + 
				"		}\r\n" + 
				"		systemLog.setAction(\"更新"+moduleName+"记录\");\r\n" + 
				"		systemLog.setEndtime(new Date());\r\n" + 
				"		systemLog.setRemark(message1);\r\n" + 
				"		systemLogService.save(systemLog);// 保持日志\r\n" + 
				"		return \"redirect:/"+entityName+"/list?message=\" + message;\r\n" + 
				"	}";
		return str;
	}
	
	// 生成记录列表页的方法
	public String listMethodStr(){
		String str = n+
				"	/**\r\n" + 
				"	 * "+moduleName+"记录 列表页，默认以id降序\r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@GetMapping(\"/list\")\r\n" + 
				"	public String list(Model model, @RequestParam Map<String, Object> searchMap) throws Exception {\r\n" + 
				"\r\n" + 
				"		int size = 20;//每页记录数\r\n" + 
				"		int TotalPages = 1;//总页数\r\n" + 
				"		int pageNum = 1;//当前页\r\n" + 
				"		int allnum = 0;//总记录数\r\n" + 
				"		if (searchMap.get(\"pageNum\") == null)//如果没有传入页数\r\n" + 
				"			searchMap.put(\"pageNum\", pageNum);//则默认第一页\r\n" + 
				"		else {//否则使用传入的页数\r\n" + 
				"			pageNum = Integer.parseInt(searchMap.get(\"pageNum\").toString());\r\n" + 
				"		}\r\n" + 
				"		searchMap.put(\"first\", (pageNum - 1) * size + 1);//记录起始下标\r\n" + 
				"		searchMap.put(\"last\", pageNum * size);//记录结束下标\r\n" + 
				"		searchMap.put(\"orderStr\", \" g."+primary_col.getColumName()+" DESC\");//默认id排序\r\n" + 
				"		List<"+entityName+"> recordList = "+entityLowerName+"Service.getListByMap(searchMap);//查询list\r\n" + 
				"		allnum = "+entityLowerName+"Service.getTotalNumByMap(searchMap);// 查询总记录数\r\n" + 
				"		if (allnum > 0) {//如果有记录\r\n" + 
				"			TotalPages = (allnum + size - 1) / size;//计算总页数\r\n" + 
				"		}\r\n" + 
				"		//设置返回前端的数据\r\n" + 
				"		model.addAttribute(\"searchMap\", searchMap);\r\n" + 
				"		model.addAttribute(\"recordList\", recordList);\r\n" + 
				"		model.addAttribute(\"pageNum\", pageNum);\r\n" + 
				"		model.addAttribute(\"TotalPages\", TotalPages);\r\n" + 
				"		model.addAttribute(\"allnum\", allnum);\r\n" + 
				"		model.addAttribute(\"size\", size);\r\n" + 
				"		return \""+module+"/"+entityLowerName+"/list\";\r\n" + 
				"	}";
		return str;
	}
	
	// 生成删除记录的方法
	public String deleteMethodStr(){
		String str = n+
				"	/**\r\n" + 
				"	 * 根据id删除 "+moduleName+"记录\r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@GetMapping(\"/delect/{"+primary_col.getEntityField()+"}\")\r\n" + 
				"	public String delect(Model model, @PathVariable "+primary_col.getJavaType()+" "+primary_col.getEntityField()+") throws Exception {\r\n" + 
				"		int suc = 0;\r\n" + 
				"		String message = \"\";\r\n" + 
				"		String message1 = \"\";\r\n" + 
				"		try {\r\n" + 
				"			suc = this."+entityLowerName+"Service.delect("+primary_col.getEntityField()+");\r\n" + 
				"			if (suc == 1) {\r\n" + 
				"				message = \"delectsuccess\";\r\n" + 
				"				message1 = \"删除"+primary_col.getEntityField()+":\" + "+primary_col.getEntityField()+" + \"成功\";\r\n" + 
				"			} else {\r\n" + 
				"				message = \"delectfailure\";\r\n" + 
				"				message1 = \"删除"+primary_col.getEntityField()+":\" + "+primary_col.getEntityField()+" + \"失败\";\r\n" + 
				"			}\r\n" + 
				"		} catch (Exception e) {\r\n" + 
				"			message = e.getMessage();\r\n" + 
				"			message1 = e.getMessage();\r\n" + 
				"			e.printStackTrace();\r\n" + 
				"		}\r\n" + 
				"		systemLog.setAction(\"删除"+moduleName+"记录\");\r\n" + 
				"		systemLog.setCreatetime(new Date());\r\n" + 
				"		systemLog.setEndtime(new Date());\r\n" + 
				"		systemLog.setRemark(message1);\r\n" + 
				"		systemLogService.save(systemLog);// 保持日志\r\n" + 
				"		return \"redirect:/"+entityName+"/list?message=\" + message;\r\n" + 
				"	}";
		return str;
	}
}
