package org.example.code.array;

import com.google.common.collect.Lists;

import java.util.*;

/**
 * @author chenxuegui
 * @since 2023/10/27
 */
public class 三数之和 {

    public static void main(String[] args) {
        System.out.println(threeSum(new int[]{-1,0,1,2,-1,-4}));
        System.out.println(threeSum(new int[]{-2,0,0,2,2}));
        System.out.println(threeSumN3(new int[]{-1,0,1,2,-1,-4}));
    }


    public static List<List<Integer>> threeSum(int[] nums) {
        List<List<Integer>> list = new ArrayList<>(10);
        if(nums.length<3){
            return list;
        }

        Arrays.sort(nums);
        int left = 0;
        int right = 0;
        int target = 0;
        for (int i = 0; i < nums.length; i++) {
            if(nums[i]>0){
                break;
            }
            if(i>0 && (nums[i] == nums[i-1])){
                continue;
            }

            target = -nums[i];
            left = i+1;
            right = nums.length -1;
            while (left < right) {
                int sum = nums[left]+ nums[right];

                if(sum == target){

                    list.add(Arrays.asList(nums[i],nums[left], nums[right]));
                    while (left < right && nums[left] == nums[left+1]){
                        left ++;
                    }
                    while (left < right && nums[right] == nums[right -1]){
                        right--;
                    }
                    left ++;
                    right--;
                }else if(sum < target){
                    left++;
                }else {
                    right--;
                }
            }
        }
        return list;
    }


    /**
     * 时间复杂度n3
     * */
    public static List<List<Integer>> threeSumN3(int[] nums) {
        Map<String,List<Integer>> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            for (int i1 = i+1; i1 < nums.length; i1++) {
                for (int i2 = i1+1; i2 < nums.length; i2++) {
                    if(nums[i] + nums[i1] + nums[i2] == 0){
                        List<Integer> list = Arrays.asList(nums[i], nums[i1], nums[i2]);
                        String listKey = listKey(list);
                        map.putIfAbsent(listKey, list);
                    }
                }
            }
        }

        return new ArrayList<>(map.values());
    }
    private static String listKey(List<Integer> list){
        Collections.sort(list);
        StringBuilder builder = new StringBuilder();
        list.stream().forEach(builder::append);
        return builder.toString();
    }
}
