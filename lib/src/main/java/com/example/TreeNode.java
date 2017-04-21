package com.example;

/**
 * Created by Administrator on 2016/8/27.
 */
public class TreeNode {


    static int[] n = {2, 4, 6, 8, 2, 11, 5, 7, 345, 61, 97, 234, 22};

    private static void mergeArray(int a[], int first, int mid, int last, int temp[]) {
        int i = first, j = mid + 1;
        int k = 0;
        int m = mid;
        int n = last;
        while (i <= m && j <= n) {
            if (a[i] <= a[j])
                temp[k++] = a[i++];
            else
                temp[k++] = a[j++];

        }

        while (i <= m) {
            temp[k++] = a[i++];
        }

        while (j <= n) {
            temp[k++] = a[j++];
        }

        for (int x = 0; x < k; x++) {
            a[first + x] = temp[x];
        }
    }

    private static void mergeSort(int a[], int left, int right, int temp[]) {
        if (left < right) {
            int mid = (left + right) / 2;
            mergeSort(a, left, mid, temp);
            mergeSort(a, mid + 1, right, temp);
            mergeArray(a, left, mid, right, temp);
        }
    }


    private static void pickPrimeNumber() {
        for (int i = 0; i < n.length; i++) {
            boolean isPrimeNumber = true;
            int s = (int) Math.sqrt(n[i]);
            for (int j = 2; j <= s; j++) {
                if (n[i] % j == 0) {
                    isPrimeNumber = false;
                    break;
                }
            }
            if (isPrimeNumber)
                System.out.print(n[i] + ", ");
        }
    }


    private static void quickSortCopy(int[] a, int left, int right) {
        if (left < right) {

            int low = left;
            int high = right;
            int key = a[low];
            while (low < high) {
                while (low < high && key <= a[high]) {
                    high--;
                }
                a[low] = a[high];
                while (low < high && key >= a[low]) {
                    low++;
                }
                a[high] = a[low];
            }
            a[low] = key;
            quickSort(a, low + 1, right);
            quickSort(a, left, low - 1);

        }

    }


    //快速排序
    private static void quickSort(int[] array, int left, int right) {
        if (left < right) {
            int key = array[left];
            int low = left;
            int high = right;
            while (low < high) {
                while (low < high && array[high] >= key) {
                    high--;
                }
                array[low] = array[high];
                while (low < high && array[low] <= key) {
                    low++;
                }
                array[high] = array[low];
            }
            array[low] = key;
            quickSort(array, left, low - 1);
            quickSort(array, low + 1, right);
        }

    }

    //直接插入排序
    private static void insertSort() {
        for (int i = 1; i < n.length; i++) {
            int temp = n[i];
            int j = i - 1;
            for (; j >= 0 && temp < n[j]; j--) {
                n[j + 1] = n[j];
            }
            n[j + 1] = temp;
        }
    }

    //希尔排序
    private static void shellSort() {
        double d1 = n.length;
        while (true) {
            d1 = Math.ceil(d1 / 2);
            int d = (int) d1;

            for (int x = 0; x < d; x++) {


                for (int i = x; i < n.length; i += d) {
                    int j = i - d;
                    int temp = n[i];
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

    //简单选择排序
    private static void selectSort() {
        int min = 0;
        int position = 0;
        for (int i = 0; i < n.length; i++) {
            min = n[i];
            position = i;
            for (int j = i; j < n.length; j++) {
                if (n[j] < min) {
                    min = n[j];
                    position = j;
                }
            }
            n[position] = n[i];
            n[i] = min;
        }
    }

    static class Node {
        int value;
        Node leftChild;
        Node rightChild;
    }

    static Node insertNode(Node node, int value) {
        if (node == null) {
            node = new Node();
            node.value = value;
            node.leftChild = null;
            node.rightChild = null;
        } else if (value < node.value) {
            node.leftChild = insertNode(node.leftChild, value);
        } else if (value >= node.value) {
            node.rightChild = insertNode(node.rightChild, value);
        }
        return node;
    }

    static void printNode(Node node) {
        if (node != null) {
            printNode(node.leftChild);
            System.out.print(node.value);
            printNode(node.rightChild);
        }

    }

    private static int getLeafCount(Node H) {
        if (H == null) {
            return 0;
        }

        if (H.leftChild == null && H.rightChild == null) {
            return 1;
        }

        return getLeafCount(H.leftChild) + getLeafCount(H.rightChild);
    }

    private static int getHeight(Node H) {
        if (H == null) {
            return 0;
        }

        if (H.leftChild == null && H.rightChild == null) {
            return 1;
        }

        return getHeight(H.leftChild) >= getHeight(H.rightChild) ? getHeight(H.leftChild) + 1 : getHeight(H.rightChild) + 1;
    }

    private static Node inverTree(Node node) {
        if (node == null) {
            return null;
        }
        Node temp;

        temp = node.leftChild;
        node.leftChild = node.rightChild;
        node.rightChild = temp;

        inverTree(node.leftChild);
        inverTree(node.rightChild);
        return node;
    }


    static class Student {
        public Student(int id, int old) {
            this.id = id;
            this.old = old;
        }

        private int id;
        private int old;
    }

    private static void plus(int i) {
        i = 3;
    }

    public static void main(String[] args) {
        /*Node node = null;
        for (int value : a) {
            node = insertNode(node, value);
        }

        System.out.print("height:" + getHeight(node) + "\a");
        System.out.print("leaf count:" + getLeafCount(node) + "\a");
        printNode(node);*/

//        quickSort(a, 0, a.length - 1);

        /*quickSortCopy(a, 0, a.length - 1);
        for (int i = 0; i < a.length; i++) {
            System.out.print(a[i] + ", ");
        }*/

//        pickPrimeNumber();

        int i = 0;
        plus(i);
        System.out.print(i);
    }
}
