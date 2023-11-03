package org.example.code.list;

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