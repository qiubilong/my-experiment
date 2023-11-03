package org.example.code.list;

import com.google.common.collect.Lists;

import static org.example.code.list.MyLists.buildList;
import static org.example.code.list.MyLists.printList;


/**
 * @author chenxuegui
 * @since 2023/10/20
 */
public class 合并有序列表 {
    public static void main(String[] args) {

        ListNode l1 = buildList(Lists.newArrayList(1, 2, 4));
        ListNode l2 = buildList(Lists.newArrayList(1, 3, 4));

        //printList(mergeTwoLists(l1, l2));
        printList(mergeToListRecursion(l1, l2));
    }

    /** 遍历法 */
    public static ListNode mergeTwoLists(ListNode list1, ListNode list2){
        ListNode newHead = new ListNode();

        ListNode current = newHead;
        ListNode currentL1 = list1;
        ListNode currentL2 = list2;
        while (true){

            if(currentL1 == null){
                current.next = currentL2;
                break;
            }
            if(currentL2 == null){
                current.next = currentL1;
                break;
            }

            if(currentL1.val <= currentL2.val){
                current.next = currentL1;
                currentL1 = currentL1.next;
            }else {
                current.next = currentL2;
                currentL2 = currentL2.next;
            }

            current = current.next;
        }

        return newHead.next;
    }

    public static ListNode mergeToListRecursion(ListNode list1,ListNode list2){
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        if(list1.val < list2.val){
            list1.next = mergeToListRecursion(list1.next,list2);
            return list1;
        }else {
            list2.next = mergeToListRecursion(list1,list2.next);
            return list2;
        }

    }
}
