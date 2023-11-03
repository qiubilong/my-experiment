package org.example.code.list;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.Stack;

import static org.example.code.list.MyLists.buildList;
import static org.example.code.list.MyLists.printList;


public class K个一组翻转链表 {

    public static void main(String[] args) {
        ArrayList<Integer> nums = Lists.newArrayList(1, 2, 3, 4, 5);
        ListNode head = buildList(Lists.newArrayList(nums));
        printList(head);
        System.out.println("==========reverseList============");
        //printList(reverseListK(head, 2));
        System.out.println("==========reverseList============");
        //printList(reverseListK(buildList(Lists.newArrayList(nums)), 3));

        System.out.println("==========reverseList============");
        //printList(reverseListK(buildList(Lists.newArrayList(1,2)), 2));

        System.out.println("==========reverseList============");
        //printList(reverseListK(buildList(Lists.newArrayList(1,2,3,4)), 2));

        System.out.println("==========reverseList============");
        printList(reverseListKV2(buildList(Lists.newArrayList(1,2,3,4)), 2));
        System.out.println("==========reverseList============");
        printList(reverseListKV2(buildList(Lists.newArrayList(1, 2, 3, 4, 5)), 3));
    }

    public static ListNode reverseListKV2(ListNode head,int k){
        ListNode headNew = new ListNode();
        headNew.next = head;

        ListNode left =  head;
        ListNode right = head;
        ListNode lastNext = right.next;
        ListNode newlast = headNew;
        int i = 1;
        while (right !=null){
            lastNext = right.next;
            if(i == k ){
                i = 1;
                ListNode listSub = reverseListSub(left,right);
                newlast.next = listSub;
                newlast = left;

                left = lastNext;
                right = lastNext;
            }else {
                i++;
                right = lastNext;
            }

        }
        return headNew.next;
    }

    public static  ListNode reverseListSub(ListNode left,ListNode right){
        ListNode headNew = new ListNode();
        ListNode rNext = right.next;
        ListNode newlast = left;
        while (left!=null && !left.equals(rNext)) {
            ListNode lNext = left.next;

            ListNode next = headNew.next;
            headNew.next = left;
            left.next = next;

            left = lNext;
        }
        newlast.next = rNext;
        return headNew.next;
    }



    public static ListNode reverseListK(ListNode head,int k){
        ListNode headNew = new ListNode();
        ListNode currentNew = headNew;
        Stack<ListNode> stack = new Stack<>();
        ListNode current = head;
        int i = 0;
        while (current!=null){

            if(i < k){
                stack.push(current);
                current = current.next;
                i++;
            }else {
                i = 0;
                //尾插法
                while (!stack.isEmpty()){
                    ListNode pop = stack.pop();
                    currentNew.next = pop;
                    currentNew = currentNew.next;
                }
                currentNew.next = null;
            }

        }
        if(stack.size()>=k){
            //尾插法
            while (!stack.isEmpty()){
                ListNode pop = stack.pop();
                currentNew.next = pop;
                currentNew = currentNew.next;
            }
            currentNew.next = null;
        }else {
            //头插法
            while (!stack.isEmpty()){
                ListNode pop = stack.pop();
                ListNode next = currentNew.next;
                currentNew.next = pop;
                pop.next = next;
            }
        }



        return headNew.next;
    }
}
