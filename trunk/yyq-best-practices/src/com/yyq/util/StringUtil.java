package com.yyq.util;

import java.io.UnsupportedEncodingException;

import org.apache.commons.lang.StringUtils;

/**
 * 字符处理工具类
 * @author YYQ
 */
public class StringUtil {
	/**
	 * 将ISO-8859-1编码转换为UTF-8编码
	 * @param str
	 * @return
	 */
	public static String ISO_UTF8(String str){
		try {
			str=new String(str.getBytes("ISO-8859-1"),"UTF-8");
		} catch (UnsupportedEncodingException e) {
			System.out.println("StringUtil字符编码转换出错！");
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 清除字符串中的回车键
	 * @param str
	 * @return
	 */
	public static String clearEnter(String str){
		return str.replaceAll("\t|\r|\n", "");
	}
	/**
	 * 判断字符串相等
	 * @param str1
	 * @param str2
	 * @return
	 */
	public static boolean isEquals(String str1,String str2){
		if(StringUtils.isBlank(str1)&&StringUtils.isBlank(str2)){
			return true;
		}else if(str1.equals(str2)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 大写首字母
	 * @param str
	 * @return
	 */
	public static String upperFirst(String str){
		return str.substring(0, 1).toUpperCase()+str.substring(1);
	}
	/**
	 * 小写首字母
	 * @param str
	 * @return
	 */
	public static String lowerFirst(String str){
		return str.substring(0, 1).toLowerCase()+str.substring(1);
	}
}
