package com.yyq.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 拼音工具类
 * @author YYQ
 */
public class PinyinUtil {
	public static String pinyin(String inputCN,String seg){
		HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
		format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		format.setVCharType(HanyuPinyinVCharType.WITH_V);
		
		StringBuffer sb=new StringBuffer();
		char c;
		for(int i=0;i<inputCN.length();i++){
			c=inputCN.charAt(i);
			try{
				for(String s:PinyinHelper.toHanyuPinyinStringArray(c, format)){
					if("sha".equals(s)) continue;
					sb.append(s);
				}
			}catch(BadHanyuPinyinOutputFormatCombination e){
				System.out.println("汉字转拼音错误！");
			}
		}
		
		return sb.toString();
	}
}
