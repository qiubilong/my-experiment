package org.example.code.sort;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;

public class 选择排序 {

    public static void main(String[] args) {
        List<Integer> nums = Lists.newArrayList(5,3,6,2,10,2,34,567,812,65,12,6,76);
        System.out.println(selectionSort(Lists.newArrayList(nums)));
        System.out.println(selectionSort1(nums));
    }

    public static List<Integer> selectionSort1(List<Integer> nums){

        for (int i = 0; i < nums.size(); i++) {
            int smallest = nums.get(i);
            int smallestIndex = i;

            for (int j = i+1; j <nums.size() ; j++) {
                Integer num = nums.get(j);
                if(num < smallest){
                    smallest = num;
                    smallestIndex = j;
                }
            }

            Integer temp = nums.get(i);
            nums.set(i, nums.get(smallestIndex));
            nums.set(smallestIndex, temp);
        }
        return nums;
    }

    public static List<Integer> selectionSort(List<Integer> nums){
        List<Integer> sortedList = new ArrayList<>(nums.size());

        int size = nums.size();
        for (int i = 0; i < size; i++) {
            int smallestIndex = findSmall(nums);
            sortedList.add(nums.get(smallestIndex));
            nums.remove(smallestIndex);
        }
       return sortedList;
    }

    public static int findSmall(List<Integer> nums){
        int smallest = nums.get(0);
        int smallestIndex = 0;
        for (int i = 0; i < nums.size(); i++) {
            Integer num = nums.get(i);
            if(num < smallest){
                smallest = num;
                smallestIndex = i;
            }
        }

        return smallestIndex;
    }
}
