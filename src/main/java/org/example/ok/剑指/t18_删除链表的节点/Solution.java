package org.example.ok.剑指.t18_删除链表的节点;

import org.example.lang.list.LinkedList;
import org.example.lang.list.ListNode;

/**
 * https://leetcode.cn/problems/shan-chu-lian-biao-de-jie-dian-lcof/submissions/536182754/
 *
 * @author chenxuegui
 * @since 2024/5/31
 *
 * 在Java中，参数传递是按值传递的。这意味着当你将一个变量传递给一个方法时，传递的是变量值的副本，而不是变量本身。对于基本数据类型（如int，float等），传递的是实际值。对于对象来说，传递的是对象的引用值，而不是对象本身。这有时会给人一种传递引用的错觉，但实际上是按值传递的。这就是为什么在方法内部对对象的更改会影响原始对象，但在方法内部重新分配引用不会影响方法外部的原始引用
 */
public class Solution {


    /** O(1)时间删除链表 */
    public ListNode deleteNode(ListNode head, int val) {
        if(head == null){
            return head;
        }

        if(head.val == val){
            /***
             * 对象的引用是按值传递的，即函数接收到的是对象引用的副本，而不是对象本身。当在函数内部将这个引用参数指向其他对象时，
             * 只是改变了副本的指向，并不影响原始对象的引用。原始对象仍然指向原来的内存地址，因此不会发生改变
             */
            head = head.next;
            return head;
        }

        ListNode before = head;
        ListNode current = head.next;
        while (current!=null){
            if(current.val == val){
                before.next = current.next;
                return head;
            }
            before = current;
            current = current.next;
        }
        return head;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{4,5,1,9};
        ListNode head = LinkedList.buildList(nums);
        LinkedList.printList(head);

        ListNode node = LinkedList.findNode(head,5);

        Solution solution = new Solution();
        solution.deleteNode(head,4);
        LinkedList.printList(head);
    }
}
