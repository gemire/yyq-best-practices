package com.yyq.jstl;

/**
 * JSTL自定义标签实现类
 * @author YYQ
 */
public class MyJstl {
	private static String SPACE="&nbsp;";
	
	/**
	 * 自定义分页标签
	 * 当需要分页的form不是第一个时，需要设置id为：pagingForm
	 * @param action
	 * @param page
	 * @return
	 */
	public static String page(String action,Page page){
		int nowPage=page.getPage();
		long sumPage=page.getTotalPages();
		int start=nowPage-2;
		if(start<1) start=1;
		long end=nowPage+2;
		if(end>sumPage) end=sumPage;
		StringBuffer sb=new StringBuffer();
		sb.append("<script type='text/javascript'>");
		sb.append("var pagingForm=document.getElementById('pagingForm');");
		sb.append("if(pagingForm==null) pagingForm=document.forms[0];");
		sb.append("</script>");
		
		//添加分页参数if(page.getOrderBy()!=null) 
		sb.append("<input id='orderBy' name='page.orderBy' type='hidden' value='"+page.getOrderBy()+"'>");
		sb.append("<input id='isAsc' name='page.isAsc' type='hidden' value='"+page.getIsAsc()+"'>");
		sb.append("<input id='pageNum' name='page.page' type='hidden' value='"+page.getPage()+"'>");
		
		if(nowPage<=1){
			sb.append("首页");
			sb.append(SPACE);
			sb.append("上一页");
			sb.append(SPACE);
		}else{
			sb.append("<a href='#' onclick=\"pageNum.value='1';forms[0].submit();\">首页</a>");
			sb.append(SPACE);
			sb.append("<a href='#' onclick=\"pageNum.value='");
			sb.append(nowPage-1);
			sb.append("';forms[0].submit();\">上一页</a>");
			sb.append(SPACE);
		}
		
		for(int i=start;i<=end;i++){
			if(i==nowPage){
				sb.append(i);
				sb.append(SPACE);
			}else{
				sb.append("<a href='#' onclick=\"pageNum.value='");
				sb.append(i);
				sb.append("';forms[0].submit();\">[");
				sb.append(i);
				sb.append("]</a>");
				sb.append(SPACE);
			}
		}
		
		if(nowPage>=sumPage){
			sb.append("下一页");
			sb.append(SPACE);
			sb.append("末页");
			sb.append(SPACE);
		}else{
			sb.append("<a href='#' onclick=\"pageNum.value='");
			sb.append(nowPage+1);
			sb.append("';forms[0].submit();\">下一页</a>");
			sb.append(SPACE);
			sb.append("<a href='#' onclick=\"pageNum.value='");
			sb.append(sumPage);
			sb.append("';forms[0].submit();\">末页</a>");
			
			sb.append(SPACE);
		}
		
		return sb.toString();
	}
	
	/**
	 * 使用JSTL自定义函数生成可排序的表头
	 * 当需要分页的form不是第一个时，需要设置id为：pagingForm
	 * @param title2
	 * @param key2
	 * @param page
	 * @return
	 */
	public static String orderByTitle(String title2,String key2,Page page){
		StringBuffer sb=new StringBuffer();
		sb.append("<script type='text/javascript'>");
		sb.append("var pagingForm=document.getElementById('pagingForm');");
		sb.append("if(pagingForm==null) pagingForm=document.forms[0];");
		sb.append("</script>");
		sb.append("<thead><tr>");
		
		String[] title1=title2.split(",");
		String[] key1=key2.split(",");
		
		boolean flag=title1.length==key1.length?true:false;
		String title;
		String key=null;
		for(int i=0;i<title1.length;i++){
			title=title1[i];
			if(i<key1.length) key=key1[i]; else key=null;
			sb.append("<th>");
			if(flag&&key!=null&&key.trim().length()!=0){
				if(key.equals(page.getOrderBy())){ // 正序排序与反序排序之间进行切换
					sb.append("<a href='#' onclick=\"isAsc.value='"+!page.getIsAsc()+"';orderBy.value='");
				}else{
					sb.append("<a href='#' onclick=\"orderBy.value='");
				}
				sb.append(key);
				sb.append("';pageNum.value='1';forms[0].submit();\">");
				sb.append(title);
				sb.append("</a>");
			}else{
				sb.append(title);
			}
			sb.append("</th>");
		}
		sb.append("</tr></thead>");
		return sb.toString();
	}
	
	/**
	 * 用自定义JSTL函数实现透明遮盖层，依赖jquery
	 * @param div_t
	 * @param div_b
	 * @return
	 */
	public static String coverDiv(String div_t,String div_b){
		StringBuffer sb=new StringBuffer();
		sb.append("<div id='");
		sb.append(div_t);
		sb.append("' style='z-index: 1000;position: absolute;filter:alpha(opacity=0);background: green;'></div>");
		sb.append("<script type='text/javascript'>$().ready(function(){");
		sb.append("cover('");
		sb.append(div_t);
		sb.append("','");
		sb.append(div_b);
		sb.append("');");
		sb.append("});</script>");
	    
	    return sb.toString();
	}
}
