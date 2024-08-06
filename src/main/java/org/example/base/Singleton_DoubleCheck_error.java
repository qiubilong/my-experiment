package org.example.base;

/** 懒汉式，按需加载
 *
 * @author chenxuegui
 * @since 2024/4/22
 */
public class Singleton_DoubleCheck_error {
    /** 使用 volatile 实现禁止锁区域内代码指令重排，保证实例正常初始化 */
    private  static Singleton_DoubleCheck_error instance;

    private Singleton_DoubleCheck_error(){

    }

    public static Singleton_DoubleCheck_error getInstance(){
        if(instance == null){
            synchronized (Singleton_DoubleCheck_error.class){
                if(instance == null){
                    instance = new Singleton_DoubleCheck_error();
                }
            }
        }
        return instance;
    }
}
