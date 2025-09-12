package org.example.ok.剑指.t15_二进制中1的个数;

/**
 * @author chenxuegui
 * @since 2024/4/22
 */
public class Solution {
    public static void main(String[] args) {

        System.out.println(Integer.toBinaryString(3));

        System.out.println(countOne(11));
        System.out.println(countOne(2147483645));
        System.out.println(countOne_逻辑与(11));
        System.out.println(countOne_逻辑与(2147483645));
    }


    public static int countOne(int n) {
        int count = 0;
        int flag = 1;
        while (flag>0){
            int value = flag & n;//只保留当前位
            if(value == flag){
                count ++;
            }
            flag = flag << 1;
        }
        return count;
    }


    public static int countOne_逻辑与(int n) {
        int count = 0;
        while (n != 0){
            int value = n & 1;//总是统计最末尾
            if(value == 1){
                count ++;
            }
            //无符号右移
            n = n >>> 1;
        }
        return count;
    }
}
