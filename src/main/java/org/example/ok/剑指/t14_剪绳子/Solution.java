package org.example.ok.剑指.t14_剪绳子;


/**
 *
 * 现需要将一根长为正整数 bamboo_len 的竹子砍为若干段，每段长度均为正整数。请返回每段竹子长度的最大乘积是多少。
 * 2 <= bamboo_len <= 58
 * @author chenxuegui
 * @since 2024/5/28
 * https://leetcode.cn/problems/jian-sheng-zi-lcof/description/
 * https://leetcode.cn/problems/jian-sheng-zi-lcof/solutions/629684/jian-zhi-offer-14-i-jian-sheng-zi-huan-s-xopj/
 */
public class Solution {

    public int maxCut(int n){
        if(n<=1){
            throw new IllegalArgumentException();
        }
        int[] table = new int[n+1];
        table[2] = 1;
        for (int i = 3; i <=n ; i++) {
            // 3*2=2*3,所以i/2
            // j=2开始
            for (int j = 1; j <=i/2 ; j++) {
                //减去j,剩余 i-j
                //i-j继续减取table[i-j]
                int temp = Math.max(j * (i-j),j * table[i-j]);
                //更新当前最大值
                table[i] = Math.max(table[i],temp);
            }
        }

        return table[n];
    }

    public int maxCut2(int n){
        if(n<=1){
            throw new IllegalArgumentException();
        }
        if(n == 2){
            return 1;
        }
        if( n== 3){
            return 2;
        }
        if( n== 4){
            return 4;
        }
        int result = 1;
        while (n >4){
            n -= 3;
            result *= 3;
        }
        return result * n;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxCut(8));
        System.out.println(solution.maxCut2(8));
    }
}
