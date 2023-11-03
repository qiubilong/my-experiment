package org.example.code.list;

import java.util.Iterator;
import java.util.LinkedList;

public class ListTest {

    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<>();
        list.add("a");
        list.add("b");
        list.add("c");

        System.out.println(list);
        System.out.println(reverseList(list));
    }

    //字符串反转
    public static LinkedList<String> reverseList(LinkedList<String> list){
        LinkedList<String> l = new LinkedList<>();
        Iterator<String> it = list.iterator();
        while (it.hasNext()){
            l.addFirst(it.next());
        }
        return l;
    }

}
