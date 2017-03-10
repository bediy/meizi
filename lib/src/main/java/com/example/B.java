package com.example;

/**
 * Created by Administrator on 2017/2/14/0014.
 */

class Person {
    String name = "No name";

    Person() {

    }

    public Person(String nm) {
        name = nm;
    }
}

class Employee extends Person {
    String empID = "0000" + 100;

    public Employee(String id) {
        super();
        empID = id;
    }
}


class B {

    public static B t1 = new B();
    public static B t2 = new B();

    {
        System.out.println("构造块");
    }

    static {
        System.out.println("静态块");
    }

    public static void main(String args[]) {
        /*Employee e = new Employee("123");
        System.out.println(e.empID);
        Long l = 42L;
        System.out.print(l.equals(42));*/
//        B b = new B();
        System.out.print(42 == 42.0f);
    }

}
