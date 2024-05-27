package org.example.ok.剑指.t6_从尾到头打印链表;

import org.example.lang.list.LinkedList;
import org.example.lang.list.ListNode;

import java.util.*;

/**
 * @author chenxuegui
 * @since 2024/5/27
 *
 * https://leetcode.cn/problems/cong-wei-dao-tou-da-yin-lian-biao-lcof/description/
 */
public class Solution {

    /** 利用栈实现先进后出 */
    public int[] reverseList_Stack(ListNode head){
        Stack<Integer> stack = new Stack<>();
        ListNode current = head;
        while (current!=null){
            stack.push(current.val);
            current = current.next;
        }
        List<Integer> result = new ArrayList<>();
        while (!stack.isEmpty()){
            result.add(stack.pop());
        }
        return result.stream().mapToInt(v-> v).toArray();
    }


    /** 利用递归栈结构实现先进后出 */
    private List<Integer> result = new ArrayList<>();
    public int[] reverseList(ListNode head){
        reverseListDo(head);
        return result.stream().mapToInt(v-> v).toArray();
    }

    private void reverseListDo(ListNode node){
        if (node ==null){
            return;
        }

        reverseListDo(node.next);

        result.add(node.val);
    }


    public static void main(String[] args) {
        int[] nums = new int[]{3,6,4,1};
        ListNode head = LinkedList.buildList(nums);
        Solution solution = new Solution();
        System.out.println(Arrays.toString(solution.reverseList_Stack(head)));
        System.out.println(Arrays.toString(solution.reverseList(head)));

        Deque<Integer> stack = new ArrayDeque<>();
        stack.push(3);
        stack.push(6);
        stack.push(4);
        while (!stack.isEmpty()){
            System.out.println(stack.pop());
        }
    }
}
