package com.yyq.orm;

import org.apache.commons.lang.StringUtils;

public class Like extends Term{
	private String value;
	public Like(String field,String value){
		this.field=field;
		this.value=value;
	}
	@Override
	public String toHql() {
		if(StringUtils.isBlank(value)) return "";
		StringBuffer sb=new StringBuffer();
		sb.append(" and ");
		sb.append(field);
		sb.append(" like '%");
		sb.append(value);
		sb.append("%'");
		return sb.toString();
	}

}
