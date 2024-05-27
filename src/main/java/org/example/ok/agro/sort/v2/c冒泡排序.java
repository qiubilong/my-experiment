package org.example.ok.agro.sort.v2;

/**
 * @author chenxuegui
 * @since 2024/1/26
 * 冒泡排序: 通过两两比较大小交换完成排序
 * 时间复杂度:
 * 比较：n-1 + n-2 +..+ 2 + 1 = n(n-1)/2
 * 交换：最好为0，最差为 3 * ( n-1 + n-2 +..+ 2 + 1 ) = 3* n(n-1)/2，平均为 3*n(n-1)/4
 * 总体：(3/4) n * n
 */
public class c冒泡排序 {

    public static void main(String[] args) {
        int[] nums = {3,5,1,45,6,8,9};
        bubbleSort(new int[]{3, 5, 1, 45, 6, 8, 9});
        bubbleSortPro(new int[]{24,54,523,65,343});
    }

    public static void bubbleSort(int[] nums) {
        for (int i = 0; i < nums.length -1; i++) {

            for (int j = 0; j < nums.length -1 - i; j++) {
                if(nums[j] > nums[j+1]){
                    swap(nums,j,j+1);
                }
            }
        }

        //System.out.println(Arrays.toString(nums));
    }

    /** 改良版，
     *  时间复杂度：
     *  比较：最好为n, 最差为n(n-1)/2，平均为n/2 + n(n-1)/4
     *  交换：最好为0，最差为 3 * ( n-1 + n-2 +..+ 2 + 1 ) = 3* n(n-1)/2，平均为 3*n(n-1)/4
     *  总体： （1/2）n * n
     */
    public static void bubbleSortPro(int[] nums) {
        for (int i = 0; i < nums.length -1; i++) {
            boolean sort = true;
            for (int j = 0; j < nums.length -1 - i; j++) {
                if(nums[j] > nums[j+1]){
                    swap(nums,j,j+1);
                    sort = false;
                }
            }
            if(sort){
                break;
            }
        }

       // System.out.println(Arrays.toString(nums));
    }

    public static void swap(int[] nums,int i,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }
}
