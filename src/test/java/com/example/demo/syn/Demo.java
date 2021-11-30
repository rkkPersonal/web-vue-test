package com.example.demo.syn;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Demo {

    public static Lock lock=new ReentrantLock();

    public static void main(String[] args) {

  /*      lock.lock();
        System.out.println("第一次上锁："+Thread.currentThread().getName());

        lock.lock();
        System.out.println("第二次上锁："+Thread.currentThread().getName());


        lock.unlock();

        new Thread(()->{
            lock.lock();
        }).start();*/

        recursive();
    }


    public static void recursive(){

        lock.lock();;
        System.out.println("第一次上锁："+Thread.currentThread().getName());

        lock.lock();
        System.out.println("第二次上锁："+Thread.currentThread().getName());

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        recursive();

        lock.unlock();

    }
}
