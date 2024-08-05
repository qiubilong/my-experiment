package org.example.tuling.jvm;

/**
 * @author chenxuegui
 * @since 2024/8/5
 *
 * Launcher类初始化拓展类加载ExtClassLoader 和应用类加载器AppClassLoader
 */
public class MyClassLoader extends ClassLoader{

    public static void main(String[] args) {

        ClassLoader appClassLoader = MyClassLoader.getSystemClassLoader();

        ClassLoader parent = appClassLoader.getParent();
        System.out.println(appClassLoader);
        System.out.println(parent);
        System.out.println(parent.getParent());

    }
}
