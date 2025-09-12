package org.example.ok.剑指.t22_链表中倒数第k个节点;

import org.example.lang.list.LinkedList;
import org.example.lang.list.ListNode;

/**
 * @author chenxuegui
 * @since 2024/6/6
 * https://leetcode.cn/problems/lian-biao-zhong-dao-shu-di-kge-jie-dian-lcof/submissions/537590316/
 */
public class Solution {

    /** 先求长度，再遍历第几个节点 */
    public ListNode lastKNode(ListNode head,int k){
        int length = listLength(head);
        int position = length - k + 1;
        if(position<1){
            return null;
        }
        ListNode current = head;
        int index = 1;
        while (current!=null){
            if(index == position){
                return current;
            }
            current = current.next;
            index++;
        }
        return null;
    }

    public int listLength(ListNode head){
        int length = 0;
        ListNode current = head;
        while (current!=null){
            length++;
            current = current.next;
        }
        return length;
    }

    /** 快慢指针 */  /* 其实就是保持相对偏移 */
    public ListNode lastKNode2(ListNode head,int k){
        if(head == null){
            return null;
        }
        ListNode right = head;
        for (int i = 1; i <= k; i++) {
            right = right.next;
            if(i != k && right == null){
                return null;
            }
        }
        ListNode left = head;
        while (right!=null){
            right = right.next;
            left = left.next;
        }
        return left;
    }

    public static void main(String[] args) {
        ListNode head = LinkedList.buildList(new int[]{2,4,7,8});

        Solution solution = new Solution();
        System.out.println(solution.lastKNode(head,1));
        System.out.println(solution.lastKNode2(head,4));
    }
}
