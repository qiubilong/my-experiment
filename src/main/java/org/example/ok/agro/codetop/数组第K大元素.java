package org.example.ok.agro.codetop;

import java.util.PriorityQueue;

/**
 * @author chenxuegui
 * @since 2024/4/17
 */
public class 数组第K大元素 {

    public static void main(String[] args) {

        int[] nums = new int[]{3,2,1,5,6,4};
        System.out.println(findKthLargest(nums,2));
    }

    public static int findKthLargest(int[] nums, int k) {
        if(nums ==null || nums.length == 0){
            throw new IllegalArgumentException();
        }
        if(nums.length < k){
            throw new IllegalArgumentException();
        }
        PriorityQueue<Integer> queue = new PriorityQueue<>();
        for (int num : nums) {
            if(queue.size() < k){
                queue.add(num);
            }else {
                int peek = queue.peek();
                if(peek<num){
                    queue.poll();
                    queue.add(num);
                }
            }
        }

        return queue.peek();
    }
}
