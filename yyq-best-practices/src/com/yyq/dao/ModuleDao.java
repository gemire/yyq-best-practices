package com.yyq.dao;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import com.yyq.jstl.Page;
import com.yyq.orm.Condition;
import com.yyq.orm.Term;
import com.yyq.util.StringUtil;

public class ModuleDao extends AbstractDao {
	@Resource(name = "sessionFactory")
	// 在各个子类中注入sessionFactory的值
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	public <T> List<T> findAll(Class<T> c){
		StringBuffer table_sb=new StringBuffer();
		StringBuffer where_sb=new StringBuffer();
		String propertyName;
		String[] joinInfo;
		List<T> list=null;
		try{
			Class propertyClass;
			String className;
			for (PropertyDescriptor outter : PropertyUtils.getPropertyDescriptors(c)) {
				propertyClass=outter.getPropertyType();
				className=StringUtil.lowerFirst(propertyClass.getSimpleName());
				if(Class.class==propertyClass) continue;
				System.out.println("--"+outter.getPropertyType().getName());
				if(table_sb.length()!=0) table_sb.append(",");
				table_sb.append(propertyClass.getName());
				table_sb.append(" ");
				table_sb.append(className);
				for(PropertyDescriptor inner : PropertyUtils.getPropertyDescriptors(propertyClass)){
					propertyName=inner.getName();
					if("class".equals(propertyName)) continue;
					System.out.println(propertyName);
					joinInfo=propertyName.split("__");
					if(joinInfo.length==2){
						System.out.println(1);
						for(PropertyDescriptor temp : PropertyUtils.getPropertyDescriptors(c)){
							if(temp.getName().equals(joinInfo[0])){
								if(where_sb.length()!=0) where_sb.append(",");
								where_sb.append(joinInfo[0]);
								where_sb.append(".");
								where_sb.append(joinInfo[1]);
								where_sb.append("=");
								where_sb.append(className);
								where_sb.append(".");
								where_sb.append(propertyName);
								break;
							}
						}
					}
				}
			}
			System.out.println("from "+table_sb.toString()+" where "+where_sb);
			
			List<Object[]> objList=getHibernateTemplate().find("from "+table_sb.toString()+" where "+where_sb);
			list=new ArrayList<T>();
			for(Object[] objs:objList){
				T o=c.newInstance();
				for(Object obj:objs){
//					System.out.println(obj.getClass().getSimpleName());
					PropertyUtils.setProperty(o, StringUtil.lowerFirst(obj.getClass().getSimpleName()), obj);
				}
				list.add(o);
			}
			
//			System.out.println(list.size());
//			for(Insu_User i:list){
//				System.out.println(i.getInsu().getId()+","+i.getUser().getUsername());
//			}
//			String name;
//			String value_new;
//			for (Map.Entry<String,String> m : map_new.entrySet()) {
//				name=m.getKey();
//				value_new=m.getValue();
//				System.out.println(name+","+value_new);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public <T> List<T> findAll(T t){
		StringBuffer table_sb=new StringBuffer();
		StringBuffer where_sb=new StringBuffer();
		StringBuffer condition_sb=new StringBuffer();
		String propertyName;
		String[] joinInfo;
		List<T> list=null;
		try{
			Class propertyClass;
			String className;
			Object outterObj;
			String innerObj;
			for (PropertyDescriptor outter : PropertyUtils.getPropertyDescriptors(t)) {
				propertyClass=outter.getPropertyType();
				className=StringUtil.lowerFirst(propertyClass.getSimpleName());
				if(Class.class==propertyClass) continue;
				outterObj=PropertyUtils.getProperty(t, className);
//				System.out.println("--"+outter.getPropertyType().getName());
				if(table_sb.length()!=0) table_sb.append(",");
				table_sb.append(propertyClass.getName());
				table_sb.append(" ");
				table_sb.append(className);
				for(PropertyDescriptor inner : PropertyUtils.getPropertyDescriptors(propertyClass)){
					propertyName=inner.getName();
					if("class".equals(propertyName)) continue;
					if(outterObj!=null){
						innerObj=BeanUtils.getProperty(outterObj, propertyName);
						System.out.println("innerObj="+innerObj);
						if(innerObj.length()!=0){
							System.out.println(innerObj);
							condition_sb.append(className);
							condition_sb.append(".");
							condition_sb.append(propertyName);
							condition_sb.append("=");
							condition_sb.append("'");
							condition_sb.append(innerObj);
							condition_sb.append("'");
							condition_sb.append(" and ");
						}
					}
//					System.out.println(propertyName);
					joinInfo=propertyName.split("__");
					if(joinInfo.length==2){
//						System.out.println(1);
						for(PropertyDescriptor temp : PropertyUtils.getPropertyDescriptors(t)){
							if(temp.getName().equals(joinInfo[0])){
								if(where_sb.length()!=0) where_sb.append(" and ");
								where_sb.append(joinInfo[0]);
								where_sb.append(".");
								where_sb.append(joinInfo[1]);
								where_sb.append("=");
								where_sb.append(className);
								where_sb.append(".");
								where_sb.append(propertyName);
								break;
							}
						}
					}
				}
			}
			System.out.println("from "+table_sb.toString()+" where "+condition_sb.toString()+where_sb);
			
			List<Object[]> objList=getHibernateTemplate().find("from "+table_sb.toString()+" where "+condition_sb.toString()+where_sb);
			list=new ArrayList<T>();
			for(Object[] objs:objList){
				T o=(T)t.getClass().newInstance();
				for(Object obj:objs){
//					System.out.println(obj.getClass().getSimpleName());
					PropertyUtils.setProperty(o, StringUtil.lowerFirst(obj.getClass().getSimpleName()), obj);
				}
				list.add(o);
			}
			
//			System.out.println(list.size());
//			for(Insu_User i:list){
//				System.out.println(i.getInsu().getId()+","+i.getUser().getUsername());
//			}
//			String name;
//			String value_new;
//			for (Map.Entry<String,String> m : map_new.entrySet()) {
//				name=m.getKey();
//				value_new=m.getValue();
//				System.out.println(name+","+value_new);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return list;
	}
	
	public <T> Page<T> joinpaging(T t,Page<T> page,Term... terms){
		StringBuffer table_sb=new StringBuffer();
		StringBuffer where_sb=new StringBuffer();
		StringBuffer condition_sb=new StringBuffer();
		StringBuffer term_sb=new StringBuffer();
		List<String> without_conditionList=new ArrayList<String>();
		
		String propertyName;
		String[] joinInfo;
		List<T> list=null;
		String field;
//		String[] fields;
		try{
			for(Term te:terms){
				field=te.getField();
//				fields=field.split("[.]");
//				System.out.println(fields.length);
//				if(fields.length==2){
//					try{
//						PropertyUtils.setProperty(PropertyUtils.getProperty(t, fields[0]), fields[1], null);
//					}catch(Exception e){
//						System.out.println("清空字段出错！");
//					}
//				}
				without_conditionList.add(field);
				term_sb.append(te.toHql());
			}
			
			Class propertyClass;
			String className;
			Object outterObj;
			String innerObj;
			for (PropertyDescriptor outter : PropertyUtils.getPropertyDescriptors(t)) {
				propertyClass=outter.getPropertyType();
				className=StringUtil.lowerFirst(propertyClass.getSimpleName());
				if(Class.class==propertyClass) continue;
				outterObj=PropertyUtils.getProperty(t, className);
//				System.out.println("--"+outter.getPropertyType().getName());
				if(table_sb.length()!=0) table_sb.append(",");
				table_sb.append(propertyClass.getName());
				table_sb.append(" ");
				table_sb.append(className);
				for(PropertyDescriptor inner : PropertyUtils.getPropertyDescriptors(propertyClass)){
					propertyName=inner.getName();
					if("class".equals(propertyName)) continue;
					if(without_conditionList.contains(className+"."+propertyName)) continue;
					if(outterObj!=null){
						innerObj=BeanUtils.getProperty(outterObj, propertyName);
						if(innerObj!=null&&innerObj.length()!=0){
//							System.out.println(innerObj);
							condition_sb.append(className);
							condition_sb.append(".");
							condition_sb.append(propertyName);
							condition_sb.append("=");
							condition_sb.append("'");
							condition_sb.append(innerObj);
							condition_sb.append("'");
							condition_sb.append(" and ");
						}
					}
//					System.out.println(propertyName);
					joinInfo=propertyName.split("__");
					if(joinInfo.length==2){
//						System.out.println(1);
						for(PropertyDescriptor temp : PropertyUtils.getPropertyDescriptors(t)){
							if(temp.getName().equals(joinInfo[0])){
								if(where_sb.length()!=0) where_sb.append(" and ");
								where_sb.append(joinInfo[0]);
								where_sb.append(".");
								where_sb.append(joinInfo[1]);
								where_sb.append("=");
								where_sb.append(className);
								where_sb.append(".");
								where_sb.append(propertyName);
								break;
							}
						}
					}
				}
			}
			String hql="from "+table_sb.toString()+" where "+condition_sb.toString()+where_sb.toString()+term_sb.toString();
//			System.out.println(hql);
			
		//查询记录总数
			long totalRows=(Long)getHibernateTemplate().find("select count(*) "+hql).get(0);
			
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
			
			//校验当前页，当当前页已经超过总记录数时，重置当前页
			if((page.getPage()-1)*page.getPageSize()>totalRows) page.setPage(1);
			
			//设置好分页的相关参数
			page.setTotalRows(totalRows);
			if(page.getNeedPaging()){
				query.setFirstResult((page.getPage()-1)*page.getPageSize());
				query.setMaxResults(page.getPageSize());
			}
			List<Object[]> objList=query.list();
			page.setTotalPages((totalRows-1)/page.getPageSize()+1);
			
			session.close();//显式关闭session
			
			list=new ArrayList<T>();
			for(Object[] objs:objList){
				T o=(T)t.getClass().newInstance();
				for(Object obj:objs){
//					System.out.println(obj.getClass().getSimpleName());
					PropertyUtils.setProperty(o, StringUtil.lowerFirst(obj.getClass().getSimpleName()), obj);
				}
				list.add(o);
			}
			
			page.setList(list);
			
//			System.out.println(list.size());
//			for(Insu_User i:list){
//				System.out.println(i.getInsu().getId()+","+i.getUser().getUsername());
//			}
//			String name;
//			String value_new;
//			for (Map.Entry<String,String> m : map_new.entrySet()) {
//				name=m.getKey();
//				value_new=m.getValue();
//				System.out.println(name+","+value_new);
//			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return page;
	}
	
	/**
	 * 表与表分页关联查询
	 * @param page
	 * @param condition
	 * @return
	 */
	protected Page pagingJoin(Page page,Condition condition){
		String hql=condition.tohql();
//		System.out.println("hql="+hql);
		String[] s=condition.getArgs();
//		for(String ss:s){
//			System.out.println(ss);
//		}
		return paging(hql,page,s);
	}
}
