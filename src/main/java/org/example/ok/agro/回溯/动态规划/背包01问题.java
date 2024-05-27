package org.example.ok.agro.回溯.动态规划;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author chenxuegui
 * @since 2024/1/22
 * 问题描述：容量为capacity的背包，装载n件物品，使其价值最大
 * 定义： dp[i][j]=容量为j时，选择前i件物品，价值最大
 *
 */
public class 背包01问题 {
    static int[] weight  = new int[] {1, 3, 4};

    static int[] values = new int[] {15, 20, 30};

    static int capacity = 4;

    static AtomicInteger counter = new AtomicInteger();

    public static void main(String[] args) {


        int best = bestBag(values, weight, capacity);

        System.out.println(best);

        System.out.println(bestBagRecursion(values, weight, capacity));
        System.out.println("counter="+counter.get());
    }


    public static int bestBag(int[] values,int[] weights, int capacity){
        int n = values.length;
        int[][] dp = new int[n+1][capacity+1];

        /*
         * 状态初始化，不选物品和背包容量为0时，价值为0
         * dp[0][..] = dp[..][0] = 0
         * java int默认为0，故不需要初始化
         */
        Map<Integer, Set<Integer>> dpMap = new HashMap<>(16);

        //遍历物品
        for (int i = 1; i <= n; i++) {
            //遍历容量
            for (int j = 1; j <= capacity; j++) {

                //第n件物品信息
                int weight = weights[i - 1];
                int value = values[i - 1];

                //装不下
                if(j < weight){
                    dp[i][j] = dp[i -1][j];

                    continue;
                }
                //装得下,max（不选，选择）
                dp[i][j] = Math.max(dp[i -1][j], dp[i -1][j - weight] + value );

                //保存对应元素
                if(dp[i][j] != dp[i -1][j]){
                    //选择
                    Set<Integer> set = dpMap.computeIfAbsent(dp[i - 1][j - weight],key-> new HashSet<>());
                    HashSet<Integer> set1 = new HashSet<>(set);
                    set1.add(i);
                    dpMap.put(dp[i][j],set1);
                }
            }
        }
        System.out.println(Arrays.deepToString(dp).replaceAll("], ", "]\n"));
        System.out.println(dpMap);

        //背包容量为capacity时，前n件物品，价值最大
        return dp[n][capacity];
    }


    public static int bestBagRecursion(int[] weight, int[] value, int capacity) {
        int n = weight.length;
        return bestBagRecursionDo(weight, value,  capacity,n-1);
    }

    public static int bestBagRecursionDo(int[] values,int[] weights, int capacity, int index){
        counter.getAndIncrement();
        if(index == 0){
            return capacity>= weights[index] ? values[index]: 0;
        }
        //不选
        int notTake = bestBagRecursionDo(values,weights,capacity,index-1);
        //选择
        int take = 0;
        if(capacity >= weights[index]){
            take = bestBagRecursionDo(values,weights,capacity - weights[index],index-1) + values[index];
        }
        return Math.max(take, notTake);
    }
}
