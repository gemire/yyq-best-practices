package com.yyq.sort;

import java.util.LinkedList;

import com.yyq.dataStructure.MyLinkedList;

import com.yyq.sort.util.SortUtil;

/**
 * @author Administrator
 * 排序效率测试类
 */
public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr=SortUtil.createArray(100000);
		long start;
		long end;
		
		start=System.currentTimeMillis();
		QuickSort.sort(arr);
		end=System.currentTimeMillis();
		System.out.println("快速排序耗时："+(end-start));
		
		arr=SortUtil.createArray(100000);
		start=System.currentTimeMillis();
		RadixSort.sort(arr);
		end=System.currentTimeMillis();
		System.out.println("基数排序耗时："+(end-start));
		
		MyLinkedList<Integer> arr2=SortUtil.createMyLinkList(100000);
		start=System.currentTimeMillis();
		MyRadixSortLink.sort(arr2);
		end=System.currentTimeMillis();
		System.out.println("自己的基于链表的基数排序耗时："+(end-start));
		
		LinkedList<Integer> arr3=SortUtil.createLinkList(100000);
		start=System.currentTimeMillis();
		RadixSortLink.sort(arr3);
		end=System.currentTimeMillis();
		System.out.println("Java的基于链表的基数排序耗时："+(end-start));
		
//		start=System.currentTimeMillis();
//		BubbleSort.sort(arr);
//		end=System.currentTimeMillis();
//		System.out.println("冒泡排序耗时："+(end-start));
		
		
	}

}
