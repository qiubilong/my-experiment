package org.example.ok.剑指;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * https://leetcode.cn/problems/diao-zheng-shu-zu-shun-xu-shi-qi-shu-wei-yu-ou-shu-qian-mian-lcof/submissions/537454000/
 * @author chenxuegui
 * @since 2024/6/5
 */
public class Solution {

    public int[] reorder(int[] nums){
        List<Integer> list1 = new ArrayList<>();
        List<Integer> list2 = new ArrayList<>();
        for (int num : nums) {
            if(num %2 == 0){
                list2.add(num);
            }else {
                list1.add(num);
            }
        }
        list1.addAll(list2);
        return list1.stream().mapToInt(v->v).toArray();
    }

    public int[] reorder2(int[] nums){
        int i = 0;
        int j = nums.length - 1;
        while (i<j){
            //向后移动，直到遇到偶数
            while (i<j && nums[i] %2 != 0){
                i++;
            }
            //向前移动，直到遇到奇数
            while (i<j && nums[j]%2 ==0){
                j--;
            }
            if(i<j){
                swap(nums,i,j);
            }
        }
        return nums;
    }

    private void swap(int[] nums,int i ,int j){
        int temp = nums[i];
        nums[i] = nums[j];
        nums[j] = temp;
    }



    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3,4,5};
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.reorder(nums)));
        System.out.println(Arrays.toString(solution.reorder2(nums)));
        int[] nums2 = new int[]{1,2,3,4,5};
        for (int num : nums2) {
            System.out.println(Integer.toBinaryString(num));
        }
    }
}
