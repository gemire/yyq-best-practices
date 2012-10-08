package com.yyq.dataStructure;

/**
 * 链表节点
 */
public class LinkedNode<T> {
	//链表的节点内容
	public T num;
	//下一个节点
	public LinkedNode<T> next;
	public LinkedNode(T num){
		this.num=num;
	}
}
