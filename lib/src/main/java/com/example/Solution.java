package com.example;

/**
 * Created by Ye on 2017/3/9/0009.
 * 翻转链表
 */


class ListNode {

    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}


public class Solution {

    static int[] ints = {1, 2, 3, 4, 5, 6, 7};

    public static ListNode createList() {
        ListNode head = new ListNode(0);
        ListNode p = head;
        for (int i = 0; i < ints.length; i++) {
            ListNode node = new ListNode(ints[i]);
            p.next = node;
            p = p.next;
        }
        return head;
    }

    public static ListNode ReverseList(ListNode head) {
        ListNode pre = null;
        ListNode next;

        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;

        }
        return pre;
    }

    public static void main(String args[]) {
        ListNode head = createList();

        head = ReverseList(head);

        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
    }

}
