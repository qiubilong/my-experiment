package org.example.剑指.数字翻译成字符串;

import java.util.ArrayList;
import java.util.List;

/**
 * https://blog.csdn.net/baichoufei90/article/details/106637584
 * 
 */

public class Solution_输出不同字符串 {
    public static int crackNumber(int ciphertext) {
        if(ciphertext<10){
            return 1;
        }
        String text = String.valueOf(ciphertext);

        int dp[] = new int[text.length()+1];
        //当前两个数字能组合翻译时，dp[2] = dp[1] + dp[0],
        //d[2]结果应该是2，dp[1]已知等于1，所以dp[0]也等于1
        dp[0] = 1;
        //一个数字只有一种翻译方法
        dp[1] = 1;
        for (int i = 2; i <= text.length(); i++) {
            String str = text.substring(i-2,i);
            if(str.compareTo("10")>=0 && str.compareTo("25")<=0){
                dp[i] = dp[i-1] + dp[i-2];
            }else {
                //否则是单独翻译
                dp[i] = dp[i-1];
            }
        }

        return dp[text.length()];
    }

    private int count;
    private List<String> result = new ArrayList<>();
    public int backtrack(int num) {
        if (num<=9) {
            this.count = 1;
            return this.count;
        }

        backtrackDo(num+"",0, "");
        return this.count;
    }

    private  void backtrackDo(String numText, int index,String current) {
        if (index == numText.length() -1 ) {
            this.count ++ ;
            this.result.add(current);
            return;
        }
        String currentNew = current +" "+ numText.charAt(index);
        backtrackDo(numText,index+1, currentNew);

        String sub = numText.substring(index, index + 2);
        int two = Integer.parseInt(sub);
        if(two>9 && two < 26){
            String currentNew2 = current +" "+ sub;
            backtrackDo(numText,index+2,currentNew2);
        }
    }



    private  void backtrack_不分割字符(int num) {
        if (num == 0) {
            this.count ++ ;
            return;
        }

        //选择一个
        backtrack_不分割字符(num/10);

        int two = num % 100;
        if(two > 9 && two < 26){
            //选择两个
            backtrack_不分割字符(two);
        }
    }




    public static void main(String[] args) {
        int num = 12258;
        System.out.println(crackNumber(num));

        Solution_输出不同字符串 solution = new Solution_输出不同字符串();
        System.out.println(solution.backtrack(num));
        System.out.println(solution.result);

        Solution_输出不同字符串 solution1 = new Solution_输出不同字符串();
        solution1.backtrack(num);
        System.out.println(solution1.count);
    }
}