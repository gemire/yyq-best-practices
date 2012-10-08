package com.yyq.util;

import org.springframework.context.ApplicationContext;

/**
 * 原先用来保存Spring的ApplicationContext，现在已经被弃用
 * @author YYQ
 */
public class SpringUtil {
	private static ApplicationContext context;
	public static ApplicationContext getContext(){
		return context;
	}
	public static void setContext(ApplicationContext context){
		SpringUtil.context = context;
	}
	/**
	 * 获得由Spring管理的Bean
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}
}
