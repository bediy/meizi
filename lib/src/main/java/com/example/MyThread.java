package com.example;

/**
 * Created by Administrator on 2016/10/2/0002.
 */

public class MyThread {


    static class MyThread3 implements Runnable{

        //两个线程操作同一个对象，共享成员变量
        //int i;
        @Override
        public void run() {
            //两个线程操作同一个对象，各自保存局部变量的拷贝
            int i = 0;
            while(i<100){
                System.out.println(i);
                i++;
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {

        MyThread3 myThread = new MyThread3();
        //下面两个线程对同一个对象(Runnable的实现类对象)进行操作
        Thread thread = new Thread(myThread);
        Thread thread2 = new Thread(myThread);
        //各自保存局部变量的拷贝，互不影响，输出200个数字
        thread.start();
        thread2.start();
    }
}



