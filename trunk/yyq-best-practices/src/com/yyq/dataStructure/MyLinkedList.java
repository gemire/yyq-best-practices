package com.yyq.dataStructure;

/**
 * @author YYQ
 * 自己实现的链表数据结构
 * @param <T>
 */
public class MyLinkedList<T> {
	private LinkedNode<T> start;
	private LinkedNode<T> end;
	private int length;
	private LinkedNode<T> obj;
	public int size(){
		return length;
	}
	public T get(int index){
		LinkedNode<T> t=start;
		for(int i=0;i<index;i++){
			t=t.next;
		}
		return t.num;
	}
	public LinkedNode<T> remove(){
		obj=start;
		start=start.next;
		length--;
		return obj;
	}
	public void add(LinkedNode<T> t){
		if(start==null){
			start=end=t;
		}else{
			end=end.next=t;
		}
		length++;
	}
	public void addAll(MyLinkedList<T> list){
		if(start==null){
			start=list.start;
			end=list.end;
			length=list.length;
		}else{
			end.next=list.start;
			end=list.end;
			length+=list.length;
		}
	}
	public void clear(){
		start=end=null;
		length=0;
	}
	@Override
	public String toString() {
		StringBuffer sb=new StringBuffer();
		sb.append("[");
		LinkedNode<T> obj=new LinkedNode<T>(start.num);
		obj.next=start.next;
		for(int i=length;i>0;i--){
			sb.append(obj.num);
			sb.append(",");
			obj=obj.next;
		}
		sb.append("]");
		return sb.toString();
	}
}
