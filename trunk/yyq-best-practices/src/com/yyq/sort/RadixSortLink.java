package com.yyq.sort;

import java.util.LinkedList;

import com.yyq.dataStructure.LinkedNode;

import com.yyq.sort.util.SortUtil;

public class RadixSortLink {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		LinkedList<Integer> arr=SortUtil.createLinkList(100);
		System.out.println(arr);
		arr=sort(arr);
		for(int i=0;i<arr.size()-1;i++){
			if(arr.get(i)>arr.get(i+1)) System.out.println("出错了！");
		}
		

	}
	
	public static LinkedList sort(LinkedList<Integer> arr) {
		int k=arr.size();
		int num;//取出的个位或十位的数
		int m=1;//
		LinkedList<Integer>[] lists=new LinkedList[10];
		for(int i=0;i<10;i++) lists[i]=new LinkedList<Integer>();
		LinkedList<Integer> list=new LinkedList<Integer>();
		int t;
		
		while(true){
			for(int i=0;i<k;i++){
				t=arr.remove();
//				System.out.println();
//				System.out.println("t="+t);
				num=(t/m)%10;//取出第几位上的数
				lists[num].add(t);
//				if(end[num]==null){
//					start[num]=end[num]=arr;
//					System.out.println(num);
//				}else{
//					end[num]=arr;
//					end[num]=end[num].next;
//				}
				
//				end[num].next=null;
//				println(lists);
//				try {
//					Thread.sleep(2000);
//				} catch (InterruptedException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				System.out.println("arr="+arr.num);
			}
			if(lists[0].size()==k){
				arr=lists[0];
				break;//所有数都在arr0里，即数组已经排好
			}
			for(int i=0;i<10;i++){
				if(lists[i].size()!=0){
					list.addAll(lists[i]);
				}
			}
			
			for(int i=0;i<10;i++) lists[i].clear();//将十个数组标记为空
			arr=list;
			m*=10;//准备取下一位
			if(m<0) break;//超出整数范围退出
			
		}
		return arr;
	}
	
	public static void println(LinkedList<Integer>[] start){
		for(int i=0;i<10;i++){
			if(start[i].size()!=0){
				System.out.print(i+"=");
				System.out.println(start[i]);
			}
		}
	}

}
