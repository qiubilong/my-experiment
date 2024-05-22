package org.example.剑指.t56_数组中出现数字的次数.只出现1次的两个数字;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/5/21
 *
 * https://leetcode.cn/problems/single-number-iii/description/
 */
public class Solution {

    public int[] singleNum(int[] nums){

        if(nums.length<2){
            return new int[]{};
        }
        int orSum = 0;
        for (int i = 0; i < nums.length; i++) {
            orSum ^= nums[i];
        }
        //右边起第几位是1
        int indexOne = 0;
        int one = 1;
        while ((orSum & one) == 0){
            one = one<<1;
            indexOne++;
        }

        System.out.println("orSum="+orSum +","+Integer.toBinaryString(orSum));
        System.out.println("indexOne="+indexOne+","+Integer.toBinaryString(indexOne));
        int[] twoNums = new int[]{0,0};
        List<Integer> ints1 = new ArrayList<>(50);
        List<Integer> ints2 = new ArrayList<>(50);
        for (int i = 0; i < nums.length; i++) {
            if(((nums[i] >> indexOne) & 1) == 0){
                twoNums[0] ^= nums[i];
                ints1.add(nums[i]);
            }else {
                twoNums[1] ^= nums[i];
                ints2.add(nums[i]);
            }
        }
        System.out.println(ints1);
        System.out.println(ints2);
        return twoNums;
    }

    public int[] singleNumber(int[] nums) {
        System.out.println("---------------");
        int[] result=new int[2];
        //1、先把所有的数字异或一次，得到的是要找的两个数的异或
        int res=0;
        for(int num:nums)
            res ^= num;

        System.out.println(res);
        //2、找到异或结果中第一个不为0的位
        int index=0;
        while(index<32){
            if(((res>>index)&1)==1)
                break;
            index++;
        }
        System.out.println(index);

        //3、按照第index位是0还是1分别异或
        for(int num:nums){
            if(((num>>index)&1)==1)
                result[0] ^= num;
            else
                result[1] ^= num;
        }
        return result;
    }


    public static void main(String[] args) {
        int[] nums = new int[]{1,2,1,3,2,5};
        Solution solution = new Solution();
        //System.out.println(Arrays.toString(solution.singleNum(nums)));
        int[] nums2 = new int[]{1193730082,587035181,-814709193,1676831308,-511259610,284593787,-2058511940,1970250135,-814709193,-1435587299,1308886332,-1435587299,1676831308,1403943960,-421534159,-528369977,-2058511940,1636287980,-1874234027,197290672,1976318504,-511259610,1308886332,336663447,1636287980,197290672,1970250135,1976318504,959128864,284593787,-528369977,-1874234027,587035181,-421534159,-786223891,933046536,959112204,336663447,933046536,959112204,1193730082,-786223891};
        //System.out.println(Arrays.toString(solution.singleNum(nums2)));

        int[] num3 = new int[]{-1638685546,-2084083624,-307525016,-930251592,-1638685546,1354460680,623522045,-1370026032,-307525016,-2084083624,-930251592,472570145,-1370026032,1063150409,160988123,1122167217,1145305475,472570145,623522045,1122167217,1354460680,1145305475};
        System.out.println(Arrays.toString(solution.singleNum(num3)));

        System.out.println(Integer.toBinaryString(1063150409));
        System.out.println(Integer.toBinaryString(160988123));

        //System.out.println(Arrays.toString(solution.singleNumber(nums2)));


    }
}
