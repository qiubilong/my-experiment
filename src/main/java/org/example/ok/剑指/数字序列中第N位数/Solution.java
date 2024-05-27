package org.example.ok.剑指.数字序列中第N位数;

/**
 * @author chenxuegui
 * @since 2024/5/10
 * https://leetcode.cn/problems/shu-zi-xu-lie-zhong-mou-yi-wei-de-shu-zi-lcof/description/
 * https://leetcode.cn/problems/nth-digit/description/
 * https://blog.csdn.net/qq_21370419/article/details/131011300
 */
/* 数字范围    数量  位数    占多少位
    1-9        9      1       9
    10-99      90     2       180
    100-999    900    3       2700
    1000-9999  9000   4       36000  ...

    例如 2901 = 9 + 180 + 2700 + 12 即一定是4位数,第12位   n = 12;
    数据为 = 1000 + (12 - 1)/ 4  = 1000 + 2 = 1002
    定位1002中的位置 = (n - 1) %  4 = 3    s.charAt(3) = 2;
*/
class Solution {
    public static int findNthDigit(int n) {
        int digit = 1;   // n所在数字的位数
        long start = 1;  // 数字范围开始的第一个数
        long count = 9;  // 占多少位
        while(n > count){
            n -= count;
            digit++;
            start *= 10;
            count = digit * start * 9;
        }
        long num = start + (n - 1) / digit;
        return Long.toString(num).charAt((n - 1) % digit) - '0';
    }

    /**
     * n=10000000太大时，超出内存
     * */
    public static int findNthDigitStr(int n) {
        if(n<=0){
            return n;
        }
        StringBuilder sb = new StringBuilder(100);
        for (int i = 0; i <= n; i++) {
            sb.append(i);
            if(sb.length()>n){
                break;
            }
        }
        //System.out.println(sb.toString());
        return sb.charAt(n) - '0';
    }

    public static void main(String[] args) {
        System.out.println(findNthDigit(11));
        System.out.println(findNthDigitStr(11));
        System.out.println(findNthDigitStr(10000000));
        System.out.println(findNthDigit(3));
        System.out.println(findNthDigitStr(3));
    }
}
