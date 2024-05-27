package org.example.ok.剑指.最大价值礼物;

/**
 * https://blog.csdn.net/qq_42958831/article/details/129395533
 *
 * https://mp.weixin.qq.com/s/fP59Pc7gMCO3InaCrFvo6A(一维数组)
 *
 * 礼物最大价值，动态规划
 */

class Solution {
    public int maxValue(int[][] frame) {
        int rows = frame.length;
        if(rows==0){
            return 0;
        }
        int cols = frame[0].length;
        if(cols==0){
            return 0;
        }

        int [][] dp = new int[rows][cols];

        //从左上角开始
        dp[0][0] = frame[0][0];
        //第一列
        for (int i = 1; i < rows; i++) {
            dp[i][0] =  dp[i-1][0] + frame[i][0];
        }
        //第一行
        for (int j = 1; j < cols; j++) {
            dp[0][j] = dp[0][j-1] + frame[0][j];
        }

        for (int i = 1; i < rows; i++) {
            for (int j = 1; j < cols; j++) {
                dp[i][j] = Math.max(dp[i-1][j], dp[i][j-1]) + frame[i][j];
            }
        }
        return dp[rows-1][cols-1];
    }

    public static void main(String[] args) {
        int [][] frame = new int[][] {{1,3,1},{1,5,1},{4,2,1}};

        int [][] frame1 = new int[][]{{},{},{}};

        Solution solution = new Solution();
        System.out.println(solution.maxValue(frame));
        System.out.println(solution.maxValue(frame1));
    }
}