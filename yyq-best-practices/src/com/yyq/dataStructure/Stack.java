package com.yyq.dataStructure;

import java.util.ArrayList;
import java.util.List;

public class Stack<T> implements IStack<T>{
	private List<T> list;
	public Stack(){
		list=new ArrayList<T>(100);
	}
	
	@Override
	public void clean() {
		list.clear();
	}

	@Override
	public boolean isEmpty() {
		return list.isEmpty();
	}

	@Override
	public T pop() {
		if(list.size()==0) throw new RuntimeException("出栈错误，栈为空！");
		T obj=list.remove(list.size()-1);
		return obj;
	}

	@Override
	public void push(T obj) {
		list.add(obj);
	}

	@Override
	public T top() {
		if(list.size()==0) throw new RuntimeException("取栈顶元素错误，栈为空！");
		T obj=list.get(list.size()-1);
		return obj;
	}
	
}
