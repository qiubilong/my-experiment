package org.example.剑指.t2_实现单例模式;

/** 饿汉式，JDK中 RunTime 工具类使用这种模式
 * @author chenxuegui
 * @since 2024/4/22
 * 利用JVM加载类的特性保证多线程安全
 * 加载类时初始化实例
 */
public class Singleton {

    private final static Singleton INSTANCE = new Singleton();

    private Singleton(){

    }

    public static Singleton getInstance(){
        return INSTANCE;
    }
}
