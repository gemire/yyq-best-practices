package com.yyq.dao;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.yyq.jstl.Page;

public abstract class AbstractDao<T extends IdEntity> extends HibernateDaoSupport {
	/**
	 * 分页的主方法，将通用的分页方法抽象至基类Dao
	 * @param hql
	 * @param page
	 * @param args
	 * @return
	 */
	protected Page<T> paging(String hql,Page<T> page,Object[] args){
		//查询记录总数
		long totalRows=(Long)getHibernateTemplate().find("select count(*) "+hql,args).get(0);
		
		/**
		Query query=getSession().createQuery("select count(*) "+sql);
		if(args!=null){
			Object obj;
			for(int i=0;i<args.length;i++){
				obj=args[i];
				query.setString(i,String.valueOf(obj));
			}
		}
		
		long totalRows=0;
		try{
			totalRows=(Long)query.uniqueResult();
		}catch(NullPointerException e){
			log.info("分页查询记录总数时结果集为空。");
		}catch(NonUniqueResultException e){
			log.info("无法进行分页。");
		}
		*/
		
		if(page==null) page=new Page();
		
		//按关键字排序
		if(!StringUtils.isBlank(page.getOrderBy())){
			hql=hql+" order by "+page.getOrderBy();
		}
		
		//正序还是反序
		if(hql.indexOf(" order by ")!=-1){
		if(page.getIsAsc()==true){
			hql=hql+" asc";
		}else{
			hql=hql+" desc";
		}
		}
		
		Session session=super.getSession();
		Query query=session.createQuery(hql);
		if(args!=null){
			Object obj;
			for(int i=0;i<args.length;i++){
				obj=args[i];
				query.setString(i,String.valueOf(obj));
			}
		}
		
		//校验当前页，当当前页已经超过总记录数时，重置当前页
		if((page.getPage()-1)*page.getPageSize()>totalRows) page.setPage(1);
		
		//设置好分页的相关参数
		page.setTotalRows(totalRows);
		if(page.getNeedPaging()){
			query.setFirstResult((page.getPage()-1)*page.getPageSize());
			query.setMaxResults(page.getPageSize());
		}
		page.setList(query.list());
		page.setTotalPages((totalRows-1)/page.getPageSize()+1);
		
		session.close();//显式关闭session
		
		return page;
	}
}
