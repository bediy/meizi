package com.example;

/**
 * Created by Administrator on 2016/8/25.
 */
public class Linked {

    static class Node {
        int n;
        Node next;
    }

    public static Node ReverseList(Node head) {

        Node pre = head,
                mid = pre.next,
                last = mid.next;
        pre.next = null;
        while (mid != null) {
            mid.next = pre;
            pre = mid;
            mid = last;
            if (last != null)
                last = last.next;
        }
        return pre;
    }

    public static Node ReverseList2(Node head) {

        Node pre = null, next = null;
        while (head != null) {
            next = head.next;
            head.next = pre;
            pre = head;
            head = next;
        }
        return pre;
    }

    public static void main(String[] args) {
        Node h = creataLinked();
        h = ReverseList(h.next);
        Node q = h;
        while (q != null) {
            System.out.print(q.n + " ");
            q = q.next;
        }

        /*Node p = h.next;

        Node insert = new Node();
        insert.n = 3;*/
        /*while (p != null) {
            if (p.next != null) {
                if (insert.n > p.n && insert.n < p.next.n) {
                    insert.next = p.next;
                    p.next = insert;
                    break;
                } else if (insert.n < p.n) {
                    insert.next = h.next;
                    h.next = insert;
                    break;
                }
            } else if (insert.n > p.n) {
                p.next = insert;
            }
            p = p.next;
        }*/



    }
//       p
// 1->2->3->4

    private static Node creataLinked() {
        Node h = new Node();
        Node p = new Node();
        p.n = 1;
        h.next = p;
        for (int i = 2; i <= 10; i = i + 2) {
            Node node = new Node();
            node.n = i;
            p.next = node;
            p = p.next;
        }
        return h;
    }
}
