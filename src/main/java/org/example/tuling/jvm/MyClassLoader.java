package org.example.tuling.jvm;

import java.io.FileInputStream;
import java.lang.reflect.Method;

/**
 * @author chenxuegui
 * @since 2024/8/5
 *
 * Launcher类初始化拓展类加载ExtClassLoader 和应用类加载器AppClassLoader
 */
public class MyClassLoader extends ClassLoader{

    private String classPath;
    public MyClassLoader(String classPath) {
        this.classPath = classPath;
    }


    private byte[] loadByte(String name) throws Exception {
        name = name.replaceAll("\\.", "/");
        FileInputStream fis = new FileInputStream(classPath + "/" + name
                + ".class");
        int len = fis.available();
        byte[] data = new byte[len];
        fis.read(data);
        fis.close();
        return data;
    }


    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
       try {
           byte[] bytes = loadByte(name);
           return defineClass(name,bytes,0,bytes.length);
       }catch (Exception e){
           e.printStackTrace();
           throw new ClassNotFoundException(name);
       }
    }

    /** 打破双亲委派模型 */
    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        synchronized (getClassLoadingLock(name)) {
            // First, check if the class has already been loaded
            Class<?> c = findLoadedClass(name);
            if (c == null) {
                long t0 = System.nanoTime();

                if(name.startsWith("java.")){
                    c = this.getParent().loadClass(name);
                }

                if (c == null) {
                    // If still not found, then invoke findClass in order
                    // to find the class.
                    long t1 = System.nanoTime();
                    c = findClass(name);

                    // this is the defining class loader; record the stats
                    sun.misc.PerfCounter.getParentDelegationTime().addTime(t1 - t0);
                    sun.misc.PerfCounter.getFindClassTime().addElapsedTimeFrom(t1);
                    sun.misc.PerfCounter.getFindClasses().increment();
                }
            }
            if (resolve) {
                resolveClass(c);
            }
            return c;
        }
    }



    public static void main(String[] args) throws Exception{
        ClassLoader appClassLoader = MyClassLoader.getSystemClassLoader();
        System.out.println("appClassLoader -->" + appClassLoader);
        System.out.println("extClassLoader -->" + appClassLoader.getParent());
        System.out.println("boostrapClassLoader -->" + appClassLoader.getParent().getParent());

        System.out.println();
        MyClassLoader myClassLoader = new MyClassLoader("C:\\data");
        //首先删掉工程的Math1类
        Class<?> aClass = myClassLoader.loadClass("org.example.tuling.jvm.Math1");
        Object instance = aClass.newInstance();
        Method method = aClass.getMethod("compute", null);
        Object invoke = method.invoke(instance, null);
        System.out.println(aClass.getClassLoader() +"---load Math1 class---" +aClass.hashCode());


        System.out.println();
        MyClassLoader myClassLoader1 = new MyClassLoader("C:\\data");
        //首先删掉工程的Math1类
        Class<?> aClass1 = myClassLoader1.loadClass("org.example.tuling.jvm.Math1");
        Object instance1 = aClass1.newInstance();
        Method method1 = aClass1.getMethod("compute", null);
        Object invoke1 = method1.invoke(instance1, null);
        System.out.println(aClass1.getClassLoader() +"---load Math1 class---" +aClass1.hashCode());

        /** JVM可以以同时存在 同包同名 的类对象，因为它们的类加载器不同，这样就可以做到不同版本隔离和热部署*/

    }
}
