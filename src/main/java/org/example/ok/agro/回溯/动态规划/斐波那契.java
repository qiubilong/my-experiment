package org.example.ok.agro.回溯.动态规划;

import java.util.Arrays;

/**
 * @author chenxuegui
 * @since 2024/1/22
 *  兔子繁殖
 * F(0) = 0，F(1) = 1
 * F(n) = F(n - 1) + F(n - 2)，其中 n > 1
 * 数列 = 0、1、 1、 2、 3、 5、 8、 13、 21、 34、 55、 89、 144、 233、 377、 610
 */
public class 斐波那契 {

    public static void main(String[] args) {
        //fib(5);
        fibo(5);
    }

    //递归求解fib
    public static int fib(int n){
        if(n <=1 ){
            return n;
        }
        int f = fib(n-1) + fib(n-2);
        System.out.println(f+"、");
        return f;
    }

    //动态规划求解fib
    public static int fibo(int n){
        if(n<=1){
            return n;
        }

        int[] dp = new int[n+1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= n; i++) {
            dp[i] = dp[i-1] + dp[i-2];

        }
        System.out.println(Arrays.toString(dp));
        return dp[n];
    }
}
