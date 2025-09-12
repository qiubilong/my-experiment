package org.example.ok.剑指.t53_在排序数组中查找数字.排序数组中缺失的数字;

/**
 * @author chenxuegui
 * @since 2024/5/22
 * https://leetcode.cn/problems/que-shi-de-shu-zi-lcof/description/
 * https://www.acwing.com/solution/content/229976/
 * 一个长度为n-1的递增排序数组中的所有数字都是唯一的，并且每个数字都在范围0到n-1之内。在范围0到n-1的n个数字中有且只有一个数字不在该数组中，请找出这个数字
 * 输入: [0,1,3] 输出: 2 示例 2:
 * 输入: [0,1,2,3,4,5,6,7,9] 输出: 8
 * https://zhxiongfei.github.io/post/%E5%89%91%E6%8C%87offer53.%E7%BC%BA%E5%A4%B1%E7%9A%84%E6%95%B0%E5%AD%97/
 */
public class Solution {

    //线性遍历求和法
    public int missNum(int[] records){
        if(records.length ==0){
            throw new IllegalArgumentException();
        }
        int n = records.length ;
        int total = n*(n+1)/2;
        for (int i = 0; i < records.length; i++) {
            total -= records[i];
        }
        return total;
    }
    //线性遍历法
    public int getMissNum(int[] records){
        if(records.length ==0){
            throw new IllegalArgumentException();
        }
        // 检查第一个元素是否为1，如果是，则缺失的数字是0
        if(records[0] == 1){
            return 0;
        }
        for (int i = 0; i < records.length; i++) {
            if(i != records[i]){
                return i;
            }
        }
        // 如果没有缺失的数字，则缺失的是最后一个数字
        return records.length;
    }

    //二分法查找
    public int findMissNum(int[] records){
        if(records.length==0){
            throw new IllegalArgumentException();
        }

        int num = findMissNumBinary(records,0,records.length-1);
        return num;
    }
    //寻找第一个数字大于索引的数
    public int findMissNumBinary(int[] nums,int left,int right){
        if(left > right){
            //如果没有缺失的数字，则缺失的是最后一个数字
            return left;
        }
        int mid = left + (right-left)/2;
        if(nums[mid] != mid) {
            if(mid==0 || nums[mid-1] == mid -1){
                return mid;
            }
            right = mid -1;
        }else {
            left = mid +1;
        }
        return findMissNumBinary(nums,left,right);
    }

    public int findMissNum2(int[] records){
        if(records.length==0){
            return -1;
        }
        int left = 0;
        int right = records.length -1;
        while (left<=right){
            int mid = left + (right-left)/2;
            if(records[mid] != mid) {
                if(mid ==0 || records[mid-1] == mid -1){
                    return mid;
                }
                right = mid -1;
            }else {
                left = mid +1;
            }
        }
        return left;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{0,1,2,3,5};
        Solution solution = new Solution();
        System.out.println(solution.missNum(nums));
        System.out.println(solution.missNum(new int[]{0}));
        System.out.println(solution.findMissNum(nums));
        System.out.println(solution.findMissNum(new int[]{1}));
        System.out.println("findMissNum2");
        System.out.println(solution.findMissNum2(new int[]{0}));
        System.out.println(solution.findMissNum2(new int[]{0,1,2,3}));
        System.out.println(solution.findMissNum2(new int[]{0, 1, 2, 3, 4, 5, 6, 8}));
        System.out.println(solution.getMissNum(new int[]{0, 1, 2, 3, 4, 5, 6, 8}));
    }
}
