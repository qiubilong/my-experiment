package org.example.tuling.jvm;

public class 字符串常量池 {

    public static void main(String[] args) {
        String s1 = new String("zhuge");
        /***
         *  String s1 = new String("zhuge");
         * 相当于
         * String s = "zhuge"; -->字符串字面量自动保存在常量池中
         * String s1 = new String(s);
         */
        String s2 = s1.intern();//获取常量池里的对象

        System.out.println(s1 == s2);  //false

        test1();

    }

    private static void test1() {
        System.out.println("==========test1");
        String s1 = new String("he") + new String("llo");
        String s2 = s1.intern();//执行前hello在常量池中不存在，执行后一共生成5个对象
        System.out.println(s1 == s2);
    }

    private static void test7() {
        System.out.println("==========test7");
        //字符串常量池："计算机"和"技术"     堆内存：str1引用的对象"计算机技术"
//堆内存中还有个StringBuilder的对象，但是会被gc回收，StringBuilder的toString方法会new String()，这个String才是真正返回的对象引用
        String str2 = new StringBuilder("计算机").append("技术").toString();   //没有出现"计算机技术"字面量，所以不会在常量池里生成"计算机技术"对象
        System.out.println(str2 == str2.intern());  //true
//"计算机技术" 在池中没有，但是在heap中存在，则intern时，会直接返回该heap中的引用

//字符串常量池："ja"和"va"     堆内存：str1引用的对象"java"
//堆内存中还有个StringBuilder的对象，但是会被gc回收，StringBuilder的toString方法会new String()，这个String才是真正返回的对象引用
        String str1 = new StringBuilder("ja").append("va").toString();    //没有出现"java"字面量，所以不会在常量池里生成"java"对象
        System.out.println(str1 == str1.intern());  //false
//java是关键字，在JVM初始化的相关类里肯定早就放进字符串常量池了

        String s1 = new String("test");
        System.out.println(s1 == s1.intern());   //false
//"test"作为字面量，放入了池中，而new时s1指向的是heap中新生成的string对象，s1.intern()指向的是"test"字面量之前在池中生成的字符串对象

        String s2 = new StringBuilder("abc").toString();
        System.out.println(s2 == s2.intern());  //false
//同上
    }
}
