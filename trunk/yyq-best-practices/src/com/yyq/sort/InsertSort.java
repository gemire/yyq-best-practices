package com.yyq.sort;

import com.yyq.dataStructure.LinkedNode;
import com.yyq.sort.util.SortUtil;

/**
 * @author YYQ
 * 插入排序
 */
public class InsertSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedNode<Integer> start=SortUtil.createLink(10);
		LinkedNode<Integer> temp=start;
		while(temp.next!=null){
			System.out.println(temp.num);
			temp=temp.next;
		}
		System.out.println("==============");
		start=stort(start);
		temp=start;
		while(temp.next!=null){
			System.out.println(temp.num);
			temp=temp.next;
		}
	}
	public static LinkedNode stort(LinkedNode<Integer> start){
		LinkedNode<Integer> newStart=start;
		start=start.next;
		
		do{
			newStart=insert(newStart,start);
			start=start.next;
		}while(start!=null);
		return newStart;
	}
	public static LinkedNode insert(LinkedNode<Integer> start,LinkedNode<Integer> node){
		if(node.num<start.num){
			node.next=start;
			start=node;
			return start;
		}else{
			LinkedNode<Integer> insert=start;
			while(!(insert.next==null||insert.next.num>node.num)){
				insert=insert.next;
				System.out.println("123");
			}
			System.out.println("insert="+insert.num);
			LinkedNode temp=insert.next;
			insert.next=node;
			node.next=temp;
			return start;
		}
	}
}
