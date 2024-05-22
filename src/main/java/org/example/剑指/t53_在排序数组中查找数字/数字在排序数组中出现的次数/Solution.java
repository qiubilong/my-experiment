package org.example.剑指.t53_在排序数组中查找数字.数字在排序数组中出现的次数;

/**
 * @author chenxuegui
 * @since 2024/5/22
 * https://leetcode.cn/problems/zai-pai-xu-shu-zu-zhong-cha-zhao-shu-zi-lcof/solutions/
 */
public class Solution {

    /** 线性遍历 */
    public int countTarget(int[] scores, int target) {
        int count = 0;
        for (int i = 0; i < scores.length; i++) {
            if(target == scores[i]){
                count++;
            }
        }
        return count;
    }

    /** 二分查询法遍历 */
    public int countTargetByBinarySearch(int[] scores, int target) {
        if(scores.length ==0){
            return 0;
        }
        int firstK = findFirstK(scores,target,0,scores.length -1);
        int lastK = findLastK(scores,target,0,scores.length-1);
        if(firstK!=-1 && lastK!=-1){
            return lastK - firstK +1;
        }
        return 0;
    }

    /**
     * @param left 左下标
     * @param right 右下标
     */
    public int findFirstK(int[] nums, int target, int left, int right){
        if(right<left){
            return -1;
        }
        int mid = left + (right - left)/2;
        if(nums[mid] == target){
            if(mid == 0 || nums[mid-1] != target){
                return mid;
            }
            right = mid-1;
        }else if(nums[mid] > target) {
            right = mid-1;
        }else if(nums[mid] < target) {
            left = mid + 1;
        }
        return findFirstK(nums,target,left,right);
    }

    public int findLastK(int[] nums, int target, int left, int right){
        if(right<left){
            return -1;
        }
        int mid = left + (right - left)/2;
        if(nums[mid] == target){
            if(mid == nums.length-1 || nums[mid+1] != target){
                return mid;
            }
            left = mid+1;
        }else if(nums[mid] > target){
            right = mid-1;
        }else if(nums[mid] < target){
            left = mid+1;
        }
        return findLastK(nums,target,left,right);
    }


    public static void main(String[] args) {
        int[] nums = new int[]{2, 2};
        int num = 3;
        Solution solution = new Solution();
        System.out.println(solution.countTarget(nums,num));
        System.out.println(solution.countTargetByBinarySearch(nums,num));
    }
}
