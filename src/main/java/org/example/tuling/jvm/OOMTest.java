package org.example.tuling.jvm;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class OOMTest {

   public static List<Object> list = new ArrayList<>();

   // JVM设置
   // -Xms10M -Xmx10M -XX:+PrintGCDetails -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=D:\jvm.dump 
   public static void main(String[] args) {
      List<Object> list = new ArrayList<>();
      long i = 0;
      long j = 0;
      while (true) {
         list.add(new User(i++, UUID.randomUUID().toString()));
         new User(j--, UUID.randomUUID().toString());
      }
   }
}