package com.yyq.util;

import org.springframework.security.core.context.SecurityContextHolder;

import com.yyq.security.MyUserDetails;

/**
 * Spring Security工具类
 * @author YYQ
 */
public class SSUtil {
	/**
	 * 获取spring security中的用户名
	 * @return
	 */
	public static String getName(){
		return SecurityContextHolder.getContext().getAuthentication().getName();
	}
	
	/**
	 * 获得自定义用户认证信息
	 * @return MyUserDetails
	 */
	public static MyUserDetails getUserDetails(){
		return (MyUserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
	}
}
