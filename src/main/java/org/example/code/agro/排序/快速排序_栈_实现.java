package org.example.code.agro.排序;

import java.util.Arrays;
import java.util.Random;
import java.util.Stack;

import static org.example.code.agro.排序.快速排序.swap;

/**
 * @author chenxuegui
 * @since 2023/11/6
 */
public class 快速排序_栈_实现 {

    public static void main(String[] args) {
        int[] nums = {2,5,6,4,8,9,1,123,43545};
        quickSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void quickSort(int[] nums){
        if(nums.length <=1){
            return;
        }
        Stack<Integer> stack = new Stack<>();
        stack.push(0);
        stack.push(nums.length -1);
        while (!stack.isEmpty()){
            int right = stack.pop();
            int left = stack.pop();
            if(left >= right){
                continue;
            }

            int p = partition(nums,left,right);

            stack.push(p+1);
            stack.push(right);

            stack.push(left);
            stack.push(p - 1);
        }
    }

    public static int partition(int[] nums,int left,int right){
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
                swap(nums,i,j);
            }
        }
        nums[left] = nums[i];
        nums[i] = pivot;

        return i;
    }

}
