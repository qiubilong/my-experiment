package org.example.ok.剑指.t3_数组中重复的数字;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

/**
 * @author chenxuegui
 * @since 2024/5/27
 *
 * https://leetcode.cn/problems/find-the-duplicate-number/description/
 *
 * 给定一个包含 n + 1 个整数的数组 nums ，其数字都在 [1, n] 范围内（包括 1 和 n），可知至少存在一个重复的整数。
 * 假设 nums 只有 一个重复的整数 ，返回 这个重复的数 。
 * 你设计的解决方案必须 不修改 数组 nums 且只用常量级 O(1) 的额外空间。
 */
public class Solution_不改变数组 {

    /**
     * 在 循环体内，先猜一个数 mid，然后遍历「输入数组」，统计小于等于 mid 的元素个数 count，
     * 如果 count > mid 说明重复元素一定出现在 [left..mid] 因此设置 right = mid
     * https://leetcode.cn/problems/find-the-duplicate-number/solutions/7038/er-fen-fa-si-lu-ji-dai-ma-python-by-liweiwei1419/
     */
    public int findDupNum_二分(int[] nums){
        int left = 1;
        int right = nums.length -1;
        while (left < right){
            int mid = left + (right - left) /2;
            int count = 0;
            for (int num : nums) {
                if(num <= mid){
                    count++;
                }
            }

            if(count > mid){
                right = mid;
            }else {
                left = mid+1;
            }

        }
        //返回right也可以
        return left;
    }


    public int findDupNum_暴力(int[] nums){
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            for (int j = i+1; j < nums.length; j++) {
                if(num == nums[j]){
                    return num;
                }
            }
        }
        return -1;
    }


    public int findDupNum_Map(int[] nums){
        Map<Integer,Boolean> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            if(map.containsKey(num)){
                return num;
            }
            map.put(num,true);
        }
        throw new NoSuchElementException();
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1,3,4,2,2};
        Solution_不改变数组 solution = new Solution_不改变数组();
        System.out.println(solution.findDupNum_二分(nums));
        System.out.println(solution.findDupNum_Map(nums));
        System.out.println(solution.findDupNum_暴力(nums));
    }
}
