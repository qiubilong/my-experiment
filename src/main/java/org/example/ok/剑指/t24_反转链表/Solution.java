package org.example.ok.剑指.t24_反转链表;

import org.example.lang.list.LinkedList;
import org.example.lang.list.ListNode;

import java.util.List;
import java.util.Stack;

/**
 * @author chenxuegui
 * @since 2024/6/17
 *
 * https://leetcode.cn/problems/reverse-linked-list/submissions/540016738/
 */
public class Solution {


    public ListNode reverseList(ListNode head){
        ListNode newHead = new ListNode();
        ListNode current = head;
        while (current!=null){

            ListNode next = current.next;

            current.next = newHead.next;
            newHead.next = current;

            current = next;
        }
        return newHead.next;
    }

    public ListNode reverseListByStack(ListNode head){
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        while (current !=null){
            stack.push(current);
            current = current.next;
        }

        ListNode newHead = new ListNode();
        ListNode lastNode = newHead;

        while (!stack.isEmpty()){
            ListNode pop = stack.pop();
            pop.next = null;

            lastNode.next = pop;
            lastNode = pop;
        }
        return newHead.next;
    }

    public static void main(String[] args) {

        int[] nums = new int[]{1,3,5,7,9};

        ListNode head = LinkedList.buildList(nums);
        LinkedList.printList(head);
        Solution solution = new Solution();
       // ListNode list = solution.reverseList(head);
        //LinkedList.printList(list);
        LinkedList.printList(solution.reverseListByStack(head));
    }
}
