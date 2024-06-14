package org.example.ok.剑指.t23_链表中环的节点入口;

import org.example.lang.list.LinkedList;
import org.example.lang.list.ListNode;

import java.util.Objects;

/**
 * @author chenxuegui
 * @since 2024/6/14
 */
public class Solution {

    public ListNode findCircleNode(ListNode head){
        ListNode circleNode = isCircleList(head);
        if(circleNode == null){
            return null;
        }
        int circleLength = circleLength(circleNode);

        ListNode p1 = head;
        ListNode p2 = head;
        for (int i = 0; i < circleLength; i++) {
            p2 = p2.next;
        }

        while (p1 != p2){
            p1 = p1.next;
            p2 = p2.next;
        }

        return p1;
    }

    public ListNode isCircleList(ListNode head){

        ListNode p1 = head;
        ListNode p2 = head;
        while (p1 != null && p2!=null){
            p1 = p1.next;
            p2 = p2.next;
            if(p2!=null){
                p2 = p2.next;
            }
            if(p2!=null && p1 == p2){
                return p1;
            }
        }
        return null;
    }

    private int circleLength(ListNode node){
        int length = 0;

        ListNode p2 = node.next;
        length++;
        while (p2!=null && p2 != node){
            p2 = p2.next;
            length++;
        }
        return length;
    }



    public static void main(String[] args) {
        int[] nums = new int[]{1,3,5,7,9};
        ListNode head = LinkedList.buildList(nums);
        LinkedList.printList(head);
        ListNode circle = LinkedList.findNode(head, 5);
        ListNode tail = LinkedList.findNode(head, nums[nums.length-1]);
        tail.next = circle;
        System.out.println(tail);
        System.out.println(circle);

        Solution solution = new Solution();
        System.out.println(solution.findCircleNode(head));

        int[] nums1 = new int[]{-1,-7,7,-4,19,6,-9,-5,-2,-5};
        ListNode head1 = LinkedList.buildList(nums1);
        ListNode tail1 = LinkedList.findNode(head1, nums1[nums1.length-1]);
        tail1.next = tail1;
        System.out.println(solution.findCircleNode(head1));
    }
}
