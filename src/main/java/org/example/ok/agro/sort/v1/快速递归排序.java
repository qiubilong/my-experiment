package org.example.ok.agro.sort.v1;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class 快速递归排序 {

    public static void main(String[] args) {
        ArrayList<Integer> nums = Lists.newArrayList(123, 434, 23, 3, 46, 4332, 4343, 454545);
        System.out.println(quickSort(Lists.newArrayList(nums)));
        System.out.println(nums);
        Object[] objects = nums.toArray();
        Arrays.sort(objects);
        System.out.println(Lists.newArrayList(objects));
    }

    /**快速排序
     * */
    public static List<Integer> quickSort(List<Integer> nums) {
        if(nums.size() <= 1){
            return nums;
        }else {
            int m = nums.get(0);
            List<Integer> less = new ArrayList<>(nums.size());
            List<Integer> great = new ArrayList<>(nums.size());
            for (int i = 1; i < nums.size(); i++) {
                Integer num = nums.get(i);
                if(num > m){
                    great.add(num);
                }else {
                    less.add(num);
                }
            }
            List<Integer> data = new ArrayList<>(nums.size());
            data.addAll(quickSort(less));
            data.add(m);
            data.addAll(quickSort(great));
            return data;
        }
    }
}
