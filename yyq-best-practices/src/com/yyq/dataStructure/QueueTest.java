package com.yyq.dataStructure;

import junit.framework.TestCase;

/**
 * 队列的JUnit测试
 */
public class QueueTest extends TestCase{
	public void testQueue(){
		IQueue queue=new Queue();
		assertTrue(queue.isEmpty());
		queue.push(1);
		assertEquals(1, queue.end());
		queue.push(2);
		assertEquals(1, queue.end());
		queue.pop();
		assertEquals(2, queue.end());
		assertFalse(queue.isEmpty());
		queue.pop();
		assertTrue(queue.isEmpty());
		queue.push(1);
		queue.push(2);
		queue.clean();
		assertTrue(queue.isEmpty());
	}
}
