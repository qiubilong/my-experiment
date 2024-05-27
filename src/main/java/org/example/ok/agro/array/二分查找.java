package org.example.ok.agro.array;

public class 二分查找 {

    public static void main(String[] args) {
        //前提：数组有序
        //复杂度：log2(n)=log(n)
        int[] nums = new int[]{1,3,4,5,7,9,18};
        System.out.println(binarySearch(nums,4));
    }

    public static int binarySearch(int[] nums,int num) {
         int low = 0, high = nums.length-1;
         int mid = (low + high)/2;
         System.out.println("low="+low+",high="+high);
         while (low <= high){
             int n = nums[mid];
             if(n == num){
                 return mid;
             } else if (n > num) {
                 high = mid -1;
             }else {
                 low = mid+1;
             }
             mid = (low + high)/2;
             System.out.println("low="+low+",high="+high);
         }
         return -1;
    }
}
