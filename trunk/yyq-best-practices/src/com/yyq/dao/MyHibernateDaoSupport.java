package com.yyq.dao;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import lombok.Getter;
import lombok.Setter;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.yyq.jstl.Page;
import com.yyq.orm.Condition;
import com.yyq.orm.FK;
import com.yyq.orm.JoinInfo;

/**
 * 基于Spring HibernateDaoSupport的泛型基类Dao
 * @author YYQ
 * @param <T>
 */
public class MyHibernateDaoSupport<T extends IdEntity> extends AbstractDao {
	@Autowired@Getter@Setter
	protected JdbcTemplate jdbcTemplate;

	@Resource(name = "sessionFactory")
	// 在各个子类中注入sessionFactory的值
	public void setSuperSessionFactory(SessionFactory sessionFactory) {
		super.setSessionFactory(sessionFactory);
	}
	
	@Autowired@Getter@Setter
	private JoinInfo joinInfo;
	
	private Class<T> entityClass;
	private String entityClassName;
	
	protected final Logger log = Logger.getLogger(this.getClass());

	public MyHibernateDaoSupport() {
		//核心代码，获取T的具体类型
		entityClass = (Class<T>)((ParameterizedType)getClass().getGenericSuperclass()).getActualTypeArguments()[0];
		entityClassName = entityClass.getName();
	}

	/**
	 * 保存
	 * @param entity
	 * @return
	 */
	public Serializable save(T entity) {
		try {
			return getHibernateTemplate().save(entity);
		} catch (RuntimeException e) {
			log.error("插入记录出错，实体类：" + entityClassName, e);
			throw e;
		}
	}

	/**
	 * 保存和更新
	 * @param entity
	 */
	public void saveOrUpdate(T entity) {
		//当主键是32位UUID时，如果主键是空字符串，则将主键设为null
		String id=entity.getId();
		if(id!=null&&id.length()==0) entity.setId(null);
		
		try {
			getHibernateTemplate().saveOrUpdate(entity);
		} catch (RuntimeException e) {
			log.error("保存记录出错，实体类：" + entityClassName, e);
			throw e;
		}
	}
	
