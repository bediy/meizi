package com.example;

public class Practices {

    /*
            collection
                |
                |-list
                |   |-linkedlist
                |   |-arraylist
                |   |-vector
                |       |-stack
                |-set

                map
                |-hashmap
                |_hashtable
                |-weakhashmap


    */

    static String s = "sf;;;gh;;;sdf;;;fff";
    static String sp = ";;;";


    static int[] a = {2, 6, 2, 8, 1, 9, 3, 6};

    static class Node {
        int value;
        Node leftChild;
        Node rightChild;
    }

    private Node reverseBinaryTree(Node node) {
        if (node == null)
            return null;
        reverseBinaryTree(node.leftChild);
        reverseBinaryTree(node.rightChild);

        Node temp = node.leftChild;
        node.leftChild = node.rightChild;
        node.rightChild = temp;


        return node;
    }

    public static void main(String[] args) {
        Practices practices = new Practices();
//        practices.mergeSort(a, 0, a.length - 1, new int[a.length]);

        practices.subStirng("asdfhiwoefjwlefkksdjf", "jf");

        int i = 5;
        int s = (i++) + (++i) + (i--) + (--i);
        System.out.print(s);


/*
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i]);
        }
*/

    }

    private void subStirng(String s, String sub) {
        char[] chars = s.toCharArray();
        char[] subChars = sub.toCharArray();

        int charLength = chars.length;
        int subLength = subChars.length;


        boolean isSub = true;
        for (int i = 0; i < charLength; i++) {
            isSub = true;
            for (int j = 0; j < subLength; j++) {
                if (chars[i + j] != subChars[j]) {
                    isSub = false;
                    break;
                }
            }
            if (isSub == true) {
                System.out.print("yes!");
                break;
            }
        }
        if (isSub == false)
            System.out.print("no!");

    }

    private void count1(int num) {
        int n = 0;
        while (num != 0) {
            num = num & (num - 1);
            n++;
        }
        System.out.print(n);
    }


    private void funIsPrime() {
        int n = 0;
        for (int i = 0; i < a.length; i++) {
            n = a[i];
            if (n == 1)
                continue;
            int j = 2;
            for (; j < n; j++) {
                if (n % j == 0)
                    break;
            }
            if (j >= n)
                System.out.print(n + " ");
        }
    }

    public void reOrderArray(int[] array) {
        for (int i = 0; i < array.length; i++) {

            int temp = array[i];

            if (temp % 2 != 0)
                continue;
            int j = i - 1;
            for (; j >= 0 && array[j] % 2 == 0; j--) {
                array[j + 1] = array[j];
            }
            array[j + 1] = temp;

        }

    }

    private void mergeSort(int a[], int l, int r, int temp[]) {
        if (l < r) {
            int m = (l + r) / 2;
            mergeSort(a, l, m, temp);
            mergeSort(a, m + 1, r, temp);
            mergeArray(a, l, m, r, temp);
        }
    }


    private void mergeArray(int[] a, int l, int m, int r, int[] temp) {
        int left = l, mid = m, next = mid + 1, right = r, i = 0;

        while (left <= mid && next <= right) {
            if (a[left] < a[next])
                temp[i++] = a[left++];
            else
                temp[i++] = a[next++];
        }

        while (left <= mid)
            temp[i++] = a[left++];

        while (next <= right)
            temp[i++] = a[next++];

        for (int k = 0; k < i; k++) {
            a[l + k] = temp[k];
        }
    }


    private void quickSort(int a[], int left, int right) {
        if (left < right) {
            int start = left;
            int end = right;
            int d = a[start];
            while (start < end) {
                while (start < end && d <= a[end])
                    end--;
                if (start < end)
                    a[start++] = a[end];
                while (start < end && d > a[start])
                    start++;
                if (start < end)
                    a[end--] = a[start];
            }
            a[start] = d;
            quickSort(a, left, start - 1);
            quickSort(a, start + 1, right);
        }
    }


    private void shellSort() {

        int d = a.length;
        while (true) {
            d = (int) Math.ceil(d / 2);

            int n = 0;
            for (int i = n++; i < a.length; i = i + d) {
                int j = i - d;
                int temp = a[i];
                for (; j >= 0 && temp < a[j]; j = j - d) {
                    a[j + d] = a[j];
                }
                a[j + d] = temp;
            }

            if (d == 1)
                break;
        }
    }


    private void selectSort() {
        for (int i = 0; i < a.length; i++) {
            int j = i + 1;
            int temp = a[i];
            int pos = i;
            for (; j < a.length; j++) {
                if (temp > a[j]) {
                    temp = a[j];
                    pos = j;
                }
            }
            a[pos] = a[i];
            a[i] = temp;
        }
    }


    private void bubbleSort() {
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < a.length - 1 - i; j++) {
                if (a[j] > a[j + 1]) {
                    int temp = a[j];
                    a[j] = a[j + 1];
                    a[j + 1] = temp;
                }
            }
        }
    }

    private void insertSort() {
        for (int i = 0; i < a.length; i++) {
            int temp = a[i];
            int j = i - 1;
            for (; j >= 0 && temp < a[j]; j--) {
                a[j + 1] = a[j];
            }
            a[j + 1] = temp;
        }
    }


}
