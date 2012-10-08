package com.yyq.flex;

import com.yyq.util.SSUtil;

public class FlexJava {
	
	/**
	 * Flex通过调用此方法获得HttpSession中的值
	 * @return HttpSession中的值
	 */
	public String getSessionValue(String key){
		return SSUtil.getName();
	}
}
