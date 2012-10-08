package com.yyq.sort;

import com.yyq.dataStructure.MyLinkedList;
import com.yyq.dataStructure.LinkedNode;

import com.yyq.sort.util.SortUtil;

/**
 * @author Administrator
 * 自己的基于链表的基数排序
 */
public class MyRadixSortLink {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		MyLinkedList<Integer> arr=SortUtil.createMyLinkList(100);
		System.out.println(arr);
		arr=sort(arr);
//		for(int i=0;i<arr.size()-1;i++){
//			if(arr.get(i)>arr.get(i+1)) System.out.println("出错了！");
//		}
		

	}
	
	public static MyLinkedList sort(MyLinkedList<Integer> arr) {
		int k=arr.size();
		int num;//取出的个位或十位的数
		int m=1;//
		MyLinkedList<Integer>[] lists=new MyLinkedList[10];
		for(int i=0;i<10;i++) lists[i]=new MyLinkedList<Integer>();
		MyLinkedList<Integer> list=new MyLinkedList<Integer>();
		MyLinkedList<Integer> temp;
		int t;
		LinkedNode<Integer> tt;
		
		while(true){
			for(int i=0;i<k;i++){
				tt=arr.remove();
				t=tt.num;
				num=(t/m)%10;//取出第几位上的数
//				System.out.println();
//				System.out.println("t="+t+",num="+num);
				lists[num].add(tt);
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
//					System.out.println("数组"+i+"的长度为："+lists[i].size());
					list.addAll(lists[i]);
//					System.out.println("加入数组"+i+"后的值："+list);
				}
			}
			
//			System.out.println("======"+list);
			
			for(int i=0;i<10;i++) lists[i].clear();//将十个数组标记为空
			temp=list;
			list=arr;
			arr=temp;
			list.clear();
//			arr=list;
			m*=10;//准备取下一位
			if(m<0) break;//超出整数范围退出
			
		}
		return arr;
	}
	
	public static void println(MyLinkedList<Integer>[] start){
		for(int i=0;i<10;i++){
			if(start[i].size()!=0){
				System.out.print(i+"=");
				System.out.println(start[i]);
			}
		}
	}

}
