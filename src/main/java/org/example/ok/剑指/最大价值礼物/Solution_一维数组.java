package org.example.ok.剑指.最大价值礼物;

/**
 * https://blog.csdn.net/qq_42958831/article/details/129395533
 *
 * https://mp.weixin.qq.com/s/fP59Pc7gMCO3InaCrFvo6A(一维数组)
 *
 * 礼物最大价值，动态规划
 */

class Solution_一维数组 {
    public int maxValue(int[][] frame) {
        int rows = frame.length;
        if(rows==0){
            return 0;
        }
        int cols = frame[0].length;
        if(cols==0){
            return 0;
        }

        int [] dp = new int[cols + 1];


        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                dp[j+1] = Math.max(dp[j], dp[j+1]) + frame[i][j];
            }
        }
        return dp[cols];
    }

    public static void main(String[] args) {
        int [][] frame = new int[][] {{1,3,1},{1,5,1},{4,2,1}};

        Solution_一维数组 solution = new Solution_一维数组();
        System.out.println(solution.maxValue(frame));
    }
}