package org.example.code.sort;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * https://developer.aliyun.com/article/1318542
 */
public class SortTest {



    public static void main(String[] args) {

        int[] nums = new int[]{3,2,1,5,6,4};
        System.out.println(findKthLargest(nums.clone(),2));
        xuanze(nums);
        maopao(nums.clone());
        charu(nums.clone());
        topK(nums.clone());
        topKFrequent(new int[]{5,5,6,6,6,1,1,1,1,3,3,2,2,2},2);
    }

    public static void topKFrequent(int[] nums,int k){
        HashMap<Integer, Integer> shows = new HashMap<>(16);
        for (int num : nums) {
            shows.compute(num,(key,v)->{
                if(v==null) {
                    v = 0;
                }
                return v+1;
            });
        }

        List<Integer> counts = new ArrayList<>(shows.values());
        counts.sort((o1, o2) -> o2 -o1);

        int min = counts.get(k-1);
        List<Integer> result = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : shows.entrySet()) {
            if(entry.getValue() >= min && result.size()<k){
                result.add(entry.getKey());
            }
        }

        System.out.println(shows);
        System.out.println(result);
    }

    public static void topK(int[] nums){
        int k = 3;
        PriorityQueue<Integer> minHeap = new PriorityQueue<>(8, (a, b) -> b-a);
        for (int num : nums) {
            minHeap.add(num);
            if (minHeap.size() >k) {
                minHeap.poll();
            }
        }
        System.out.println(minHeap);
    }

    public static int findKthLargest(int[] nums, int k) {
        Arrays.sort(nums);
        return nums[nums.length - k];
    }

    public static void xuanze(int[] nums){
        for (int i = 0; i < nums.length; i++) {
            for (int j = i+1; j < nums.length; j++) {
                if(nums[i]>nums[j]){
                    int temp = nums[i];
                    nums[i] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
    }

    public static void maopao(int[] nums){
        for (int i = 0; i < nums.length; i++) {
            for (int j = 0; j < nums.length-i-1; j++) {
                if(nums[j] > nums[j+1]){
                    int temp = nums[j + 1];
                    nums[j + 1] = nums[j];
                    nums[j] = temp;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
    }


    public static void charu(int[] nums){
        for (int i = 1; i < nums.length; i++) {
            for (int j = i; j >0; j--) {
                if(nums[j] < nums[j-1]){
                    int temp = nums[j];
                    nums[j] = nums[j-1];
                    nums[j-1] = temp;
                }else {
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(nums));
    }
}
