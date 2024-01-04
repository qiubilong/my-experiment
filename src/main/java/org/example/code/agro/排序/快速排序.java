package org.example.code.agro.排序;

import java.util.Arrays;
import java.util.Random;

/**
 * @author chenxuegui
 * @since 2023/11/6
 * 快速排序，平均时间复杂度O(n*logn) ,最坏n~2
 */
public class 快速排序 {
    public static void main(String[] args) {
        int[] nums = {2,5,6,4,8,9,1,123,43545};
        quickSort(nums);
        System.out.println(Arrays.toString(nums));
    }

    public static void quickSort(int[] nums){
        quickSort(nums,0, nums.length -1);
    }
    public static void quickSort(int[] nums,int left,int right){
        //递归结束条件
        if(left >= right){
            return ;
        }
        //基准归位
        int i = partition(nums, left, right);

        //左数组递归
        quickSort(nums, left,i -1);
        //右数组递归
        quickSort(nums,i +1, right);
    }

    public static int partition(int[] nums, int left, int right){
        //防止最坏情况下数组有序,时间复杂度退化成n平方，选定随机基准值
        int randomIndex = left + new Random().nextInt(right - left);
        swap(nums,randomIndex,left);

        int i = left;
        int j = right;
        int pivot  = nums[left];
        while (i <j){
            //右指针先扫描，因为基准是选左边
            while (i<j && nums[j]>= pivot){
                j--;
            }
            //如果左指针先扫描，结束时会停留在基准值大的位置上，最后交换基准值时会把大于基准值的元素交换到左边，
            while (i< j && nums[i] <= pivot){
                i++;
            }
            if(i<j){
                swap(nums,i,j);
            }
        }
        //基准归位
        nums[left] = nums[i];
        nums[i] = pivot;
        return i;
    }

    public static void swap(int[] nums, int i, int j){
        int temp = nums[j];
        nums[j] = nums[i];
        nums[i] = temp;
    }
}
