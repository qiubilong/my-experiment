package org.example.ok.剑指.最小的k个数;

import java.util.Arrays;

/**
 * @author chenxuegui
 * @since 2024/5/21
 * https://leetcode.cn/problems/smallest-k-lcci/description/
 * 使用快速排序思想获取最小的k个数
 * 时间复杂度为O(n)
 */
public class Solution_快排 {

    public int[] smallestK(int[] arr, int k) {
        if (k<=0 || k> arr.length) {
            return new int[]{};
        }
        quickSort(arr,0, arr.length -1,k);
        int[] nums = new int[k];
        for (int i = 0; i < k; i++) {
            nums[i] = arr[i];
        }
        return nums;
    }

    public void quickSort(int[] arr,int left,int right, int k){
        if(left>=right){
            return;
        }
        int sortedIndex = partition(arr, left, right);
        //左边已经是最小k
        if(sortedIndex == k -1){
            return;
        }else if(sortedIndex < k -1){
            quickSort(arr, sortedIndex+1, right, k);
        }else {
            quickSort(arr, left, sortedIndex-1, k);
        }
    }

    public int partition(int[] arr,int left,int right){
        int i = left;
        int j = right;
        int pivot = arr[left];
        while (i<j){
            while (i<j && arr[j] >= pivot){
                j--;
            }
            while (i<j && arr[i] <= pivot){
                i++;
            }
            if(i<j){
                swap(arr,i,j);
            }
        }
        //基准归位
        arr[left] = arr[i];
        arr[i] = pivot;
        return i;
    }

    public void swap(int[] arr,int i ,int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,3,5,7,2,4,6,8};
        int k = 4;
        Solution_快排 solution = new Solution_快排();
        System.out.println(Arrays.toString(solution.smallestK(arr, k)));
    }
}
