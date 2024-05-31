package org.example.ok.剑指.t10_斐波那契;

/**
 * @author chenxuegui
 * @since 2024/5/28
 *
 * https://leetcode.cn/problems/fibonacci-number/description/
 *
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 */
public class Solution {

    /** 递归计算，缺点是存在大量重复计算，效率底下 */
    public int fibo(int n){
        if( n==0 ){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        return fibo(n-1) + fibo(n-2);
    }
    int[] cache;
    public int fiboCache(int n){

        cache = new int[n+1];
        return fiboCacheDo(n);
    }
    private int fiboCacheDo(int n){
        if( n==0 ){
            cache[0] = 0;
            return 0;
        }
        if(n == 1){
            cache[1] = 1;
            return 1;
        }
        if(cache[n]!=0){
            System.out.println(cache[n]);
            return cache[n];
        }
        cache[n] = fibo(n-1) + fibo(n-2);
        return cache[n];
    }

    /** 动态规划法，从下向上计算，求解小问题再解决大问题 */
    public int fibo_动态规划(int n){
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        int[] table = new int[n+1];
        table[0] = 0;
        table[1] = 1;
        for (int i = 2; i <=n ; i++) {
            table[i] = table[i-1] + table[i-2];
        }
        return table[n];
    }


    /** 动态规划法，压缩结果数组 */
    public int fibo_动态规划2(int n){
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }
        int last1 = 1;
        int last2 = 0;
        int result = 0;
        for (int i = 2; i <= n; i++) {
            result = last1 + last2;
            System.out.print(result);
            System.out.print(",");

            last2 = last1;
            last1 = result;
        }
        return result;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.fibo(3));
        System.out.println(solution.fibo_动态规划2(3));
        System.out.println(solution.fibo_动态规划2(10));
        System.out.println(solution.fibo_动态规划(10));
        System.out.println(solution.fiboCache(10));
    }
}
