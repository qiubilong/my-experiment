package org.example.lang.list;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chenxuegui
 * @since 2024/5/27
 */
public class LinkedList {

    public static ListNode buildList(int[] nums){
        ListNode head = new ListNode();
        ListNode last = head;
        for (int i = 0; i < nums.length; i++) {
            ListNode node = new ListNode(nums[i]);
            last.next = node;
            last = node;
        }
        return head.next;
    }

    public static void printList(ListNode head){
        List<Integer> result = new ArrayList<>();
        ListNode current = head;
        while (current!=null){
            result.add(current.val);
            current = current.next;
        }
        System.out.println(result);
    }

    public static ListNode findNode(ListNode head, int val){
        ListNode current = head;
        while (current != null){
            if(current.val == val){
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{3,6,4,1};
        ListNode head = LinkedList.buildList(nums);
        LinkedList.printList(head);
    }
}
