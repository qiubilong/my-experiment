package org.example.tuling.jvm;

/**
 * @author chenxuegui
 * @since 2024/8/9
 */
public class MathHighCPU {

    public int compute() {  //一个方法对应一块栈帧内存区域
        int a = 1;
        int b = 2;
        int c = (a + b) * 10;
        return c;
    }

    public static void main(String[] args) {
        MathHighCPU math = new MathHighCPU();
        while (true){
            int total = math.compute();
        }
    }
}
