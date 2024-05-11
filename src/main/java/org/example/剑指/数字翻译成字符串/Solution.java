package org.example.剑指.数字翻译成字符串;

public class Solution {
    public static int crackNumber(int ciphertext) {
        if(ciphertext<0){
            return 0;
        }
        String text = String.valueOf(ciphertext);
        if(text.length() == 1){
            return 1;
        }
        int dp[] = new int[text.length()+1];
        dp[0] = 1;
        dp[1] = 1;
        for (int i = 2; i <= text.length(); i++) {
            String str = text.substring(i-2,i);
            if(str.compareTo("10")>=0 && str.compareTo("25")<=0){
                dp[i] = dp[i-1] + dp[i-2];
            }else {
                dp[i] = dp[i-1];
            }
        }

        return dp[text.length()];
    }

    public static void main(String[] args) {
        System.out.println(crackNumber(12258));
    }
}