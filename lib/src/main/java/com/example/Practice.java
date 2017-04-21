package com.example;

/**
 * Created by Administrator on 2016/10/12/0012.
 */


public class Practice {

    static int[] n = {2, 4, 6, 8, 2, 11, 5, 7, 345, 61, 97, 234, 22};


    public void quickSort(int[] n, int left, int right) {
        if (left < right) {
            int low = left;
            int high = right;
            int pivot = n[low];
            while (left < right) {
                while (pivot <= n[high] && left < right) {
                    high--;
                }
                n[low] = n[high];
                while (pivot >= n[low] && left < right) {
                    low++;
                }
                n[high] = n[low];
            }
            n[low] = pivot;
            quickSort(n, left, low - 1);
            quickSort(n, low + 1, right);
        }
    }


    public void selectSort() {
        for (int i = 0; i < n.length; i++) {
            int j = i;
            int min = n[i];
            int position = i;
            for (;j < n.length; j++) {
                if (min > n[j]) {
                    min = n[j];
                    position = j;
                }
            }
            n[position] = n[i];
            n[i] = min;
        }
    }

    public void insertSort() {
        for (int i = 1; i < n.length; i++) {
            int temp = n[i];
            int j = i - 1;
            for (; j >= 0 && temp < n[j]; j--) {
                n[j + 1] = n[j];
            }
            n[j + 1] = temp;
        }
    }

    public void shellSort() {
        int d = n.length;
        while (true) {
            d = (int) Math.ceil(d / 2);

            for (int x = 0; x < d; x++) {

                for (int i = x + d; i < n.length; i += d) {
                    int temp = n[i];
                    int j = i - d;
                    for (; j >= 0 && temp < n[j]; j -= d) {
                        n[j + d] = n[j];
                    }
                    n[j + d] = temp;
                }

            }

            if (d == 1) {
                break;
            }
        }
    }


    static class Node {
        Node left;
        Node right;
        int value;
    }

    static int leaveCount(Node node) {
        if (node == null) {
            return 0;
        }

        if (node.left == null && node.right == null) {
            return 1;
        }

        return leaveCount(node.left) + leaveCount(node.right);
    }


    public static Node createTree(Node node, int value) {
        if (node == null) {
            node = new Node();
            node.value = value;
            return node;
        }

        if (node.value <= value) {
            node.right = createTree(node.right, value);
        } else {
            node.left = createTree(node.left, value);
        }

        return node;

    }

    public static void printTree(Node node) {
        if (node != null) {
            printTree(node.left);
            System.out.print(node.value + " ");
            printTree(node.right);
        }
    }

    public static int heightTree(Node node) {
        if (node == null) {
            return 0;
        }

        return 1 + Math.max(heightTree(node.left), heightTree(node.right));
    }


    public static void main(String[] args) {

        /*Node node = null;

        for (int value : a) {
            node = createTree(node, value);
        }

//        printTree(node);

        System.out.print(leaveCount(node));*/

        new Practice().selectSort();

        for (int value : n) {
            System.out.print(value + " ");
        }
    }
}
