package org.example.list;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2023/10/25
 */
public class Stream流处理 {

    public static void main(String[] args) {
        // 使用一个容器装载 100 个数字，通过 Stream 并行处理的方式将容器中为单数的数字转移到容器 parallelList
        List<Integer> integerList= new ArrayList<Integer>();

        for (int i = 1; i <=100; i++) {
            integerList.add(i);
        }

        List<Integer> parallelList = new ArrayList<Integer>() ;
        integerList.stream()
                .parallel()
                .filter(i->i%2==1)
                .forEach(i->parallelList.add(i));

        System.out.println(parallelList.size());



        List<Integer> synchronizedList = Collections.synchronizedList(new ArrayList<>());
        integerList.stream()
                .parallel()
                .filter(i->i%2==1)
                .forEach(i->synchronizedList.add(i));
        System.out.println(synchronizedList.size());
    }
}
