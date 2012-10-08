package com.yyq.sort;

import com.yyq.sort.util.SortUtil;

/**
 * @author YYQ
 * 冒泡排序
 */
public class BubbleSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr=SortUtil.createArray(100);
		sort(arr);
		for(int i=0;i<arr.length-1;i++){
			if(arr[i]>arr[i+1]){
				System.out.println(arr[i]+" "+arr[i+1]);
				System.out.println("出错了！");
			}
		}
	}
	public static void sort(int[] arr){
		int temp;
		for(int i=1;i<arr.length;i++){
			for(int j=1;j<arr.length;j++){
				if(arr[j]<arr[j-1]){
					temp=arr[j];
					arr[j]=arr[j-1];
					arr[j-1]=temp;
				}
			}
		}
	}
}
