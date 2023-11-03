package org.example.code.sort;

import com.google.common.collect.Lists;

import java.util.List;

public class 递归 {

    public static void main(String[] args) {
        List<Integer> nums = Lists.newArrayList(1,2,3,4,5,6,7,8,9);
        System.out.println(sum(Lists.newArrayList(nums)));
        System.out.println(count(Lists.newArrayList(nums)));
        System.out.println(findMax(Lists.newArrayList(1,121,5,7,88,3323,323,1)));
    }

    /**求和
     * */
    public static int sum(List<Integer> nums){
        if(nums.size() == 1){
            return nums.get(0);
        }else {
            int last = nums.size() - 1;
            int num =  nums.get(last);
            nums.remove(last);
            return num + sum(nums);
        }
    }

    /**包含元素数量
     * */
    public static int count(List<Integer> nums){
        if(nums.size() == 1){
            return 1;
        }else {
            int last = nums.size() - 1;
            nums.remove(last);
            return 1 + count(nums);
        }
    }

    /** 找最大值 */
    public static int findMax(List<Integer> nums){
        if(nums.size() == 1){
            return nums.get(0);
        }else {
            int last = nums.size() - 1;
            int num =  nums.get(last);
            nums.remove(last);
            int f = findMax(nums);
            if(f > num){
                return f;
            }else{
                return num;
            }
        }
    }
}
