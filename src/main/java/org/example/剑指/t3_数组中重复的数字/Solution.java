package org.example.剑指.t3_数组中重复的数字;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author chenxuegui
 * @since 2024/5/24
 * https://leetcode.cn/problems/find-all-duplicates-in-an-array/description/
 * 在一个长度为 n 的数组里的所有数字都在 0 到 n-1 的范围内。数组中某些数字是重复的，但不知道有几个数字是重复的，也不知道每个数字重复几次。请找出数组中任意一个重复的数字。
 */
public class Solution {

    /** 排序解决 ，时间复杂度O(n*log(n)) */
    public int findDuplicateNum_sort(int[] nums){
        if(nums.length <=1){
            return -1;
        }
        Arrays.sort(nums);
        System.out.println(Arrays.toString(nums));
        int last = nums[0];
        for (int i = 1; i < nums.length; i++) {
            if(nums[i] == last){
                return nums[i];
            }
            last = nums[i];
        }

        return -1;
    }
    /** map统计，空间复杂度O(n) */
    public int findDupNum_Map(int[] nums){
        if(nums.length <=1){
            return -1;
        }
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(nums[i])){
                return nums[i];
            }
            map.put(nums[i],1);
        }
        return -1;
    }

    /** 利用下标排序原有数组 ,空间复杂度O(1) */
    public int findDupNum_Index(int[] nums){
        if(nums.length <= 0){
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < nums.length; i++) {
            while (nums[i] != i){
                if(nums[nums[i]] == nums[i]){
                    return nums[i];
                }
                swap(nums,nums[i],i);
            }
        }
        throw new IllegalArgumentException();
    }

    private void swap(int[] nums, int i ,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }

    /** 利用下标排序原有数组 ,空间复杂度O(1),数字从1开始 */
    public List<Integer> findDupNums_Index(int[] nums){
        if(nums.length <= 0){
            return new ArrayList<>();
        }
        Set<Integer> result = new HashSet<>();
        for (int i = 0; i < nums.length; i++) {
            while (nums[i]-1 != i){
                int a = nums[i]-1;
                if(nums[a] == nums[i]){
                    if(result.contains(nums[i])){
                        break;
                    }
                    result.add(nums[i]);
                }
                swap(nums,a,i);
            }
        }
        return new ArrayList<>(result);
    }

    /** 利用数字当数组下标，不需要排序，数字从1开始 */
    public List<Integer> findDuplicates(int[] nums) {
        if(nums.length <= 0){
            return new ArrayList<>();
        }
        List<Integer> result = new ArrayList<>();

        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            int index = Math.abs(num) -1;
            //第一次访问
            if(nums[index] > 0){
                nums[index] = -nums[index];
            }else {
                result.add(index + 1);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{2,3,1,0,2,5,3};

        Solution solution = new Solution();
        System.out.println(solution.findDuplicateNum_sort(nums));
        System.out.println(solution.findDupNum_Map(nums));
        System.out.println(solution.findDupNum_Index(nums));

        int[] nums2 = new int[]{4,3,2,7,8,2,3,1};
        //System.out.println(solution.findDupNums_Index(nums2));
        System.out.println(solution.findDuplicates(nums2));
    }
}
