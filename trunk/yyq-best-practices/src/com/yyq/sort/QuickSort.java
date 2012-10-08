package com.yyq.sort;

import com.yyq.sort.util.SortUtil;

/**
 * @author YYQ
 * 快速排序
 */
public class QuickSort {   
	public static void main(String[] args) {
		int[] arr=SortUtil.createArray(10000);//new int[]{5,6,8,2,4,1,9,7,3,8,4,2,6,5};//5,6,8,2,4,1,9,7,3,8,4,2,6,5
		sort(arr);
		for(int i=0;i<arr.length;i++) System.out.print(arr[i]+" ");
		System.out.println();
		for(int i=0;i<arr.length-1;i++){
			if(arr[i]>arr[i+1]){
				System.out.println(arr[i]+" "+arr[i+1]);
				System.out.println("出错了！");
			}
		}
	}
	public static void sort(int[] arr){
		qsort(arr,0,arr.length-1);
	}
	private static void qsort(int[] arr, int begin, int end) {
		
		if (end - begin <1) return;
		int pivot = find(arr, begin, end);
//		System.out.println("一次排序后的结果："+pivot);
//		for(int i=0;i<arr.length;i++) System.out.print(arr[i]+" ");
//		System.out.println();
		qsort(arr, begin, pivot-1);
		qsort(arr, pivot + 1, end);
    }
	private static int partition(int[] arr, int begin, int end) {
//		System.out.print("待排序数组：");
//		for(int i=begin;i<=end;i++) System.out.print(arr[i]+" ");
//		System.out.println();
		
		int value=arr[begin];//开始时以第一个数作为参照值
		int left=begin;//初始化左右指针
		int right=end;
		while(left<right){//一直循环，直到右指针在左指针左边
			while((arr[left]<=value)&&(left<end)) left++;//左指针找到下一个比参照值大的数
			while((arr[right]>=value)&&(right>begin)) right--;//右指针找到下一个比参照值小的数
			
			if(left<right){//如果左指针在右指针左边则交换左右指针指的数
				SortUtil.swap(arr,left,right);
			}
		}
		if(right<begin){//如果右指针超出左边界则返回第一个数的下标
			return begin;
		}else{//否则交换右指针指的数和第一个数，并返回右指针下标
			SortUtil.swap(arr,right,begin);
			return right;
		}
		
		
		
	}
	
	/**
	 * 网上的一个中间数查找算法
	 */
	private static int find(int[] arr,int left,int right){
		boolean moveLeft=true;//是否是移左指针（true移左指针，false移右指针）
		while(left!=right){//移左右指针，直到左右指针重叠
			if(arr[left]>arr[right]){
				SortUtil.swap(arr,left,right);
				moveLeft=!moveLeft;//变换移左右指针
			}
			if(moveLeft){
				left++;
			}else{
				right--;
			}
		}
		return left;
	}

	

}