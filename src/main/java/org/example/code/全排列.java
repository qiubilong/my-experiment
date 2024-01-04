package org.example.code;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2023/11/28
 */
public class 全排列 {

    public static void main(String[] args) {
        allPossible(new int[]{1,2,3});
    }

    public static void allPossible(int[] nums){
        if(nums.length == 0){
            return;
        }
        List<List<Integer>> resultAll = new ArrayList<>(10);
        dfs(nums,new boolean[nums.length],new ArrayList<>(10),resultAll);
        System.out.println(resultAll);
    }

    public static void dfs(int[] nums, boolean[] used, List<Integer> result, List<List<Integer>> resultAll){
        if(result.size() == nums.length){
            resultAll.add(new ArrayList<>(result));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if(!used[i]){
                used[i] = true;

                result.add(nums[i]);
                dfs(nums,used,result,resultAll);

                used[i] = false;
                result.remove(result.size() -1);
            }
        }
    }
}
