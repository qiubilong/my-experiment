package org.example.剑指.t53_在排序数组中查找数字.数组中数值和下标相等的元素;

import java.util.NoSuchElementException;

/**
 * @author chenxuegui
 * @since 2024/5/23
 * https://developer.aliyun.com/article/999553
 *
 * 假设一个单调递增的数组里的每个元素都是整数并且是唯一的。
 * 请编程实现一个函数找出数组中任意一个数值等于其下标的元素。
 * 例如，在数组 [−3,−1,1,3,5] 中，数字 3 和它的下标相等。
 *
 * 样例
 * 输入：[-3, -1, 1, 3, 5]
 * 输出：3
 */
public class Solution {

    //二分查找
    public int findNum(int[] nums){
        if(nums.length == 0){
            throw new IllegalArgumentException();
        }
        int left = 0;
        int right = nums.length -1;
        while (left <= right){
            int mid = left + (right -left)/2;
            if(mid == nums[mid]){
                return nums[mid];
            }else if(mid > nums[mid]){
                left = mid+1;
            }else if(mid < nums[mid]){
                right = mid-1;
            }
        }
        throw new NoSuchElementException();
    }


    public static void main(String[] args) {
        int[] nums = new int[]{-3, -1, 1, 3, 5};
        Solution solution = new Solution();
        System.out.println(solution.findNum(nums));
    }
}
