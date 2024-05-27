package org.example.ok.agro.链表;

import com.google.common.collect.Lists;

import java.util.Iterator;
import java.util.LinkedList;

import static org.example.ok.agro.链表.MyLists.buildList;
import static org.example.ok.agro.链表.MyLists.printList;

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
