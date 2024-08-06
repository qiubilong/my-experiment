package org.example.base;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * @author chenxuegui
 * @since 2024/4/19
 * https://github.com/lcyanxi/springboot-learn/blob/master/Java%E8%99%9A%E6%8B%9F%E6%9C%BA/%E5%BC%BA%E3%80%81%E8%BD%AF%E3%80%81%E5%BC%B1%E3%80%81%E8%99%9A%E5%BC%95%E7%94%A8%E4%BD%BF%E7%94%A8%E5%9C%BA%E6%99%AF.md
 */
public class 强弱引用对比 {
    public static void main(String[] args) {
        myHashMap();
        System.out.println("==========");
        myWeakHashMap();

        Object o1 = new Object();
        WeakReference<Object> weakReference = new WeakReference<>(o1);
        System.out.println(o1);
        System.out.println(weakReference.get());
        o1 = null;
        System.gc();
        System.out.println(o1);
        System.out.println(weakReference.get());
    }

    private static void myHashMap() {
        Map<Integer, String> map = new HashMap<>();
        Integer key = new Integer(1);
        String value = "HashMap";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.gc();
        //对于普通的HashMap来说，key置空并不会影响，HashMap的键值对，因为这个属于强引用，不会被垃圾回收
        System.out.println(map);
    }

    private static void myWeakHashMap() {
        Map<Integer, String> map = new WeakHashMap<>();
        Integer key = new Integer(1);
        String value = "WeakHashMap";
        map.put(key, value);
        System.out.println(map);
        key = null;
        System.gc();
        //WeakHashMap，在进行GC操作后，弱引用的就会被回收
        System.out.println(map);
    }
}
