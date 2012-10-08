package com.yyq.jstl;

import java.util.List;

import lombok.Data;

/**
 * 分页参数封装类
 * @author YYQ
 * @param <T>
 */
public @Data class Page<T> {
	private long totalRows;//记录总数
	private int pageSize=3;//设置一页显示的记录数
	private int page=1;//当前页码
	private long totalPages;//总页数
	private boolean needPaging=true;//是否需要分页
	public boolean getNeedPaging(){
		return needPaging;
	}
	private boolean isAsc;//是否是按升序排列
	public boolean getIsAsc(){
		return isAsc;
	}
	public String orderBy;//排序关键字
	private List<T> list;//记录列表
}
