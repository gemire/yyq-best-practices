package com.yyq.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

/**
 * Excel文件读取工具类
 * @author YYQ
 */
public class ExcelUtil {
	/**
	 * 读出字符串类型的单元格内容
	 * @param row
	 * @param cellnum
	 * @return String
	 */
	public static String getString(HSSFRow row,int cellnum){
		HSSFCell cell=row.getCell(cellnum);
		if(cell==null) return null;
		String str=cell.toString();
		str=str.replaceAll("[.]0", "");//数值单元格都带有“.0”
		return str;
	}
	
	/**
	 * 载入一个Excel文件
	 * @param excelFile
	 * @return HSSFWorkbook
	 */
	public static HSSFWorkbook loadExcel(File excelFile){
		InputStream inputStream=null;
		HSSFWorkbook workbook=null;
		try {
			inputStream = new FileInputStream(excelFile);
			workbook = new HSSFWorkbook(inputStream);
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally{
			try{
			inputStream.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		return workbook;
	}
}
