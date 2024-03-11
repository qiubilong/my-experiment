package org.example.agro.回溯;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/1/24
 * N叉树前序遍历框架法、暴力遍历、无重复子解
 * https://mp.weixin.qq.com/s/nrTpZ9b9RvfNsaEkJoHMvg
 */
public class 全排列 {

    static List<List<Integer>> all = new ArrayList<>();
    static boolean[] used;

    public static void main(String[] args) {

        int[] nums = {1,2,3};
        allChoices(nums);

        System.out.println(all);
    }

    public static void allChoices(int[] nums) {
        used = new boolean[nums.length];
        allChoicesDo(nums,new LinkedList<>());
    }

    public static void allChoicesDo(int[] nums,  LinkedList<Integer> trace){
        //到达叶子节点
        if(trace.size() == nums.length){
            all.add(new ArrayList<>(trace));
            return;
        }

        for (int i = 0; i < nums.length; i++) {
            if(used[i]){
                continue;
            }
            //选择
            trace.add(nums[i]);
            used[i] = true;

            //下一层
            allChoicesDo(nums,trace);

            //撤销
            trace.removeLast();
            used[i] = false;
        }
    }
}
