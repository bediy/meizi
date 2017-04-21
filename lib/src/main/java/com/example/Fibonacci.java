package com.example;

import java.util.ArrayList;

/**
 * Created by Ye on 2017/3/14/0014.
 */

public class Fibonacci {
    public int Fibonacci(int n) {

        if (n == 0) {
            return 0;
        } else if (n == 1) {
            return 1;
        } else {
            int F = 0;
            int pre = 0;
            int next = 1;
            for (int i = 0; i < n - 1; i++ ) {
                F = pre + next;
                pre = next;
                next = F;
            }
            return F;
        }


    }
}
