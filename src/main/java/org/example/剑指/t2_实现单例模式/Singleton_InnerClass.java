package org.example.剑指.t2_实现单例模式;

/** 懒汉式
 * @author chenxuegui
 * @since 2024/4/22
 * 利用jvm加载类机制保证多线程安全
 * 利用内部静态类实现按需加载，实现节省内存
 */
public class Singleton_InnerClass {

    private Singleton_InnerClass(){

    }

    public static Singleton_InnerClass getInstance(){
        return SingletonHolder.INSTANCE;
    }


    private static class SingletonHolder {
        private final static Singleton_InnerClass INSTANCE = new Singleton_InnerClass();

    }
}
