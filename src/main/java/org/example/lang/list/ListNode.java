package org.example.lang.list;

/**
 * @author chenxuegui
 * @since 2024/5/27
 */
public class ListNode {
    public Integer val;
    public ListNode next;

    public ListNode() {
    }

    public ListNode(Integer val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return "ListNode{" +
                "val=" + val +
                '}';
    }
}
