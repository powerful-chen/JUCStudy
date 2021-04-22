package com.chen.demo01;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName SaleTicketDemo01
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/4/17 9:55
 */
// 真正的多线程开发，线程就是一个单独的资源类，没有任何附属的操作
public class SaleTicketDemo02 {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类，把资源类丢入线程
        Ticket2 ticket = new Ticket2();
        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i < 40; i++) {
                ticket.sale();
            }
        }, "C").start();

    }

}

// lock三部曲
// 1、new ReentrantLock();
// 2、lock.lock();//加锁
// 3、lock.unlock();//解锁
class Ticket2 {
    // 属性、方法
    private int number = 30;

    Lock lock = new ReentrantLock();

    // 卖票的方式
    public void sale() {
        lock.lock();//加锁

        try {
            if (number > 0) {
                System.out.println(Thread.currentThread().getName() + "卖出了" + (number--) + "票，剩余：" + number);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            lock.unlock();//解锁
        }
    }

}
