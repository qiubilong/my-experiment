package org.example.ok.agro.链表;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Data
@Accessors(chain = true)
public class ListNode {
      public int val;
      public ListNode next;

      public ListNode() {}
      public ListNode(int val) {
       this.val = val;
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