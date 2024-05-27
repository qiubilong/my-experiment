package org.example.ok.剑指.t4_二位数组查找;

/**
 * @author chenxuegui
 * @since 2024/5/27
 *
 * 编写一个高效的算法来搜索 m x n 矩阵 matrix 中的一个目标值 target 。该矩阵具有以下特性：
 * 每行的元素从左到右升序排列。
 * 每列的元素从上到下升序排列。
 *
 * https://leetcode.cn/problems/search-a-2d-matrix-ii/solutions/2361487/240-sou-suo-er-wei-ju-zhen-iitan-xin-qin-7mtf/
 */
public class Solution {

    /** 类搜索二叉树遍历 */
    public boolean containNum(int[][] nums,int target){
        int i = 0;
        int j = nums[0].length -1;
        while ( i<nums.length && j>=0){
            if(nums[i][j] == target){
                return true;
            }else if(nums[i][j] > target){
                j--;
            }else {
                //nums[i][j] < target
                i++;
            }
        }
        return false;
    }

    /** 每行二分查找 */
    public boolean containNum_Half(int[][] nums,int target){
        for (int i = 0; i < nums.length; i++) {
            int left = 0;
            int right = nums[0].length - 1;
            while (left<=right){
                int mid = left + (right - left)/2;
                if(nums[i][mid] == target){
                    return true;
                }else if(nums[i][mid] < target){
                    left = mid + 1;
                }else {
                    //nums[i][mid] > target
                    right = mid - 1;
                }
            }
        }

        return false;
    }

    /** 直接遍历法 */
    boolean containNum_All(int[][] nums,int target){
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums[0].length; j++) {
                if(target == nums[i][j]){
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {
        int[][] nums = new int[][]{{1,2,8,9},{2,4,9,12},{4,7,10,13},{6,8,11,15}};
        Solution solution = new Solution();
        System.out.println(solution.containNum_All(new int[][]{{-1,3}},3));
        System.out.println(solution.containNum_All(nums,7));
        System.out.println(solution.containNum_Half(nums,7));
        System.out.println(solution.containNum(nums,7));
    }
}
