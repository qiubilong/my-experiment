package org.example.code.list;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import static org.example.code.list.MyLists.buildList;
import static org.example.code.list.MyLists.printList;

public class 反转列表 {

    public static void main(String[] args) {
        ListNode listNode = buildList(Lists.newArrayList(2, 4, 6, 8, 44, 66));
        printList(listNode);
        System.out.println("==========reverseList============");
        ListNode reverseList = reverseList(listNode);
        printList(reverseList);
    }

    public static ListNode reverseList(ListNode head) {
        if(head ==null){
            return null;
        }
        ListNode headNew = new ListNode();

        ListNode current = head;
        while (current !=null){
            ListNode next = current.next;

            ListNode newNext = headNew.next;
            headNew.next = current;
            current.next = newNext;

            current = next;
        }
        return headNew.next;
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