	/**
	 * 智能地将实体类中有更改的属性值更新进数据库
	 * 要求实体类的主键必须是id
	 * @param entity_new
	 */
	public void updateSmart(T entity_new) {
		Map<String, String> map_new=null;
		Map<String, String> map_old=null;
		T entity_old;
		boolean needUpdate=false;//是否需要更新
		try {
			map_new = BeanUtils.describe(entity_new);
			String id=entity_new.getId();
			if(id==null) throw new RuntimeException("MyHibernateDaoSupport中updateAuto方法id为空！");
			entity_old=findById(id);
			map_old = BeanUtils.describe(entity_old);
			
			String name;
			String value_new;
			String value_old;
			for (Map.Entry<String,String> m : map_new.entrySet()) {
				name=m.getKey();
				value_new=m.getValue();
				if(value_new!=null&&!"class".equals(value_new)&&!"id".equals(value_new)){
					value_old=map_old.get(name);
					if(!value_new.equals(value_old)){//属性值与数据库里的值不一样
						BeanUtils.setProperty(entity_old, name, value_new);
						needUpdate=true;
					}
				}
			}
			if(needUpdate){
				getHibernateTemplate().saveOrUpdate(entity_old);//更新数据库
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 删除
	 * @param entity
	 */
	public void delete(T entity) {
		joinDelete(entity.getId());
		
		try {
			getHibernateTemplate().delete(entity);
		} catch (RuntimeException e) {
			log.error("删除记录出错，实体类：" + entityClassName, e);
			throw e;
		}
	}

	/**
	 * 根据主键删除
	 * @param id
	 */
	public void deleteById(String id) {
		joinDelete(id);
		
		try {
			getHibernateTemplate().delete(
					getHibernateTemplate().get(entityClass, id));
		} catch (RuntimeException e) {
			log.error("根据主键删除记录出错，实体类：" + entityClassName, e);
			throw e;
		}
	}
	
	/**
	 * 在删除记录之前先去删除相关联的从表里的相关记录
	 * @param id
	 */
	private void joinDelete(String id){
		List<FK> list=joinInfo.getDeleteMap().get(entityClass.getSimpleName());
		for(FK fk:list){
			Session session=getSession();
			session.createQuery("delete from "+fk.getFclass()+" where "+fk.getColumnName()+" = '"+id+"'").executeUpdate();
			session.close();
		}
	}

	/**
	 * 根据主键查找
	 * @param id
	 * @return
	 */
	public T findById(String id) {
		try {
			return (T)getHibernateTemplate().get(entityClass, id);
		} catch (RuntimeException e) {
			log.error("根据主键查询记录出错，实体类：" + entityClassName, e);
			throw e;
		}
	}
	
	/**
	 * 根据传入对象的属性值进行查询
	 * @param entity
	 * @return
	 */
	public List<T> findByExample(T entity){
		return getHibernateTemplate().findByExample(entity);
	}
	
	/**
	 * 根据传入对象的属性值进行唯一查询
	 * @param entity
	 * @return
	 */
	public T findUniqueByExample(T entity){
		List<T> list=getHibernateTemplate().findByExample(entity);
		if(list.size()==1){
			return list.get(0);
		}else if(list.size()==0){
			return null;
		}else{
			throw new RuntimeException("查询的结果不唯一！");
		}
	}
	
	/**
	 * 按属性查找对象列表,匹配方式为相等.
	 */
	public List<T> findBy(String propertyName,Object value) {
		return getHibernateTemplate().find(" from "+entityClassName+" where "+propertyName+"=?", value);
	}

	/**
	 * 按属性查找唯一对象,匹配方式为相等.
	 */
	public T findUniqueBy(String propertyName,Object value) {
		List<T> list=getHibernateTemplate().find(" from "+entityClassName+" where "+propertyName+"=?", value);
		if(list.size()>1){
			throw new RuntimeException("查询的结果不唯一！");
		}else if(list.size()==0){
			return null;
		}
		return list.get(0);
	}
	
	/**
	 * 根据HQL语句查找
	 * @param hql
	 * @return
	 */
	public List<T> find(String hql){
		try {
			return getHibernateTemplate().find(hql);
		} catch (RuntimeException e) {
			log.error("泛型Dao中find()方法出错，实体类：" + entityClassName, e);
			throw e;
		}
	}
	
	/**
	 * 查找所有
	 * @return
	 */
	public List<T> findAll() {
		try {
			String queryString = "from " + entityClassName;
			return getHibernateTemplate().find(queryString);
		} catch (RuntimeException e) {
			log.error("查询表的全部记录时出错，实体类：" + entityClassName, e);
			throw e;
		}
	}
	
	/**
	 * 查找某个字段的所有值
	 * @param property
	 * @return
	 */
	public List<String> findPropertyValues(String property){
		try {
			Query query=getSession().createQuery("select distinct "+property+" from "+entityClassName);
			return query.list();
		} catch (RuntimeException e) {
			log.error("根据主键查询记录出错，实体类：" + entityClassName, e);
			throw e;
		}
	}
	
	public static Object getBeanFromRequest(HttpServletRequest request,String beanName){
		return WebApplicationContextUtils.getWebApplicationContext(request.getSession().getServletContext()).getBean(beanName);
	}
	
	
	
	/**
	 * 根据传入对象的属性值限定相等条件查询
	 * @param obj
	 * @param page
	 * @return
	 */
	protected Page<T> paging(Object obj,Page<T> page){
		StringBuffer sb=new StringBuffer();
		sb.append("from ");
		sb.append(entityClassName);
		Map<String, String> map=null;
		boolean needAnd=false;
		String name;
		String value;
		List<String> argList=new ArrayList<String>();
		try {
			map = BeanUtils.describe(obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (Map.Entry<String,String> m : map.entrySet()) {
			name=m.getKey();
			value=m.getValue();
			if(value!=null&&value.length()!=0&&!"class".equals(name)){
				if(needAnd==true){
					sb.append(" and ");
				}else{
					sb.append(" where ");
					needAnd=true;
				}
				sb.append(name);
				sb.append("=?");
				argList.add(value.trim());//有发现属性值后面有时跟一个空格造成查不到数据
			}
		}
		if(argList.size()!=0){
			String[] s=new String[argList.size()];
			s=argList.toArray(s);
			return paging(sb.toString(),page,s);
		}else{
			return paging(sb.toString(),page);
		}
	}
	
	/**
	 * 普通分页方法
	 * @param page
	 * @return
	 */
	public Page<T> paging(Page<T> page){
		Object[] args=null;
		if(page==null) page=new Page<T>();
		return paging("from " + entityClassName,page,args);
	}
	
	/**
	 * 无参分页方法
	 * @param hql
	 * @param page
	 * @return
	 */
	protected Page<T> paging(String hql,Page<T> page){
		Object[] args=null;
		return paging(hql,page,args);
	}
	
	
	
	/**
	 * 无参数分页方法
	 * @param hql
	 * @param page
	 * @param totalRows
	 * @return
	 */
	protected Page<T> paging(String hql,Page<T> page,int totalRows){
		Object[] obj=null;
		return paging(hql,page,obj);
	}
	
	/**
	 * 只有一个参数的分页方法
	 * @param hql
	 * @param page
	 * @param keyWord
	 * @return
	 */
	protected Page<T> paging(String hql,Page<T> page,String keyWord){
		return paging(hql,page,new Object[]{keyWord});
	}
}
