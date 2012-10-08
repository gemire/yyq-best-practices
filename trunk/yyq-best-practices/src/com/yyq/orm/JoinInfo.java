package com.yyq.orm;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;

import lombok.Getter;
import lombok.Setter;

import org.hibernate.impl.SessionFactoryImpl;
import org.hibernate.persister.entity.SingleTableEntityPersister;

import com.yyq.util.StringUtil;

public class JoinInfo {
	@Getter@Setter
	private Map<String,List<FK>> deleteMap=new HashMap<String, List<FK>>();
	public JoinInfo(SessionFactoryImpl sessionFactory) throws NamingException{
		SingleTableEntityPersister tp;
		String[] joinInfo;
		String key;
		for(Object obj:sessionFactory.getAllClassMetadata().values()){
			tp=(SingleTableEntityPersister)obj;
//			System.out.println(tp.getEntityType().getReturnedClass());
			for(String propertyName:tp.getClassMetadata().getPropertyNames()){
				joinInfo=propertyName.split("__");
//				System.out.println(propertyName);
				if(joinInfo.length==2){
					key=StringUtil.upperFirst(joinInfo[0]);
					FK fk=new FK();
					fk.setFclass(tp.getType().getName());
					fk.setColumnName(propertyName);
					List<FK> list=deleteMap.get(key);
					if(list==null){
						list=new ArrayList<FK>();
						list.add(fk);
						deleteMap.put(key,list);
//						System.out.println("new");
					}else{
						list.add(fk);
//						System.out.println("add");
					}
				}
				
				
//				Type type=tp.getPropertyType(str);
//				Class returnedClass=type.getReturnedClass();
//				System.out.println(str);
////				for(String s:tp.getPropertyColumnNames(str)){
//					if(!"string".equals(type.getName())){
//						FK fk=new FK();
//						fk.setFclass(tp.getType().getName());
//						fk.setColumnName(tp.getPropertyColumnNames(str)[0]);
//						System.out.println(type.getReturnedClass()+","+fk.getFclass()+","+fk.getColumnName());
//						List<FK> list=deleteMap.get(returnedClass);
//						if(list==null){
//							list=new ArrayList<FK>();
//							list.add(fk);
//							deleteMap.put(returnedClass,list);
//							System.out.println("new");
//						}else{
//							list.add(fk);
//							System.out.println("add");
//						}
//					}
//				}
			}
//			
//			for(Type type:tp.getClassMetadata().getPropertyTypes()){
//				String name=type.getName();
//				
//				if(!"string".equals(name)){
//					FK fk=new FK();
//					fk.setFclass(tp.getType().getReturnedClass());
//					fk.setColumnName(tp.getPropertyColumnNames(name)[0]);
//					deleteMap.put(tp.getEntityType().getReturnedClass(), fk);
//				}
//				System.out.println(name);
//				
//			}
			
//			System.out.println("---");
//			tp.getDiscriminatorColumnName()
//			for(String str:tp.get()){
//				System.out.println(str);
//			}
//			System.out.println("===");
		}
		
//		while(sessionFactory.getAllClassMetadata().size()().getAll().hasMoreElements()){
//			System.out.println(sessionFactory.getReference().getAll().nextElement().getType());
//		}
	}
}
