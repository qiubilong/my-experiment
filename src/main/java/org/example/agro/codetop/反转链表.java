package org.example.agro.codetop;

import com.google.common.collect.Lists;
import org.example.agro.链表.ListNode;

/**
 * @author chenxuegui
 * @since 2024/4/17
 * https://leetcode.cn/problems/reverse-linked-list/
 */
public class 反转链表 {

    public static void main(String[] args) {
        ListNode listNode = ListNode.buildList(Lists.newArrayList(1, 2, 3));
        ListNode headNew = reverseList(listNode);
        ListNode.printList(headNew);

        ListNode headNew2 = reverseList_递归(headNew);
        ListNode.printList(headNew2);
    }

    public static ListNode reverseList(ListNode head) {
        if(head == null){
            return null;
        }
        //使用头插法
        ListNode headNew = new ListNode();
        ListNode cur = head;
        while (cur !=null) {
            ListNode next = cur.next;

            ListNode newNext = headNew.next;
            headNew.next = cur;
            cur.next = newNext;

            cur = next;
        }
        return headNew.next;
    }

    /**
     * https://mp.weixin.qq.com/s/YVQvbhO0HJtnrocVg8-qmQ
     * 递归反转
     */
    public static ListNode reverseList_递归(ListNode head){
        if(head == null || head.next ==null){
            return head;
        }
        ListNode newHead = reverseList_递归(head.next);
        head.next.next = head;
        head.next = null;
        return newHead;
    }
}
