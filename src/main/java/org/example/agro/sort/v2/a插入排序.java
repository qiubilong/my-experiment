package org.example.agro.sort.v2;

import java.util.Arrays;

/**
 * @author chenxuegui
 * @since 2024/1/25
 * 简单排序（冒泡，选择，插入）中插入性能最好，因为其操作指令少
 * 平均时间复杂度
 *  比较：最好为 0，最坏为 1+2+..+ n-1 = n*(n-1)/2 ，平均---> n(n-1)/4
 *  移动：和比较一样，平均---> n(n-1)/4
 *  总体：n(n-1)/2
 * 数据规模小（如n<=7）选择插入排序，规模大选择快速排序
 * https://www.scaler.com/topics/insertion-sort-in-java/
 */
public class a插入排序 {

    public static void main(String[] args) {
        int[] nums = {4,764,65,32,2,45,6};

        insertionSort(nums);

        /** jdk中，
         * · 数组长度<47时，使用插入排序
         * · 数组长度>286且基本有序时，使用归并排序
         * · 其他使用快速排序
         * */
        Arrays.sort(nums);
    }

    public static void insertionSort(int[] nums){

        //i = 1从第二个元素开始，默认第一个是有序序列
        for (int i = 1; i < nums.length; i++) {

            //待排序元素
            int key = nums[i];

            int j = i-1;
            while (j>=0 && nums[j]> key){
                //往后移
                nums[j+1] = nums[j];
                j--;
            }

            //j+1是执行了j--，第一个大于key元素的位置
            nums[j+1] =  key;
        }

        //System.out.println(Arrays.toString(nums));
    }
}
