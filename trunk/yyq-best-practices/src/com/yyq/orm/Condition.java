package com.yyq.orm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.util.StringUtils;

import com.yyq.util.StringUtil;

/**
 * 这个类用来封装两个表之间的关联查询条件
 * @author Administrator
 */
public class Condition {
	public enum condition{Equal,Locate};
	private HashMap<String, Condition.condition> conditionMap=new HashMap<String, Condition.condition>();
	private HashMap<String, String> valueMap=new HashMap<String, String>();
	private List<String> argList=new ArrayList<String>();//参数列表
	StringBuffer sb=new StringBuffer();
	private String table1;
	private String table2;
	private String t1;
	private String t2;
	/**
	 * 创建两个表的关联查询
	 * 并将它们的属性值作为查询的限定条件
	 * @param tabel_1
	 * @param key1
	 * @param tabel_2
	 * @param key2
	 */
	public Condition(Object tabel_1,String key1,Object tabel_2,String key2){
		table1=tabel_1.getClass().getSimpleName();
		table2=tabel_2.getClass().getSimpleName();
		t1=StringUtil.lowerFirst(table1);
		t2=StringUtil.lowerFirst(table2);
		sb.append("from ");
		sb.append(table1);
		sb.append(" ");
		sb.append(t1);
		sb.append(",");
		sb.append(table2);
		sb.append(" ");
		sb.append(t2);
		sb.append(" where ");
		sb.append(t1);
		sb.append(".");
		sb.append(key1);
		sb.append("=");
		sb.append(t2);
		sb.append(".");
		sb.append(key2);
		
		addEqual(tabel_1,tabel_2);
	}
	/**
	 * 将对象的属性值作为查询的限定条件
	 * @param objs
	 * @return
	 */
	public Condition addEqual(Object... objs){
		String name;
		String value;
		String key;
		for(Object obj:objs){
			Map<String, String> map=null;
			try {
				map = BeanUtils.describe(obj);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (Map.Entry<String,String> m : map.entrySet()) {
				name=m.getKey();
				value=m.getValue();
				if(value!=null&&value.length()!=0&&!"class".equals(name)){
					key=(obj.getClass().getSimpleName().equals(table1)?t1:t2)+"."+name;
					conditionMap.put(key, condition.Equal);
					valueMap.put(key, value);
				}
			}
		}
		return this;
	}
	/**
	 * 添加某字段包含特定字符串的限定条件
	 * @param key
	 * @param value
	 * @return
	 */
	public Condition locate(String key,String value){
		if(StringUtils.hasLength(value)){
			key=StringUtil.lowerFirst(key);
			conditionMap.put(key, condition.Locate);
			valueMap.put(key, value);
		}
		return this;
	}
	/**
	 * 生成hql语句
	 * @return
	 */
	public String tohql(){
		for (Map.Entry<String,Condition.condition> m : conditionMap.entrySet()) {
			String name=m.getKey();
			Condition.condition value=m.getValue();
			switch(value){
			case Equal:
				sb.append(" and ");
				sb.append(name);
				sb.append("=?");
				argList.add(valueMap.get(name));
				break;
			case Locate:
//				sb.append(" and ");
//				sb.append(name);
//				sb.append(" like concat(?,'%')");
				sb.append(" and LOCATE(?,");
				sb.append(name);
				sb.append(")<>0");
				argList.add(valueMap.get(name));
				break;
			}
			
		}
		return sb.toString();
	}
	/**
	 * 获得参数列表
	 * @return
	 */
	public String[] getArgs(){
		String[] s=new String[argList.size()];
		s=argList.toArray(s);
		return s;
	}
}
