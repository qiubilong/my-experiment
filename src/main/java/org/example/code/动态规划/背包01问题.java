package org.example.code.动态规划;

/**
 * @author chenxuegui
 * @since 2024/1/22
 */
public class 背包01问题 {


    public static void main(String[] args) {
        int[] weight  = new int[] {1, 3, 4};

        int[] values = new int[] {15, 20, 30};

        int capacity = 4;

        int best = bestBag(values, weight, capacity);

        System.out.println(best);
    }


    public static int bestBag(int[] values,int[] weights, int capacity){
        int n = values.length;
        int[][] dp = new int[n+1][n+1];

        for (int i = 1; i <= n; i++) {
            for (int j = 0; j <= capacity; j++) {

                //装不下
                if(j < weights[i]){
                    dp[i][j] = dp[i -1][j];
                    continue;
                }
                //装得下
                dp[i][j] = Math.max(dp[i -1][j], dp[i -1][j - weights[i]] + values[i] );
            }
        }

        return dp[n][capacity];

    }
}
