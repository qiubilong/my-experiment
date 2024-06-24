package org.example.ok.剑指.t30_包含min函数的栈;

import java.util.*;

/**
 * @author chenxuegui
 * @since 2024/6/20
 */
public class MyStack {

    private Stack<Integer> stack;
    private Stack<Integer> stackMin;

    public MyStack() {
        stack = new Stack<>();
        stackMin = new Stack<>();
    }

    public int min(){
        return stackMin.peek();
    }

    public int pop(){
        stackMin.pop();
        return stack.pop();
    }

    public void push(int i){
        stack.push(i);
        if(stackMin.isEmpty()){
            stackMin.push(i);
        }else {
            Integer min = min();
            if(i< min){
                min = i;
            }
            stackMin.push(min);
        }
    }

    public static void main(String[] args) {
        MyStack myStack = new MyStack();
        List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);
        Collections.shuffle(integers);
        integers.stream().forEach(v->{
            myStack.push(v);
            System.out.println("push->"+v+",min="+myStack.stackMin);
        });
    }
}
