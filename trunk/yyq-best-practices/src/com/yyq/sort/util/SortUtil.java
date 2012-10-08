package com.yyq.sort.util;

import java.util.LinkedList;
import java.util.Random;

import com.yyq.dataStructure.LinkedNode;
import com.yyq.dataStructure.MyLinkedList;

/**
 * @author YYQ
 * 排序工具类
 */
public class SortUtil {
	/**
	 * 生成指定长度的随机整数数组
	 * @param length
	 * @return
	 */
	public static int[] createArray(int length){
		int[] intArr=new int[length];
		Random random=new Random();
		for(int i=0;i<length;i++){
			intArr[i]=Math.abs(random.nextInt());
		}
		return intArr;
	}
	/**
	 * 生成指定长度的随机整数数组（基于链表结构）
	 * @param length
	 * @return
	 */
	public static LinkedNode createLink(int length){
		Random random=new Random();
		LinkedNode start;
		LinkedNode end;
		start=end=new LinkedNode(Math.abs(random.nextInt()));
		for(int i=1;i<length;i++){
			LinkedNode newNode=new LinkedNode(Math.abs(random.nextInt()));
			end.next=newNode;
			end=newNode;
		}
		return start;
	}
	/**
	 * 生成指定长度的随机整数数组（基于Java的链表结构）
	 * @param length
	 * @return
	 */
	public static LinkedList<Integer> createLinkList(int length){
		Random random=new Random();
		LinkedList<Integer> linkedList=new LinkedList<Integer>();
		for(int i=0;i<length;i++){
			linkedList.add(Math.abs(random.nextInt()));
		}
		return linkedList;
	}
	/**
	 * 生成指定长度的随机整数数组（基于自己的链表结构）
	 * @param length
	 * @return
	 */
	public static MyLinkedList<Integer> createMyLinkList(int length){
		Random random=new Random();
		MyLinkedList<Integer> linkedList=new MyLinkedList<Integer>();
		for(int i=0;i<length;i++){
			linkedList.add(new LinkedNode(Math.abs(random.nextInt())));
		}
		return linkedList;
	}
	/**
	 * 交换数组中的两个数
	 */
	public static void swap(int[] number, int i, int j) {
		int t;
		t = number[i];
		number[i] = number[j];
		number[j] = t;
	}
}
