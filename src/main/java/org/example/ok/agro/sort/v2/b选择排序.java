package org.example.ok.agro.sort.v2;

/**
 * @author chenxuegui
 * @since 2024/1/26
 * 选择排序：每次从待排序列表选择一个最小值
 * 时间复杂度：最好最差一样
 *  比较: n-1 + n-2 + 2 + 1 = n(n-1)/2
 *  交换：3*n
 * 总体性能对比冒泡排序，减少了交换次数，但不如插入排序
 */
public class b选择排序 {

    public static void main(String[] args) {
        int[] nums = {2,86,34,56,23,12,1};
        selectionSort(nums);
    }

    public static void selectionSort(int[] nums) {

        for (int i = 0; i < nums.length; i++) {
            int minIndex = i;
            for (int j = i+1; j < nums.length; j++) {
                //找到最小值
                if(nums[j] < nums[minIndex]){
                    minIndex = j;
                }
            }

            swap(nums,i,minIndex);
        }

        //System.out.println(Arrays.toString(nums));
    }

    public static void swap(int[] nums,int i, int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
