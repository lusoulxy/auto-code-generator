package com.silas.generator.helper.fileStrHelper;

import java.util.List;

import com.silas.generator.config.ImportC;
import com.silas.generator.helper.Column;
import com.silas.generator.helper.OutPutFile;
import com.silas.generator.helper.interface_.CreateClassHelper;
import com.silas.generator.helper.interface_.Module;
import com.silas.generator.helper.interface_.ModuleImport;
import com.silas.generator.helper.interface_.ModuleMethod;
import com.silas.util.GeneratorUtil;
import com.silas.util.module.ModuleUtils;

public class ServiceImplHelper extends CreateClassHelper{
	String fileFullName = path + GeneratorUtil.getPackage(packagePath) + "service/" + entityName + "ServiceImpl.java";
	//设置模块是否生成，和生成的顺序；value=0为不生成，数字越小排越前面
	{
		//key:方法名，value：true 则生成 false 则不生成
		toCreateModule.put("classFields", 1);//importExcel 导入excel
		toCreateModule.put("save", 2);//save 保存记录，选择字段保存
		toCreateModule.put("update", 3);//update 根据主键更新记录，选择字段更新 
		toCreateModule.put("delect", 4);///delect 根据主键删除
		toCreateModule.put("findById", 5);//findById 根据主键删除
		toCreateModule.put("getListByMap", 6);// getListByMap 根据map查询list，用于分页查询
		toCreateModule.put("getTotalNumByMap", 7);//getTotalNumByMap 根据map查询总数
		toCreateModule.put("getListByEntity", 8);//getListByEntity 根据实体类查询list
		
//		toCreateModule.put("importExcel", 10);//importExcel 导入excel
		toCreateModule.put("getRowString", 11);//getRowString 辅助excel文件操作 从cell中取出String类型数据
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
	public String getOutPutContent()  throws Exception {
		ModuleImport otherImport = new ModuleImport();
		String classDesciption = "";//类描述
		//1。包名
		String packageName = "package " + packagePath + ".service;" + n2;
		//2。导入所依赖的包
		String importNeed = "";
		
		//3。开始
		String start =   n+"@Service"+
				n + "public class " + entityName + "ServiceImpl implements " + entityName + "Service {" + n;
		otherImport.putImport(ImportC.SERVICE);
		otherImport.putImport(packagePath+".mapper."+entityName+"Mapper");
		
		
		//4。方法
		List<Module> modlues = getModules();
		String methods = ModuleUtils.getModulesStr(modlues);
		modlues.add(otherImport);
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
		 * 创建类所需属性
		 */
		public ModuleMethod classFields(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
			String fullStr = n+
					"	@Autowired\r\n" + 
					"	private "+entityName+"Mapper "+entityLowerName+"Mapper;";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(ImportC.AUTOWIRED);
			method.putImport(packagePath+".mapper."+entityName+"Mapper");
			return method;
		}
		/*
		 * importExcel 导入excel
		 */
		public ModuleMethod importExcel(){
			ModuleMethod method = new ModuleMethod();
			//完整描述
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
					"			throw new Exception(\"上传文件格式不正确\");\r\n" + 
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
					method.putImport(column.getColumnTypeHelper().getImportStr());
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
						method.putImport(ImportC.SIMPLEDATAEFORMAT);
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
			method.setFullStr(s);
			//要导入的包
			method.putImport(ImportC.MULTIPARTFILE);
			method.putImport(entityPackage);
			//此方法要导入的包
			method.putImport(ImportC.LIST);
			method.putImport(ImportC.MULTIPARTFILE);
			method.putImport(ImportC.POI_XSSFWORKBOOK);
			method.putImport(ImportC.POI_WORKBOOK);
			method.putImport(ImportC.POI_SHEET);
			method.putImport(ImportC.POI_ROW);
			method.putImport(ImportC.POI_CELL);
			method.putImport(ImportC.POI_HSSFWORKBOOK);
			method.putImport(ImportC.INPUTSTREAM);
			return method;
		}
		/*
		 * getRowString 辅助excel文件操作 从cell中取出String类型数据
		 */
		public ModuleMethod getRowString(){
			ModuleMethod method = new ModuleMethod();
			String fullStr = n+
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
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(ImportC.POI_CELL);
			method.putImport(ImportC.POI_CELLTYPE);
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
					"	 */\r\n" + 
					"	@Override\r\n" + 
					"	public List<"+entityName+"> getListByEntity("+entityName+" record) {\r\n" +
					"		return "+entityLowerName+"Mapper.getListByEntity(record);\r\n" + 
					"	}" ;
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
					"	 *  根据map查询总数\r\n" + 
					"	 */\r\n" + 
					"	@Override\r\n" + 
					"	public int getTotalNumByMap(Map<String, Object> map) {\r\n" +
					"		return "+entityLowerName+"Mapper.getTotalNumByMap(map);\r\n" + 
					"	}";
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
					"	 *  根据map查询list，用于分页查询\r\n" + 
					"	 */\r\n" + 
					"	@Override\r\n" + 
					"	public List<"+entityName+"> getListByMap(Map<String, Object> map) {\r\n"+
					"		return "+entityLowerName+"Mapper.getListByMap(map);\r\n" + 
					"	}" ; 
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
					"	 *  根据主键查询\r\n" + 
					"	 */\r\n" + 
					"	@Override\r\n" + 
					"	public "+entityName+" findById("+primary_col.getJavaType()+" "+primary_col.getColumName()+") {\r\n;" +
					"		return "+entityLowerName+"Mapper.selectByPrimaryKey(id);\r\n" + 
					"	}";
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
					"	 *  根据主键删除\r\n" + 
					"	 */\r\n" + 
					"	@Override\r\n" + 
					"	public int delect("+primary_col.getJavaType()+" "+primary_col.getEntityField()+") {\r\n"+
					"		return "+entityLowerName+"Mapper.deleteByPrimaryKey(id);\r\n" + 
					"	}";
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
					"	/**\r\n" + 
					"	 *  根据主键更新记录，选择字段更新\r\n" + 
					"	 */\r\n" + 
					"	@Override\r\n" + 
					"	public int update("+entityName+" record) {\r\n" + 
					"		return "+entityLowerName+"Mapper.updateByPrimaryKeySelective(record);\r\n" + 
					"	}";
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
					"	 *  保存记录，选择字段保存\r\n" + 
					"	 */\r\n" + 
					"	@Override\r\n" + 
					"	public int save("+entityName+" record) {\r\n" + 
					"		return "+entityLowerName+"Mapper.insert(record);\r\n" + 
					"	}";
			method.setFullStr(fullStr);
			//要导入的包
			method.putImport(entityPackage);
			return method;
		}
		
	}
}
