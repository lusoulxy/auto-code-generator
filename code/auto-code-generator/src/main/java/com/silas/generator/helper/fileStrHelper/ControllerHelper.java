package com.silas.generator.helper.fileStrHelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;

public class ControllerHelper implements CreateFileHelper{
	
	Map<String,String> importMap = new HashMap<String,String>();
	
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
		importMap.put(n+"import org.springframework.stereotype.Controller;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.RequestMapping;", "");
		//classStart要导入的包
		// 类开始
		String classStart = n + 
							"/**\r\n" + 
							" * "+moduleName+"模块\r\n" + 
							" * @version 时间："+new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date())+"\r\n" + 
							" */\r\n" + 
							"@Controller\r\n" + 
							"@RequestMapping(\"/"+entityName+"\")\r\n" + 
							"public class "+entityName+"Controller {\r\n" ;
		//classBody要导入的包
		importMap.put(n+"import org.springframework.beans.factory.annotation.Autowired;", "");
		importMap.put(n+"import javax.servlet.http.HttpServletRequest;", "");
		importMap.put(n+"import "+packagePath+".service."+entityName+"Service;", "");
		importMap.put(n+"import com.hzsh.configuration.service.SystemLogService;", "");
		importMap.put(n+"import com.hzsh.configuration.entity.SystemLog;", "");
		
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
		String downloadTemplate = downloadTemplate();// 生成下载数据模板的方法
		String importExcelView = importExcelView();// 生成进入数据导入的界面的方法
		String importExcel = importExcel();// 生成数据导入的方法，导出格式为excel
		String exportExcel = exportExcel();//生成导出数据表数据，导出格式为excel
		
		//方法拼接
		classBody += addViewMethodStr+saveMethodStr+updateViewMethodStr+
				updateMethodStr+lisMethodStr+deleteMethodStr+exportExcel+
				importExcelView+importExcel+downloadTemplate;
