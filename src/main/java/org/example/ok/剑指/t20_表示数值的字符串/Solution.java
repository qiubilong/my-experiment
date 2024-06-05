package org.example.ok.剑指.t20_表示数值的字符串;

/**
 * @author chenxuegui
 * @since 2024/6/5
 *https://leetcode.cn/problems/biao-shi-shu-zhi-de-zi-fu-chuan-lcof/solutions/278913/mian-shi-ti-20-biao-shi-shu-zhi-de-zi-fu-chuan-y-2/
 * A[.B][e|EA]
 * .B[e|EA]
 */
public class Solution {

    private int index;

    public boolean validNumber(String s){
        if(s==null){
            return false;
        }
        s = s.trim();
        if(s.length() == 0){
            return false;
        }
        char[] chars = s.toCharArray();
        boolean isNum = scanNumber(chars);

        if(index< chars.length && chars[index] == '.'){
            this.index++;
            boolean numAfterDot = scanUnsignNumber(chars);
            isNum = isNum || numAfterDot;
        }
        if(index< chars.length && (chars[index] == 'e' || chars[index] == 'E')){
            this.index++;
            boolean numAfterE = scanNumber(chars);

            isNum = isNum && numAfterE;
        }
        return isNum && index == chars.length;
    }

    private boolean scanUnsignNumber(char[] chars){
        int start = index;
        while (this.index < chars.length && chars[index]>='0' &&  chars[index]<='9'){
            this.index++;
        }
        return index > start;
    }

    private boolean scanNumber(char[] chars){
        if(this.index < chars.length && (chars[index] == '+' || chars[index] == '-')){
            this.index++;
        }
        return scanUnsignNumber(chars);
    }

    public static void main(String[] args) {
        System.out.println(new Solution().validNumber("."));
        System.out.println(new Solution().validNumber("0"));
        System.out.println(new Solution().validNumber("-123.456e789"));
        System.out.println(new Solution().validNumber("123"));
        System.out.println(new Solution().validNumber("--123"));
    }

    /**
     A[.B][e|EA]
        .B[e|EA]
     */
    public boolean validNumber2(String s) {
        if (s == null) {
            return false;
        }
        s = s.trim();
        if (s.length() == 0) {
            return false;
        }

        char[] chars = s.toCharArray();
        for (char ch : chars) {
            if(ch== '+' || ch== '-'){
                continue;
            }
        }

        return true;
    }
}
