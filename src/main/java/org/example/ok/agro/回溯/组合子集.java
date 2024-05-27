package org.example.ok.agro.回溯;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/1/24
 * n叉树的前序遍历框架
 * 利用元素的相对顺序实现不重复
 * https://mp.weixin.qq.com/s/nrTpZ9b9RvfNsaEkJoHMvg
 */
public class 组合子集 {
    static List<List<Integer>> all = new ArrayList<>();


    public static void main(String[] args) {

        int[] nums = {1,2,3};
        combine(nums);

        System.out.println(all);
    }

    public static void combine(int [] nums){
        List<Integer> track = new ArrayList<>();
        combineDo(nums,0,track);
    }

    public static void combineDo(int [] nums, int start, List<Integer> track){
        all.add(new ArrayList<>(track));

        for (int i = start; i < nums.length; i++) {
            track.add(nums[i]);

            //利用start控制分支，避免重复
            combineDo(nums,i+1,track);

            track.remove((Integer) nums[i]);
        }
    }
}
