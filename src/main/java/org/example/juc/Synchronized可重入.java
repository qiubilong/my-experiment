package org.example.juc;

/**
 * @author chenxuegui
 * @since 2024/1/29
 */
public class Synchronized可重入 {

    static final Object monitor = new Object();

    public static void main(String[] args) {

        synchronized (monitor){
            System.out.println("acquire 1");

            synchronized (monitor){
                System.out.println("acquire 2");

                synchronized (monitor) {
                    System.out.println("acquire 3");
                }
            }

        }
    }
}
