package org.example.juc;

/**
 * @author chenxuegui
 * @since 2024/1/29
 */
public class 内存变量不可见示例 {
    private static boolean ready = false;
    private static int number;

    private static class Reader extends Thread {
        @Override
        public void run() {
            while (!ready) {
                //Thread.sleep(0)
                Thread.yield();
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new Reader().start();

        number = 33;
        ready = true;
    }
}
