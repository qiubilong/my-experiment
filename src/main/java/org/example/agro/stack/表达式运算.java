package org.example.agro.stack;

import java.util.Arrays;
import java.util.Stack;

/**
 * @author chenxuegui
 * @since 2023/11/2
 */
public class 表达式运算 {

    public static void main(String[] args) {
        String expression = "3+5*8-6";
        System.out.println(calExpression(expression));
        System.out.println(calExpression("1+2+3+4+5"));
    }
    public static Long calExpression(String expression){
        Long result = null;
        if(expression ==null ||expression.length() == 0){
            return result;
        }
        Stack<Long> stackNum = new Stack<>();
        Stack<SymbolEnum> stackSymbol= new Stack<>();
        for (int i = 0; i < expression.length(); i++) {
            char c = expression.charAt(i);
            if(Character.isDigit(c)){
                stackNum.push(Long.valueOf(c+""));
            }else {
                SymbolEnum symbol = SymbolEnum.getSymbol(c);
                if(!stackSymbol.isEmpty()){
                    SymbolEnum peek = stackSymbol.peek();
                    while (symbol.priority <= peek.priority){
                        SymbolEnum pop = stackSymbol.pop();
                        Long num2 = stackNum.pop();
                        Long num1 = stackNum.pop();
                        stackNum.push(pop.op(num1, num2));

                        if(stackSymbol.isEmpty()){
                            break;
                        }
                        peek = stackSymbol.peek();
                    }
                }
                stackSymbol.push(symbol);
            }
        }
        if(!stackSymbol.isEmpty()){
            SymbolEnum pop = stackSymbol.pop();
            Long num2 = stackNum.pop();
            Long num1 = stackNum.pop();
            result = pop.op(num1,num2);
        }

        return result;
    }

    interface OpNum{
        Long op(Long num1, Long num2);
    }

    public enum SymbolEnum implements OpNum{
        PLUS('+',1){
            @Override
            public Long op(Long num1, Long num2) {
                return num1 + num2;
            }
        },
        MINUS('-',1) {
            @Override
            public Long op(Long num1, Long num2) {
                return num1 - num2;
            }
        },
        MULTI('*',2) {
            @Override
            public Long op(Long num1, Long num2) {
                return num1 * num2;
            }
        },
        DIVIDE('/',2) {
            @Override
            public Long op(Long num1, Long num2) {
                return num1 / num2;
            }
        },

        ;

        private Character symbol;
        private int priority;

        SymbolEnum(Character symbol, int priority) {
            this.symbol = symbol;
            this.priority = priority;
        }

        public static SymbolEnum getSymbol(char symbol) {
            return Arrays.stream(values()).filter(e -> e.symbol == symbol).findFirst().orElseThrow();
        }
    }
}
