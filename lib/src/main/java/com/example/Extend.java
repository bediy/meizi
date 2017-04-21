package com.example;

/**
 * Created by Ye on 2017/4/17/0017.
 */


class C {
    C() {
        System.out.print("C");
    }
}

class A extends C{
    C c = new C();

    A() {
        this("A");
        System.out.print("A");
    }

    A(String s) {
        System.out.print(s);
    }
}

class Test extends A {
    Test() {
        super("B");
        System.out.print("B");
    }

    public static void main(String[] args) {
        new Test();
    }
}
