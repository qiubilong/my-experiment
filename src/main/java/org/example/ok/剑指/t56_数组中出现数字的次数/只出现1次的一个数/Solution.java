package org.example.ok.剑指.t56_数组中出现数字的次数.只出现1次的一个数;

/**
 * @author chenxuegui
 * @since 2024/5/21
 */
public class Solution {

    public int singleNum(int[] nums){
        int num = 0;
        for (int i = 0; i < nums.length; i++) {
            num ^= nums[i];
        }
        return num;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4,1,2,1,2};
        Solution solution = new Solution();
        System.out.println(solution.singleNum(nums));
    }
}
