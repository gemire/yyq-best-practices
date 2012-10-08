package com.yyq.util;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;

/**
 * POI工具类
 * @author YYQ
 */
public class POIUtil {
	/**
	 * 为一行的每个单元格设置单元格风格
	 * @param row
	 * @param cellStyle
	 */
	public static void setRowEveryCellStyle(HSSFRow row,HSSFCellStyle cellStyle){
		int n=row.getLastCellNum();
		for(int i=0;i<n;i++){
			row.getCell(i).setCellStyle(cellStyle);
		}
	}
}
