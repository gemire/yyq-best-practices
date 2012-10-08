package com.yyq.orm;

public abstract class Term {
	public abstract String toHql();
	protected String field;
	public String getField(){
		return field;
	}
}
