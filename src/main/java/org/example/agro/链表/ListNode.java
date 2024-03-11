package org.example.agro.链表;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ListNode {
      public int val;
      public ListNode next;

      ListNode() {}
      ListNode(int val) {
       this.val = val;
      }

  }