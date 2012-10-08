package com.yyq.dataStructure;

/**
 * 队列的实现（可以基于链表实现，链表可使用LinkedNode类）
 */
public interface IQueue<T> {
	//判断队列是否为空
	public boolean isEmpty();
	//清空队列
	public void clean();
	//访问队尾元素（元素不出栈）
	public T end();
	//取出队尾元素（元素出栈）
	public T pop();
	//将元素入队
	public void push(T obj);
}
