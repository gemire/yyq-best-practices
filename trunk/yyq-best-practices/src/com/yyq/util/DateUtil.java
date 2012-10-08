package com.yyq.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期工具类
 * @author YYQ
 */
public class DateUtil {
	/**
	 * 获取当前时间，格式：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String nowTime(){
		String nowTime=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(Calendar.getInstance().getTime());
		return nowTime;
	}
	/**
	 * 获取当前日期，格式：yyyy-MM-dd
	 * @return
	 */
	public static String getDate(){
		String date=new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
		return date;
	}
	
	/**
	 * 此方法现在未使用
	 * @return
	 */
	public static String dateForJavaScript(){
		String nowTime=new SimpleDateFormat("yyyy,MM,dd,HH,mm,ss").format(Calendar.getInstance().getTime());
		return nowTime;
	}
}
