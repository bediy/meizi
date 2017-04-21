package com.example;

/**
 * Created by Ye on 2017/4/9/0009.
 */

interface MyInterface {
    void method1();
}

public abstract class MyAbstractClass implements MyInterface {

}

class MyNormalClass extends MyAbstractClass {
    @Override
    public void method1() {
        System.out.println("from method1");
    }
}