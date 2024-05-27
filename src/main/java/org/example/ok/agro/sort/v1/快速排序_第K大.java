package org.example.ok.agro.sort.v1;

import java.util.Arrays;

/**
 * @author chenxuegui
 * @since 2023/11/7
 */
public class 快速排序_第K大 {
    public static void main(String[] args) {
        int[] nums = {1,5,65,2,78,989,8965};
        int[] clone = nums.clone();
        Arrays.sort(clone);
        System.out.println(Arrays.toString(clone));
        int k = 3;
        System.out.println(topk(nums,k));
    }

    private static int topk(int[] nums, int k) {
        if(nums.length< k){
            throw new IllegalArgumentException();
        }

        int left = 0;
        int right = nums.length -1;
        int k1 = nums.length - k;
        while (left < right) {

            int p = partition(nums,left,right);
            if(p == k1){
                return nums[p];
            }else if (p < k1 ){
                left = p+1;
            }else {
                right = p-1;
            }
        }
        return nums[left];
    }

    public static int partition(int[] nums, int left,int right){
        int i = left;
        int j = right;
        int pivot = nums[left];
        while (i<j){
            while (i<j && nums[j]>= pivot){
                j--;
            }
            while ( i<j && nums[i]<= pivot){
                i++;
            }
            if(i<j){
                快速排序.swap(nums,i,j);
            }
        }
        nums[left] = nums[i];
        nums[i] = pivot;

        return i;
    }

}
