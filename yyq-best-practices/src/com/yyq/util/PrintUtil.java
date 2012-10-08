package com.yyq.util;

import java.beans.PropertyDescriptor;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 打印工具类
 * @author YYQ
 */
public class PrintUtil {
	public static Log log=LogFactory.getLog(PrintUtil.class);
	/**
	 * 打印出一个实例的所有属性的名-值对
	 */
	public static void printlnAllProperties(Object obj){
		String propertyName;
		String propertyValue=null;
		PropertyDescriptor[] propertyDescriptors=PropertyUtils.getPropertyDescriptors(obj);
		for(int i=0;i<propertyDescriptors.length;i++){
			PropertyDescriptor pd=propertyDescriptors[i];
			propertyName=pd.getName();
			try {
				propertyValue=BeanUtils.getProperty(obj, propertyName);
			} catch (Exception e) {
				e.printStackTrace();
			}
			log.info("\""+propertyName+"\"属性的值是："+propertyValue);
		}
	}
}
