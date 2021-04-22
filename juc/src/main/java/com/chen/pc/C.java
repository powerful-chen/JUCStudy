package com.chen.pc;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName C
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/4/19 8:19
 */
//按顺序执行 A->B->C
public class C {
    public static void main(String[] args) {

        Data3 data = new Data3();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printA();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printB();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                data.printC();
            }
        }, "C").start();
    }
}

class Data3 { //资源类

    private Lock lock = new ReentrantLock();
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();
    private int number = 1; //当number为1时执行A 1A 2B 3C

    public void printA() {
        lock.lock();
        try {
            // 业务，判断->执行->通知
            while (number != 1) {
                // 等待
                condition1.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>AAAAAAA");
            number = 2;
            //唤醒，唤醒指定的人B
            condition2.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printB() {
        lock.lock();
        try {
            // 业务，判断->执行->通知
            while (number != 2) {
                // 等待
                condition2.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>BBBBBBB");
            number = 3;
            //唤醒，唤醒指定的人C
            condition3.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void printC() {
        lock.lock();
        try {
            // 业务，判断->执行->通知
            while (number != 3) {
                // 等待
                condition3.await();
            }
            System.out.println(Thread.currentThread().getName() + "=>CCCCC");
            number = 1;
            //唤醒，唤醒指定的人A
            condition1.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }


}
