package org.example.code.list;

import com.google.common.collect.Lists;
import org.springframework.util.CollectionUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author chenxuegui
 * @since 2023/10/20
 */
public class MyLists {

    public static ListNode buildList(int[] nums){
        return buildList(Arrays.stream(nums).boxed().collect(Collectors.toList()));
    }

    public static ListNode buildList(List<Integer> nums){
        if (CollectionUtils.isEmpty(nums)) {
            return null;
        }
        ListNode head = new ListNode(nums.get(0));
        ListNode current = head;
        for (int i = 1; i < nums.size(); i++) {
            ListNode n = new ListNode(nums.get(i));
            current.setNext(n);
            current = n;
        }
        System.out.println(head);
        return head;
    }

    public static void printList(ListNode head){
        ListNode current = head;
        while(current != null){
            System.out.println(current.getVal()+",");
            current = current.getNext();
        }
    }
}
