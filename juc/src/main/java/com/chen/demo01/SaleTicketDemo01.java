package com.chen.demo01;

/**
 * @ClassName SaleTicketDemo01
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/4/17 9:55
 */
// 真正的多线程开发，线程就是一个单独的资源类，没有任何附属的操作
public class SaleTicketDemo01 {
    public static void main(String[] args) {
        // 并发：多线程操作同一个资源类，把资源类丢入线程
        Ticket ticket = new Ticket();

        new Thread(() -> {
            for (int i = 1; i < 60; i++) {
                ticket.sale();
            }
        }, "A").start();
        new Thread(() -> {
            for (int i = 1; i < 60; i++) {
                ticket.sale();
            }
        }, "B").start();
        new Thread(() -> {
            for (int i = 1; i < 60; i++) {
                ticket.sale();
            }
        }, "C").start();
    }

}

// 资源类 OOP
class Ticket {
    // 属性、方法
    private int number = 50;

    // 卖票的方式
    public synchronized void sale() {
        if (number > 0) {
            System.out.println(Thread.currentThread().getName() + "卖出了" + (number--) + "票，剩余：" + number);
        }
    }

}
