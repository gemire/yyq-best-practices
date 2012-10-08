package com.yyq.dataStructure;

/**
 * 栈的实现（可以使用数组或是ArrayList实现）
 */
public interface IStack<T> {
	//判断栈是否为空
	public boolean isEmpty();
	//清空栈
	public void clean();
	//访问栈顶元素（元素不出栈）
	public T top();
	//取出栈顶元素（元素出栈）
	public T pop();
	//将元素入栈
	public void push(T obj);
}
