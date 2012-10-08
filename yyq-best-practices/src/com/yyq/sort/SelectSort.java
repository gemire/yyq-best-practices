package com.yyq.sort;

/**
 * @author YYQ
 * 选择排序
 */
public class SelectSort {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		int[] arr=new int[]{2,6,9,7,1,8,3,5,4,0};
		arr=sort(arr);
		for(int i:arr){
			System.out.println(i);
		}
	}
	private static int[] sort(int[] arr){
		int temp;
		int index;
		for(int i=0;i<arr.length;i++){
			temp=Integer.MAX_VALUE;
			index=i;
			for(int j=i;j<arr.length;j++){
				if(arr[j]<temp){
					temp=arr[j];
					index=j;
				}
			}
			if(index!=i){
				temp=arr[i];
				arr[i]=arr[index];
				arr[index]=temp;
			}
		}

		return arr;
	}
}
