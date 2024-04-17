package org.example.agro.string;

import com.google.common.collect.Lists;

import java.util.*;

public class 最大不同子串 {

    public static void main(String[] args) {
         List<String> tests = Lists.newArrayList("abcabcbb","bbbbb","pwwkew"," ","aab","abba");
         System.out.println(maxDifferent("abcabcbb"));
         System.out.println(maxDifferent("bbbbb"));
         System.out.println(maxDifferent("pwwkew"));
         System.out.println(maxDifferent(" "));
         System.out.println(maxDifferent("aab"));

        System.out.println("======================");


        for (String s : tests) {
            System.out.println(maxDifferentBySet(s));
        }
        System.out.println("======================");
        for (String s : tests) {
            System.out.println(maxDifferentByMap(s));
        }

        System.out.println("======================");
        for (String s : tests) {
            System.out.println(maxDifferentByCharIndex(s));
        }
    }

    /**
     * 利用map记录字符位置
     * */
    public static int maxDifferentByMap(String s){
        if(s==null || s.length() == 0){
            return 0;
        }
        Map<Character,Integer>  map = new HashMap<>();
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

    /**
     * 利用数组记录字符位置
     * */
    public static int maxDifferentByCharIndex(String s){
        if(s==null || s.length() == 0){
            return 0;
        }
        int[] charIndex = new int[128];
        int maxSize = 0;
        for (int left = 0,right = 0; right < s.length(); right++) {
            char c = s.charAt(right);
            left =  Math.max(charIndex[c],left);
            maxSize = Math.max(maxSize,right-left+1);

            charIndex[c] = right + 1;

        }
        return  maxSize;
    }



    public static int maxDifferentBySet(String s){
        if(s == null || s.length() == 0){
            return 0;
        }
        int maxSize = 0;
        char[] chars = s.toCharArray();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < chars.length; ) {
            if(!set.contains(chars[i])){
                set.add(chars[i]);
                i++;
            }else {
                if(set.size() > maxSize){
                    maxSize = set.size();
                }
                i = i - set.size() + 1;
                set = new HashSet<>();
            }
        }
        if(set.size() > maxSize){
            maxSize = set.size();
        }
        return maxSize;

    }

    /**
     * 利用set实现
     * */
    public static int maxDifferent(String s){
        if(s == null || s.length() == 0){
            return 0;
        }
        char[] chars = s.toCharArray();
        Set<Character> setMax = new HashSet<>();
        Set<Character> set = new HashSet<>();
        for (int i = 0; i < chars.length; ) {
            if(!set.contains(chars[i])){
                set.add(chars[i]);
                i++;
            }else {
                if(set.size() > setMax.size()){
                    setMax = set;
                }
                i = i - set.size() + 1;
                set = new HashSet<>();
            }
        }
        if(set.size() > setMax.size()){
            setMax = set;
        }
        System.out.println(setMax);
        return setMax.size();

    }
}