//		classBody += lisMethodStr;
		// 类结束
		String classEnd =n+ "}";
		// import导入类
		String importStr = importStr()+n;
		// 拼接
		fileOutputStr = packageStr + importStr + classStart + classBody + classEnd;
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}
	
	/**
	 * 生成数据导入的方法，导出格式为excel
	 * @return
	 */
	private String importExcel() {
		importMap.put(n+"import org.springframework.web.bind.annotation.PostMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.RequestParam;", "");
		importMap.put(n+"import org.springframework.web.multipart.MultipartFile;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.ResponseBody;", "");
		String str = 
				"	\r\n" + 
				"	/*\r\n" + 
				"	 * 导入物料编码\r\n" + 
				"	 */\r\n" + 
				"	@ResponseBody"+
				"	@PostMapping(value = \"/importExcel\")\r\n" + 
				"	public String importExcel(@RequestParam(value = \"filename\") MultipartFile file) {\r\n" + 
				"\r\n" + 
				"		String message = \"导入成功\";\r\n" + 
				"\r\n" + 
				"		String fileName = file.getOriginalFilename();\r\n" + 
				"\r\n" + 
				"		try {\r\n" + 
				"			message = "+entityLowerName+"Service.importExcel(fileName, file);\r\n" + 
				"		} catch (Exception e) {\r\n" + 
				"			e.printStackTrace();\r\n" + 
				"			message = e.getMessage();\r\n" + 
				"		}\r\n" + 
				"//		System.out.println(\"message:\" + message);\r\n" + 
				"		return message;\r\n" + 
				"	}";
		
		return str;
	}

	/**
	 *  生成进入数据导入的界面的方法
	 * @return
	 */
	private String importExcelView() {
		importMap.put(n+"import org.springframework.web.bind.annotation.GetMapping;", "");
		importMap.put(n+"import org.springframework.ui.Model;", "");
		String str = n+
				"	/**\r\n" + 
				"	 * 返回excel导入界面\r\n" + 
				"	 * \r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 */\r\n" + 
				"	@GetMapping(\"/importExcelView\")\r\n" + 
				"	public String importExcelView(Model model) {\r\n" + 
				"		model.addAttribute(\"ok\", \"true\");\r\n" + 
				"		return \""+module+"/"+entityLowerName+"/importExcelView\";\r\n" + 
				"	}";
		return str;
	}

	/**
	 * 生成下载数据模板的方法
	 * @return
	 */
	private String downloadTemplate() {
		importMap.put(n+"import org.springframework.web.bind.annotation.RequestMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.ResponseBody;", "");
		importMap.put(n+"import java.io.OutputStream;", "");
		importMap.put(n+"import java.text.SimpleDateFormat;", "");
		importMap.put(n+"import java.util.Date;", "");
		importMap.put(n+"import java.util.List;", "");
		importMap.put(n+"import org.apache.poi.hssf.usermodel.HSSFRow;", "");
		importMap.put(n+"import org.apache.poi.hssf.usermodel.HSSFSheet;", "");
		importMap.put(n+"import org.apache.poi.hssf.usermodel.HSSFWorkbook;", "");
		importMap.put(n+"import java.io.IOException;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
		importMap.put(n+"import javax.servlet.http.HttpServletResponse;", "");
		String str = n+
				"	/**\r\n" + 
				"	 * 下载模板 数据库表"+tableName+"的excel导入模板\r\n" + 
				"	 * \r\n" + 
				"	 * @param response\r\n" + 
				"	 * @throws IOException\r\n" + 
				"	 */\r\n" + 
				"	@RequestMapping(value = \"/downloadTemplate\")\r\n" + 
				"	@ResponseBody\r\n" + 
				"	public void downloadTemplate(HttpServletResponse response) throws IOException {\r\n" + 
				"\r\n" + 
				"		List<"+entityName+"> list = "+entityLowerName+"Service.getListByEntity(new "+entityName+"());\r\n" + 
				"		HSSFWorkbook wb = new HSSFWorkbook();\r\n" + 
				"		HSSFSheet sheet = wb.createSheet(\""+moduleName+"\");\r\n" + 
				"		HSSFRow row = null;\r\n" + 
				"\r\n" + 
				"		//设置字段名\r\n" + 
				"		row = sheet.createRow(0);\r\n" + 
				"		row.setHeight((short) (22.50 * 20));// 设置行高\r\n";
		for(int i=0;i<colList.size();i++) {
			Column column = colList.get(i);
			if(column.isPkAuto()) {//如果主键自增，则不操作主键
				continue;
			}
			str+=	"		row.createCell("+i+").setCellValue(\""+column.getRemark()+"\");// 为第"+(i+1)+"个单元格设值\r\n" ; 
		}
		
		str+=	"		sheet.setDefaultRowHeight((short) (16.5 * 20));\r\n" + 
				"		// 列宽自适应\r\n" + 
				"		for (int i = 0; i <= "+colList.size()+"; i++) {\r\n" + 
				"			sheet.autoSizeColumn(i);\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		response.setContentType(\"application/vnd.ms-excel;charset=utf-8\");\r\n" + 
				"		OutputStream os = response.getOutputStream();\r\n" + 
				"		String dateString = new SimpleDateFormat(\"yyyyMMddHHmmss\").format(new Date());\r\n" + 
				"		String fileName = \""+moduleName+"导入模板\" + dateString + \".xls\";\r\n" + 
				"		response.setHeader(\"Content-disposition\",\r\n" + 
				"				\"attachment;filename=\" + new String(fileName.getBytes(\"gb2312\"), \"ISO8859-1\"));// 默认Excel名称\r\n" + 
				"		wb.write(os);\r\n" + 
				"		os.flush();\r\n" + 
				"		os.close();\r\n" + 
				"	}";
		return str;
	}
	/**
	 * 生成导出数据表数据，导出格式为excel
	 * @return
	 */
	private String exportExcel() {
		//此方法要导入的包
		importMap.put(n+"import org.springframework.web.bind.annotation.RequestMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.ResponseBody;", "");
		importMap.put(n+"import java.io.OutputStream;", "");
		importMap.put(n+"import java.text.SimpleDateFormat;", "");
		importMap.put(n+"import java.util.Date;", "");
		importMap.put(n+"import java.util.List;", "");
		importMap.put(n+"import org.apache.poi.hssf.usermodel.HSSFRow;", "");
		importMap.put(n+"import org.apache.poi.hssf.usermodel.HSSFSheet;", "");
		importMap.put(n+"import org.apache.poi.hssf.usermodel.HSSFWorkbook;", "");
		importMap.put(n+"import java.io.IOException;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
		importMap.put(n+"import javax.servlet.http.HttpServletResponse;", "");
		String str = 
				"	/**\r\n" + 
				"	 * 导出数据库表中的所有"+moduleName+"记录\r\n" + 
				"	 * \r\n" + 
				"	 * @param response\r\n" + 
				"	 * @throws IOException\r\n" + 
				"	 */\r\n" + 
				"	@RequestMapping(value = \"/exportExcel\")\r\n" + 
				"	@ResponseBody\r\n" + 
				"	public void exportExcel(HttpServletResponse response) throws IOException {\r\n" + 
				"\r\n" + 
				"		//查询数据库\r\n" + 
				"		List<"+entityName+"> list = "+entityLowerName+"Service.getListByEntity(new "+entityName+"());\r\n" + 
				"		//创建excel对象\r\n" + 
				"		HSSFWorkbook wb = new HSSFWorkbook();\r\n" + 
				"		HSSFSheet sheet = wb.createSheet(\""+moduleName+"\");\r\n" + 
				"		HSSFRow row = null;\r\n" + 
				"\r\n" + 
				"		//设置字段名\r\n" + 
				"		row = sheet.createRow(0);\r\n" + 
				"		row.setHeight((short) (22.50 * 20));// 设置行高\r\n";
		for(int i=0;i<colList.size();i++) {//导出列名 语句
			Column column = colList.get(i);
			if(column.isPkAuto()) {//如果主键自增，则不操作主键
				continue;
			}
			str+=	"		row.createCell("+i+").setCellValue(\""+column.getRemark()+"\");// 为第"+(i+1)+"个单元格设值\r\n" ; 
		}
		str+=	"		for (int i = 0; i < list.size(); i++) {\r\n" + 
				"			row = sheet.createRow(i + 1);\r\n" + 
				"			"+entityName+" "+entityLowerName+" = list.get(i);\r\n";
		for(int i=0;i<colList.size();i++) {//导出数据 语句
			Column column = colList.get(i);
			String remark = column.getRemark();
			String FieldUpper = column.getEntityFieldUpperFisrt();
			if(column.isPkAuto()) {//如果主键自增，则不操作主键
				continue;
			}
			if(column.getJavaType().equals("Date")) {//若为时间
				str+=n+
					"			if("+entityLowerName+".get"+FieldUpper+"()!=null) {//"+remark+"\r\n" + 
					"				String colvalue = new SimpleDateFormat(\""+dateFormatPartten+"\").format("+entityLowerName+".get"+FieldUpper+"());\r\n"+
					"				row.createCell("+i+").setCellValue("+entityLowerName+".get"+FieldUpper+"().toString());\r\n" +
					"			}else {\r\n" + 
					"				row.createCell(1).setCellValue(\"\");\r\n" + 
					"			}";
			}else {
				str+=n+
					"			if("+entityLowerName+".get"+FieldUpper+"()!=null) {//"+remark+"\r\n" + 
					"				row.createCell("+i+").setCellValue("+entityLowerName+".get"+FieldUpper+"().toString());\r\n"+
					"			}else {\r\n" + 
					"				row.createCell("+i+").setCellValue(\"\");\r\n" + 
					"			}";
			}
		}
		str+=	"		}\r\n"+ 
				"		sheet.setDefaultRowHeight((short) (16.5 * 20));\r\n" + 
				"		// 列宽自适应\r\n" + 
				"		for (int i = 0; i <= "+colList.size()+"; i++) {\r\n" + 
				"			sheet.autoSizeColumn(i);\r\n" + 
				"		}\r\n" + 
				"\r\n" + 
				"		response.setContentType(\"application/vnd.ms-excel;charset=utf-8\");\r\n" + 
				"		OutputStream os = response.getOutputStream();\r\n" + 
				"		String dateString = new SimpleDateFormat(\"yyyyMMddHHmmss\").format(new Date());\r\n" + 
				"		String fileName = \""+moduleName+"\" + dateString + \".xls\";\r\n" + 
				"		response.setHeader(\"Content-disposition\",\r\n" + 
				"				\"attachment;filename=\" + new String(fileName.getBytes(\"gb2312\"), \"ISO8859-1\"));// 默认Excel名称\r\n" + 
				"		wb.write(os);\r\n" + 
				"		os.flush();\r\n" + 
				"		os.close();\r\n" + 
				"	}";
		return str;
	}

	//生成所需导入的包importStr
	public String importStr(){
		// 通过ArrayList构造函数把map.entrySet()转换成list
		List<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(importMap.entrySet());
		// 通过比较器实现比较排序
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
		    @Override
		    public int compare(Map.Entry<String, String> mapping1, Map.Entry<String, String> mapping2) {
		        return mapping1.getKey().compareTo(mapping2.getKey());
		    }
		});
		String str = "";
		for(Map.Entry<String, String> entry : list) {
			str += entry.getKey();
		}
		return str;
	}
	
	// 生成新增记录查看页的方法
	public String addViewMethodStr(){
		//此方法要导入的包
		importMap.put(n+"import org.springframework.web.bind.annotation.GetMapping;", "");
		importMap.put(n+"import org.springframework.ui.Model;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
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
		//此方法要导入的包
		importMap.put(n+"import org.springframework.web.bind.annotation.PostMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.ModelAttribute;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
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
		//此方法要导入的包
		importMap.put(n+"import org.springframework.web.bind.annotation.GetMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.PathVariable;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
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
		//此方法要导入的包
		importMap.put(n+"import org.springframework.web.bind.annotation.GetMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.ModelAttribute;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
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
		//此方法要导入的包
		importMap.put(n+"import org.springframework.web.bind.annotation.GetMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.ModelAttribute;", "");
		importMap.put(n+"import org.springframework.ui.Model;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
		importMap.put(n+"import com.hzsh.util.params_trim.RequestParamsTrim;", "");
		importMap.put(n+"import java.util.Map;", "");
		importMap.put(n+"import java.util.List;", "");
		String str = n+
				"	/**\r\n" + 
				"	 * "+moduleName+"记录 列表页，默认以id降序\r\n" + 
				"	 * @param model\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@GetMapping(\"/list\")\r\n" + 
				"	public String list(Model model, @RequestParamsTrim Map<String, Object> searchMap) throws Exception {\r\n" + 
				"\r\n" + 
				"		int size = 20;//每页记录数\r\n" + 
				"		int totalPages = 1;//总页数\r\n" + 
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
				"			totalPages = (allnum + size - 1) / size;//计算总页数\r\n" + 
				"		}\r\n" + 
				"		//设置返回前端的数据\r\n" + 
				"		model.addAttribute(\"searchMap\", searchMap);\r\n" + 
				"		model.addAttribute(\"recordList\", recordList);\r\n" + 
				"		model.addAttribute(\"pageNum\", pageNum);\r\n" + 
				"		model.addAttribute(\"totalPages\", totalPages);\r\n" + 
				"		model.addAttribute(\"allnum\", allnum);\r\n" + 
				"		model.addAttribute(\"size\", size);\r\n" + 
				"		return \""+module+"/"+entityLowerName+"/list\";\r\n" + 
				"	}";
		return str;
	}
	
	// 生成删除记录的方法
	public String deleteMethodStr(){
		//此方法要导入的包
		importMap.put(n+"import org.springframework.web.bind.annotation.GetMapping;", "");
		importMap.put(n+"import org.springframework.web.bind.annotation.PathVariable;", "");
		importMap.put(n+"import org.springframework.ui.Model;", "");
		importMap.put(primary_col.getColumnTypeHelper().getImportStr(), "");//导入主键类型
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
