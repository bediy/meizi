package com.example;

public class MyClass {

    public class A {
    }
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

    public static void main(String[] args) {

        char[] chars = s.toCharArray();
        char[] sps = sp.toCharArray();

        String[] results = new String[chars.length];
        int n = 0;
        int oi = 0;

        for (int i = 0; i < chars.length; i++) {
            for (int j = 0; j < sps.length; j++) {
                if (chars[i + j] == sps[j]) {
                    if (j == sps.length - 1) {
                        results[n] = getTheS(i, oi);
                        oi = i + sps.length;
                        n++;
                    }
                } else {
                    break;
                }
            }
            if (i == chars.length - 1) {
                results[n] = getTheS(i + 1, oi);
            }
        }

        System.out.print(results.toString());

    }

    private static String getTheS(int start, int oi) {

        char[] chars = new char[start - oi];

        for (int i = 0; i < start - oi; i++) {
            chars[i] = s.charAt(oi + i);
        }

        return String.valueOf(chars);
    }

    private static int get() {
        int n = 77;

        for (int i = n; i > 0; i--) {
            for (int j = 1; j <= n; j++) {
                if (i % j == 0) {
                    if (i != j && j != 1) {
                        break;
                    } else if (i == j) {
                        return i;
                    }
                }
            }
        }
        return 0;
    }

    private static void a() {
        int t, i, j;

        for (i = 3; i <= 100; i++) {
            t = 1;
            for (j = 2; j <= (i / 2); j++)
                if (i % j == 0)
                    t = 0;
            if (t == 1)
                System.out.printf("%d ", i);
        }
    }
}
