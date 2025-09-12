package org.example.ok.剑指.最小的k个数;

import java.util.Arrays;
import java.util.PriorityQueue;

/**
 * https://leetcode.cn/problems/smallest-k-lcci/description/
 */
class Solution {
    public int[] smallestK(int[] arr, int k) {
        if(k == 0 ){
            return new int[]{};
        }
        if( k < 0 || arr.length < k ){
            return null;
        }
        //大顶堆
        PriorityQueue<Integer> queue = new PriorityQueue<>(k, (num1, num2) -> num2 - num1);
        for (int i = 0; i < k; i++) {
            queue.add(arr[i]);
        }
        for (int i = k; i < arr.length; i++) {
            Integer peek = queue.peek();
            if(arr[i] < peek){//相当于删掉最大
                queue.poll();
                queue.add(arr[i]);
            }
        }

        return queue.stream().mapToInt(v-> v).toArray();
    }

    public static void main(String[] args) {
        int[] arr = new int[]{1,3,5,7,2,4,6,8};
        int k = 4;
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.smallestK(arr, k)));

    }
}