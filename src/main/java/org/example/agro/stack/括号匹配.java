package org.example.agro.stack;

import java.util.Stack;

/**
 * @author chenxuegui
 * @since 2023/11/2
 */
public class 括号匹配 {

    public static void main(String[] args) {
        System.out.println(ifMatch("[()]"));
        System.out.println(ifMatch("[()"));
        System.out.println(ifMatch("[()x"));
    }

    public static boolean ifMatch(String s){
        if(s == null || s.length() <1){
            return false;
        }
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            if(ch == '{' || ch == '[' || ch == '('){
                stack.push(ch);
            }else {
                Character pop = stack.pop();
                if(ch == '}'){
                    if(pop != '{'){
                        return false;
                    }
                }else if(ch == ']'){
                    if(pop != '['){
                        return false;
                    }
                }else if(ch == ')'){
                    if(pop != '('){
                        return false;
                    }
                }else {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }
}
