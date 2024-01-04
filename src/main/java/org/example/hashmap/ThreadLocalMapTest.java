package org.example.hashmap;

/**
 * @author chenxuegui
 * @since 2023/11/9
 */
public class ThreadLocalMapTest {
    static ThreadLocal<Integer> localMap = new ThreadLocal<Integer>();

    public static void main(String[] args) {
        localMap.set(1);
    }
}
