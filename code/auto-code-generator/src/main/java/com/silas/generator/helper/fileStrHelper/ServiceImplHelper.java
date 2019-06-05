package com.silas.generator.helper.fileStrHelper;

import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateFileHelper;
import com.silas.util.GeneratorUtil;
import com.silas.util.StringUtils;

public class ServiceImplHelper implements CreateFileHelper{
	Map<String,String> importMap = new HashMap<String,String>();
	// 生成service/EntityNameServiceImpl.java文件
	public void createFile() {
		GeneratorUtil.createFile(getOutPutFile());
	}

	// 准备service/EntityNameServiceImpl.java文件
	public OutPutFile getOutPutFile() {
		String fileOutputStr = "";
		String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "service/" + entityName + "ServiceImpl.java";
		// 类包名
		String packageStr = "package " + packagePath + ".service;" + n2;
		
		importMap.put(n+"import java.util.List;", "");
		importMap.put(n+"import java.util.Map;", "");
		importMap.put(n+"import org.springframework.beans.factory.annotation.Autowired;", "");
		importMap.put(n+"import org.springframework.stereotype.Service;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";"  , "");
		importMap.put(n+"import "+packagePath+".mapper."+entityName+"Mapper;", "");
		
		// 类开始
		String classStart = n+"@Service"+
				n + "public class " + entityName + "ServiceImpl implements " + entityName + "Service {" + n;

		String classBody = "	@Autowired\r\n" + 
				"	private "+entityName+"Mapper "+StringUtils.getLowerCamelCase(entityName)+"Mapper;\r\n" + 
				"\r\n" + 
				"	// 保存记录，选择字段保存\r\n" + 
				"	@Override\r\n" + 
				"	public int save("+entityName+" "+StringUtils.getLowerCamelCase(entityName)+") {\r\n" + 
				"		return "+StringUtils.getLowerCamelCase(entityName)+"Mapper.insert("+StringUtils.getLowerCamelCase(entityName)+");\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	// 根据主键更新记录，选择字段更新\r\n" + 
				"	@Override\r\n" + 
				"	public int update("+entityName+" "+StringUtils.getLowerCamelCase(entityName)+") {\r\n" + 
				"		return "+StringUtils.getLowerCamelCase(entityName)+"Mapper.updateByPrimaryKey("+StringUtils.getLowerCamelCase(entityName)+");\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	// 根据主键删除\r\n" + 
				"	@Override\r\n" + 
				"	public int delect("+primary_col.getJavaType()+" "+primary_col.getEntityField()+") {\r\n" + 
				"		return "+StringUtils.getLowerCamelCase(entityName)+"Mapper.deleteByPrimaryKey("+primary_col.getEntityField()+");\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	// 根据主键查询\r\n" + 
				"	@Override\r\n" + 
				"	public "+entityName+" findById("+primary_col.getJavaType()+" "+primary_col.getEntityField()+") {\r\n" + 
				"		return "+StringUtils.getLowerCamelCase(entityName)+"Mapper.selectByPrimaryKey("+primary_col.getEntityField()+");\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	// 根据map查询list，用于分页查询\r\n" + 
				"	@Override\r\n" + 
				"	public List<"+entityName+"> getListByMap(Map<String, Object> map) {\r\n" + 
				"		return "+StringUtils.getLowerCamelCase(entityName)+"Mapper.getListByMap(map);\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	// 根据map查询总数\r\n" + 
				"	@Override\r\n" + 
				"	public int getTotalNumByMap(Map<String, Object> map) {\r\n" + 
				"		return "+StringUtils.getLowerCamelCase(entityName)+"Mapper.getTotalNumByMap(map);\r\n" + 
				"	}\r\n" + 
				"\r\n" + 
				"	//根据实体类查询list\r\n" + 
				"	@Override\r\n" + 
				"	public List<"+entityName+"> getListByEntity("+entityName+" "+StringUtils.getLowerCamelCase(entityName)+") {\r\n" + 
				"		// TODO Auto-generated method stub\r\n" + 
				"		return "+StringUtils.getLowerCamelCase(entityName)+"Mapper.getListByEntity("+StringUtils.getLowerCamelCase(entityName)+");\r\n" + 
				"	}\r\n" + n;
		String importExcel = importExcel();
		
		// 类结束
		String classEnd = "}";
		// import导入类
				String importStr = importStr();
		// 拼接
		fileOutputStr = packageStr + importStr + classStart + classBody +importExcel+ classEnd;
		return GeneratorUtil.getOutPutFile(fileFullName, fileOutputStr);
	}
	//生成所需导入的包importStr
	public String importStr(){
		String str = "";
		for(String importStr : importMap.keySet()) {
			str += importStr;
		}
		return str;
	}

	private String importExcel() {
		//此方法要导入的包
		importMap.put(n+"import java.util.List;", "");
		importMap.put(n+"import org.springframework.web.multipart.MultipartFile;", "");
		importMap.put(n+"import org.apache.poi.xssf.usermodel.XSSFWorkbook;", "");
		importMap.put(n+"import org.apache.poi.ss.usermodel.Workbook;", "");
		importMap.put(n+"import org.apache.poi.ss.usermodel.Sheet;", "");
		importMap.put(n+"import org.apache.poi.ss.usermodel.Row;", "");
		importMap.put(n+"import org.apache.poi.ss.usermodel.Cell;", "");
		importMap.put(n+"import org.apache.poi.hssf.usermodel.HSSFWorkbook;", "");
		importMap.put(n+"import java.io.InputStream;", "");
		importMap.put(n+"import com.hzsh.util.MyException;", "");
		importMap.put(n+"import "+packagePath+".entity."+entityName+";", "");
		String s = n+
				"	/**\r\n" + 
				"	 * 根据Excel，批量导入 "+moduleName+" 记录\r\n" + 
				"	 * \r\n" + 
				"	 * @param fileName\r\n" + 
				"	 * @param file\r\n" + 
				"	 * @return\r\n" + 
				"	 * @throws Exception\r\n" + 
				"	 */\r\n" + 
				"	@Override\r\n" + 
				"	public String importExcel(String fileName, MultipartFile file) throws Exception {\r\n" + 
				"		if (!fileName.matches(\"^.+\\\\.(?i)(xls)$\") && !fileName.matches(\"^.+\\\\.(?i)(xlsx)$\")) {\r\n" + 
				"			throw new MyException(\"上传文件格式不正确\");\r\n" + 
				"		}\r\n" + 
				"		boolean isExcel2003 = true;\r\n" + 
				"		if (fileName.matches(\"^.+\\\\.(?i)(xlsx)$\")) {\r\n" + 
				"			isExcel2003 = false;\r\n" + 
				"		}\r\n" + 
				"		InputStream is = file.getInputStream();\r\n" + 
				"		Workbook wb = null;\r\n" + 
				"		if (isExcel2003) {\r\n" + 
				"			wb = new HSSFWorkbook(is);\r\n" + 
				"		} else {\r\n" + 
				"			wb = new XSSFWorkbook(is);\r\n" + 
				"		}\r\n" + 
				"		Sheet sheet = wb.getSheetAt(0);\r\n" + 
				"		if (sheet == null) {\r\n" + 
				"			return \"文件为空\";\r\n" + 
				"		}\r\n" + 
				"		int lastRowNum = sheet.getLastRowNum();// 读取最后一条数据的下标（没有数据的那一行）\r\n" + 
				"		"+entityName+" "+entityLowerName+" = new "+entityName+"();\r\n" + 
				"		int updateNum = 0;\r\n" + 
				"		int insertNum = 0;\r\n" + 
				"		int errorNum = 0;\r\n" + 
				"		StringBuffer sBuffer = new StringBuffer();\r\n" + 
				"		sBuffer.append(\"\\n\" + \"详情：\");\r\n" + 
				"		for (int rowNum = 1; rowNum <= lastRowNum; rowNum++) {\r\n" + 
				"			try {\r\n" + 
				"				Row row = sheet.getRow(rowNum);\r\n" + 
				"				if (row == null) {\r\n" + 
				"					continue;\r\n" + 
				"				}\r\n" + 
				"				try {\r\n" ; 
			for(int i=0;i<colList.size();i++) {//遍历字段，生成实体类对应属性的赋值语句
				Column column = colList.get(i);
				importMap.put(column.getColumnTypeHelper().getImportStr(), "");
				if(column.isPkAuto()) {//若为主键自增，则无需操作
					continue;
				}
				String javaType = column.getJavaType();
				String fieldUpperFisrt = column.getEntityFieldUpperFisrt();
				String field = column.getEntityField();
				s += 
				"					String "+field+" = getRowString(row.getCell("+i+"));// 获得第i列\r\n" + 
				"					if ("+field+" != null & !"+field+".trim().equals(\"\")) {// 不 为空\r\n" + 
				"							// 转换类型\r\n";
				if(javaType.equals("String")) {
					s += "							String colvalue = new String("+field+");\r\n";
				}
				if(javaType.equals("BigDecimal")) {
					s += "							BigDecimal colvalue = new BigDecimal("+field+");\r\n";
				}
				if(javaType.equals("Date")) {
					importMap.put(n+"import java.text.SimpleDateFormat;", "");
					s += "							Date colvalue = new SimpleDateFormat(\""+dateFormatPartten+"\").parse("+field+");\r\n";
				}
				s += 
				"							// 给字段赋值\r\n" + 
				"							"+entityLowerName+".set"+fieldUpperFisrt+"(colvalue);\r\n" +
				"						}\r\n"; 
			}	
			s +=
				"				} catch (Exception e) {\r\n" + 
				"					e.printStackTrace();\r\n" + 
				"				}"+
				"				// 查询数据库是否有此编码的记录\r\n" + 
				"				boolean exist;\r\n" + 
				"				List<"+entityName+"> result = "+entityLowerName+"Mapper.getListByEntity("+entityLowerName+");\r\n" + 
				"				if (result.isEmpty()) {\r\n" + 
				"					exist = false;\r\n" + 
				"				} else {\r\n" + 
				"					exist = true;\r\n" + 
				"					"+entityLowerName+".set"+primary_col.getEntityFieldUpperFisrt()+"(result.get(0).get"+primary_col.getEntityFieldUpperFisrt()+"());\r\n" + 
				"				}\r\n" + 
				"				if (exist) {// 若有此编码的记录,则更新\r\n" + 
				"					"+entityLowerName+"Mapper.updateByPrimaryKeySelective("+entityLowerName+");\r\n" + 
				"					updateNum += 1;\r\n" + 
				"					sBuffer.append(\"\\n更新第\" + rowNum + \"行，\" + "+entityLowerName+".get"+primary_col.getEntityFieldUpperFisrt()+"());\r\n" + 
				"				} else { // 若无此编码的记录,则新增\r\n";
			if(primary_col.isPkAuto()) {//如果是自增
				s+=
				"					"+entityLowerName+".set"+primary_col.getEntityFieldUpperFisrt()+"(\"AUTOCREATE\");//\r\n" +
				"					"+entityLowerName+"Mapper.insertSelective("+entityLowerName+");\r\n" ;
			}else {
				s+=
				"					"+entityLowerName+"Mapper.insertSelective("+entityLowerName+");\r\n" ;
			}
			s+=	
				"					insertNum += 1;\r\n" + 
				"					sBuffer.append(\"\\n新增第\" + rowNum + \"行，\" + "+entityLowerName+".get"+primary_col.getEntityFieldUpperFisrt()+"());\r\n" + 
				"				}\r\n" + 
				"			} catch (Exception e) {\r\n" + 
				"				e.printStackTrace();\r\n" + 
				"				errorNum++;\r\n" + 
				"				sBuffer.append(\"\\n第\" + rowNum + \"行操作失败，\" + "+entityLowerName+".get"+primary_col.getEntityFieldUpperFisrt()+"() + \"，失败原因：\" + e.getMessage());\r\n" + 
				"			}\r\n" + 
				"		}\r\n" + 
				"		String result = \"总\" + (updateNum + insertNum) + \"行记录，插入\" + insertNum + \"行，更新\" + updateNum + \"行，失败\" + errorNum\r\n" + 
				"				+ \"行\";\r\n" + 
				"		result = result + sBuffer.toString();\r\n" + 
				"		return result;\r\n" + 
				"	}";
			s += getRowString();//加入此方法调用的辅助方法
		
		return s;
	}
	
	public String getRowString() {
		importMap.put(n+"import org.apache.poi.ss.usermodel.Cell;", "");
		importMap.put(n+"import org.apache.poi.ss.usermodel.CellType;", "");
		String s = n+
				"	/**\r\n" + 
				"	 * 辅助excel文件操作 从cell中取出String类型数据\r\n" + 
				"	 * \r\n" + 
				"	 * @param cell\r\n" + 
				"	 * @return\r\n" + 
				"	 */\r\n" + 
				"	String getRowString(Cell cell) {\r\n" + 
				"		if (cell != null) {\r\n" + 
				"			cell.setCellType(CellType.STRING);\r\n" + 
				"			String rowString = null;\r\n" + 
				"			rowString = cell.getStringCellValue();\r\n" + 
				"			if (rowString != null && \"\".equals(rowString.trim()))\r\n" + 
				"				return null;\r\n" + 
				"			else\r\n" + 
				"				return rowString;\r\n" + 
				"		} else {\r\n" + 
				"			return null;\r\n" + 
				"		}\r\n" + 
				"	}";
		return s;
	}
	
}
