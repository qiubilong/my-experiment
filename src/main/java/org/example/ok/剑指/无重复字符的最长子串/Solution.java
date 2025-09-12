package org.example.ok.剑指.无重复字符的最长子串;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chenxuegui
 * @since 2024/5/20
 *
 * https://leetcode.cn/problems/longest-substring-without-repeating-characters/description/
 */
public class Solution {

    public int maxSubString(String s){
        int max = 0;
        if(s==null || s.length()==0){
            return max;
        }
        //简单hash表
        int[] asc = new int[128];
        for (int i = 0; i < asc.length; i++) {
            asc[i] = -1;
        }

        int left = 0;
        int right = 0;

        for (;right < s.length(); right++ ){ // "abcabcbb"
            int ch = s.charAt(right);
            int index = asc[ch];
            if(index>=0){
                left = Math.max(index + 1,left);
            }
            max = Math.max(right - left +1, max);
            asc[ch] = right;
        }
        return max;
    }

    public int lengthOfLongestSubstring(String s) {
        if(s==null || s.length() == 0){
            return 0;
        }
        Map<Character,Integer> map = new HashMap<>();
        int maxSize = 0;
        for (int left = 0,right = 0; right < s.length(); right++) {

            if(map.containsKey(s.charAt(right))){
                left =  Math.max(map.get(s.charAt(right))+1,left);
            }
            map.put(s.charAt(right),right);
            maxSize = Math.max(maxSize,right-left+1);

        }
        return  maxSize;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.maxSubString("abcabcbb"));
    }
}
