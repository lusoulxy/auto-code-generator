package com.silas;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;


public class ExcelUtil {
	//根据filePath，取得fileName
	public static String getFileName(String filePath) {
		String fileName = null;
		String [] filePathArr = filePath.split("/");
		if(filePathArr.length>0)
			fileName = filePathArr[filePathArr.length-1];
		return fileName;
	}
	
	//读取excel文件
	public static Workbook getExcleFile(String filePath) {
		Workbook wb = null;
		try {
			String fileName =filePath.split("")[0];
			if (!fileName.matches("^.+\\.(?i)(xls)$") && !fileName.matches("^.+\\.(?i)(xlsx)$")) {
		          throw new Exception("上传文件格式不正确");
		       }
			boolean isExcel2003 = true;
			if(fileName.matches("^.+\\.(?i)(xlsx)$")) {//为2007以后版本
				isExcel2003 = false;
			}
			InputStream inputStream = new FileInputStream(new File(filePath)) ;
		       if (isExcel2003) {
		          wb = new HSSFWorkbook(inputStream);//03版
		       } else {
		          wb = new XSSFWorkbook(inputStream);//07版
		       }
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return wb;
	}
	
	  /**
     * 查询指定目录中电子表格中所有的数据
     * @param file 文件完整路径
     * @return
     */
   /* public static List<StuEntity> getAllByExcel(String file){
        List<StuEntity> list=new ArrayList<StuEntity>();
        try {
            Workbook rwb=Workbook.getWorkbook(new File(file));
            Sheet rs=rwb.getSheet("Test Shee 1");//或者rwb.getSheet(0)
            int clos=rs.getColumns();//得到所有的列
            int rows=rs.getRows();//得到所有的行
            
            System.out.println(clos+" rows:"+rows);
            for (int i = 1; i < rows; i++) {
                for (int j = 0; j < clos; j++) {
                    //第一个是列数，第二个是行数
                    String id=rs.getCell(j++, i).getContents();//默认最左边编号也算一列 所以这里得j++
                    String name=rs.getCell(j++, i).getContents();
                    String sex=rs.getCell(j++, i).getContents();
                    String num=rs.getCell(j++, i).getContents();
                    
                    System.out.println("id:"+id+" name:"+name+" sex:"+sex+" num:"+num);
                    list.add(new StuEntity(Integer.parseInt(id), name, sex, Integer.parseInt(num)));
                }
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } 
        return list;
        
    }*/

}
