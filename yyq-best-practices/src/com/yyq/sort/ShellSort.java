package com.yyq.sort;

import java.util.ArrayList;

import com.yyq.sort.util.SortUtil;

/**
 * @author YYQ
 * 希尔排序
 */
public class ShellSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr=SortUtil.createArray(100);
		arr=sort(arr);
		for(int i=0;i<arr.length-1;i++){
			if(arr[i]>arr[i+1]){
				System.out.println(arr[i]+" "+arr[i+1]);
				System.out.println("出错了！");
			}
		}
//		for(int p:arr) System.out.print(p+" ");
//		System.out.println();
	}
	public static int[] sort(int[] arr){
		int step=arr.length-1;
		ArrayList<Integer> list=new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		boolean flag=false;
		for(int i=2;i<arr.length;i++){
			flag=false;
			for(int j=1;j<list.size();j++){
				if(i%list.get(j)==0){
					flag=true;
					break;
				}
			}
			if(flag==false) list.add(i);
		}
		
		for(int p:list) System.out.print(p+"=");
		System.out.println();
		
		int temp2;
		while(step>1){
			System.out.println("step="+step);
			for(int i=0;i<list.size();i++){
				
				temp2=list.get(i);
				if((temp2<step)&&((i+1==list.size())||list.get(i+1)>step)){
					System.out.println("加入："+step);
					list.add(i, step);
					break;
				}
				
			}
			step=(step+1)/2;
		}
		
		int temp=0;
//		for(;;step=(step+1)/2){
		for(int j=list.size()-1;j>=0;j--){
			step=list.get(j);
			System.out.println("step="+step);
			//for(int j=0;j<arr.length-step;j++){
				for(int i=0;i<arr.length-step;i++){
					if(arr[i]>arr[i+step]){
						System.out.println("交换元素："+arr[i]+" "+arr[i+step]);
//						System.out.println(temp+":"+arr[i]+":"+arr[i+step]);
						temp=arr[i];
						arr[i]=arr[i+step];
						arr[i+step]=temp;
//						System.out.println(temp+":"+arr[i]+":"+arr[i+step]);
					}
				}
			//}
			for(int p:arr) System.out.print(p+",");
			System.out.println();
			if(step==1) break;
		}
		return arr;
	}

}
