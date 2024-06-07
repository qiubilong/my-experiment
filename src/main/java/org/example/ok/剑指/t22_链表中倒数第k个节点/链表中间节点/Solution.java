package org.example.ok.剑指.t22_链表中倒数第k个节点.链表中间节点;

import org.example.lang.list.LinkedList;
import org.example.lang.list.ListNode;

/**
 * @author chenxuegui
 * @since 2024/6/6
 *
 * https://leetcode.cn/problems/middle-of-the-linked-list/submissions/537605984/
 */
public class Solution {

    /** 如果只能遍历一次链表，一般使用快慢指针解决问题 */
    public ListNode middleNode(ListNode head){
        if(head == null){
            return null;
        }
        ListNode left  = head;
        ListNode right = head.next;
        boolean two = false;
        if(right !=null){
            two = true;
        }
        while (right!=null && right.next!=null){
            two = false;
            left = left.next;
            right = right.next.next;
            if(right!=null){
                two = true;
            }
        }
        if(two){
            return left.next;
        }
        return left;
    }

    public static void main(String[] args) {
        ListNode head = LinkedList.buildList(new int[]{2,4});
        Solution solution = new Solution();
        System.out.println(solution.middleNode(head));
    }
}
