package org.example.tuling.jvm;

/**
 * @author chenxuegui
 * @since 2024/8/6
 * //添加运行JVM参数： -XX:+PrintGCDetails
 * //打印JVM所有参数： -XX:+PrintFlagsFinal
 * -Xmx300m
 * -Xms300m
 * -Xmn100m
 * -XX:+PrintGCDetails
 * -XX:+PrintGCDateStamps
 * -XX:+UseParNewGC
 * -XX:+UseConcMarkSweepGC
 * -XX:PretenureSizeThreshold=1M
 */
public class GCTest {

    public static void main(String[] args) {

        byte[] allocation1, allocation2, allocation3, allocation4, allocation5, allocation6;
            allocation1 = new byte[60000*1024];

         allocation2 = new byte[8000*1024];

        allocation3 = new byte[1000*1024];
         allocation4 = new byte[1000*1024];
         allocation5 = new byte[1000*1024];
         allocation6 = new byte[1000*1024];

    }
}
