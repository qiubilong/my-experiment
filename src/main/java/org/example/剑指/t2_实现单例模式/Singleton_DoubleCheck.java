package org.example.剑指.t2_实现单例模式;

/** 懒汉式，按需加载
 *
 * @author chenxuegui
 * @since 2024/4/22
 */
public class Singleton_DoubleCheck {
    /** 使用 volatile 实现禁止锁区域内代码指令重排，保证实例正常初始化 */
    private volatile static Singleton_DoubleCheck instance;

    private Singleton_DoubleCheck(){

    }

    public static Singleton_DoubleCheck getInstance(){
        if(instance == null){
            synchronized (Singleton_DoubleCheck.class){
                if(instance == null){
                    instance = new Singleton_DoubleCheck();
                }
            }
        }
        return instance;
    }
}
