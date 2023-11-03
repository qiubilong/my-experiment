package org.example.code.array;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.PriorityQueue;
import java.util.stream.Collectors;

/**
 * @author chenxuegui
 * @since 2023/10/25
 */
public class 数组第K大 {

    public static void main(String[] args) {

        System.out.println(topKByHeap(new int[]{3,2,1,5,6,4},2));
        System.out.println(topKByHeap(new int[]{3,2,3,1,2,4,5,5,6},4));
        System.out.println(topKBySort(new int[]{3,2,3,1,2,4,5,5,6},4));
    }

    public static int topKByHeap(int[] nums,int k) {
        PriorityQueue<Integer> p = new PriorityQueue<>(k);
        for (int num : nums) {
            if(p.size() < k){
                p.add(num);
            }else {
                if(p.peek() < num){
                    p.poll();
                    p.add(num);
                }
            }
        }
        System.out.println(p);

        return p.peek();
    }

    public static int topKBySort(int[] nums,int k){
        Arrays.sort(nums);
        return nums[nums.length - k];
    }
}
