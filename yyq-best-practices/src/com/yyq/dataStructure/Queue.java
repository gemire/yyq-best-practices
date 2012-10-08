package com.yyq.dataStructure;

public class Queue<T> implements IQueue<T> {
	private LinkedNode<T> startNode;
	private LinkedNode<T> endNode;
	
	@Override
	public void clean() {
		endNode=startNode=null;
	}

	@Override
	public boolean isEmpty() {
		return startNode==null;
	}

	@Override
	public T pop() {
		if(startNode==null) throw new RuntimeException("出队错误，队列为空！");
		T num=startNode.num;
		startNode=startNode.next;
		return num;
	}

	
	public void push(T obj) {
		LinkedNode node=new LinkedNode(obj);
		if(endNode!=null){
			endNode.next=node;
		}else{
			startNode=node;
		}
		endNode=node;
	}

	@Override
	public T end() {
		if(startNode==null) throw new RuntimeException("取队尾元素错误，队列为空！");
		return startNode.num;
	}

}
