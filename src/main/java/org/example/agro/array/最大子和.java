package org.example.agro.array;

/**
 * @author chenxuegui
 * @since 2023/10/30
 */
public class 最大子和 {

    public static void main(String[] args) {
        System.out.println(maxSubArray(new int[]{2,1,-3,4,-1,2,1,-5,4}));
        System.out.println(maxSubArray(new int[]{-1,-2}));
        System.out.println(maxSubArray2(new int[]{2,1,-3,4,-1,2,1,-5,4}));
        System.out.println(maxSubArray2(new int[]{-1,-2}));
        System.out.println(maxSubArray2(new int[]{-1,0,-2}));

    }

    public static int maxSubArray(int[] nums){
        int max = nums[0];
        int subMax = nums[0];
        int  i = 1;
        for (;  i< nums.length; i++) {
            //子序列增益
            if(subMax > 0){
                subMax = subMax + nums[i];
            }else {
               subMax = nums[i];
           }
            max = Math.max(max,subMax);
        }
        return max;
    }

    /** 暴力解法 */
    public static int maxSubArray2(int[] nums){
        int max = nums[0];
        for (int i = 0; i < nums.length; i++) {
            int sum = nums[i];
            max = Math.max(max,sum);
            for (int j = i+1; j < nums.length; j++) {
                sum = sum + nums[j];
                max = Math.max(max,sum);
            }
        }
        return max;
    }
}
