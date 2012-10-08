package com.yyq.dataStructure;

import junit.framework.TestCase;

/**
 * 栈的JUnit测试
 */
public class StackTest extends TestCase{
	public void testStack(){
		IStack stack=new Stack();
		assertTrue(stack.isEmpty());
		stack.push(1);
		assertEquals(1, stack.top());
		stack.push(2);
		assertEquals(2, stack.top());
		stack.pop();
		assertEquals(1, stack.top());
		assertFalse(stack.isEmpty());
		stack.pop();
		assertTrue(stack.isEmpty());
		stack.push(1);
		stack.push(2);
		stack.clean();
		assertTrue(stack.isEmpty());
	}
}
