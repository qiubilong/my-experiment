package org.example.ok.剑指.t25_合并两个有序链表;

import org.example.lang.list.LinkedList;
import org.example.lang.list.ListNode;

/**
 * @author chenxuegui
 * @since 2024/6/17
 */
public class Solution {

    public ListNode mergeList(ListNode list1,ListNode list2){
        ListNode head = new ListNode();
        ListNode current = head;
        ListNode p1 = list1;
        ListNode p2 = list2;
        while (p1!=null && p2!=null){
            if(p1.val< p2.val){
                current.next = p1;
                p1 = p1.next;
            }else {
                current.next = p2;
                p2 = p2.next;
            }
            current = current.next;
        }

        while (p1!=null){
            current.next = p1;
            p1 = p1.next;
            current = current.next;
        }

        while (p2!=null){
            current.next = p2;
            p2 = p2.next;
            current = current.next;
        }
        return head.next;
    }

    public ListNode mergeTwoLists(ListNode list1, ListNode list2) {
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

    public static void main(String[] args) {

        ListNode list1 = LinkedList.buildList(new int[]{1,3,5,7,9});
        ListNode list2 = LinkedList.buildList(new int[]{2,4,6,8,10});

        Solution solution = new Solution();
        ListNode list = solution.mergeList(list1,list2);
        LinkedList.printList(list);
    }
}
