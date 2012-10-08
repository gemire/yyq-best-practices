package com.yyq.sort;

import com.yyq.sort.util.SortUtil;

/**
 * @author YYQ
 * 基数排序
 */
public class RadixSort {
	public static void main(String[] args) {
		int[] arr=SortUtil.createArray(10);
		for(int i:arr){
			System.out.print(i+" ");
		}
		System.out.println();
		sort(arr);
		for(int i=1;i<arr.length;i++){
			if(arr[i]<arr[i-1]) System.out.println("出错了！");
		}
		for(int i:arr){
		System.out.print(i+" ");
		}
		System.out.println();
	}
	
	public static void sort(int[] arr){
		int n=arr.length;
		int[][] temp=new int[10][n];
		int[] sum=new int[10];
		int k=0;
		int num;//取出的个位或十位的数
		int m=1;//
		
		while(true){
			for(int t:arr){
				num=(t/m)%10;//取出第几位上的数
				temp[num][sum[num]]=t;//将原数组里的数放入十个数组中的第num个
				sum[num]++;
			}
			for(int i=0;i<10;i++){
				if(sum[i]!=0){//如果第i个数组不为空
					for(int j=0;j<sum[i];j++){//将第i个数组中的元素放回原数组arr中
						arr[k]=temp[i][j];
						k++;
					}
				}
			}
			if(sum[0]==n) break;//所有数都在arr0里，即数组已经排好
			for(int i=0;i<10;i++) sum[i]=0;//将十个数组标记为空
			k=0;//还原k的值，为下次循环做准备
			m*=10;//准备取下一位
			if(m<0) break;//超出整数范围退出
		}
		
	}
}
