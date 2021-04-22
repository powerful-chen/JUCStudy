package com.chen.pc;

import java.util.Date;

/**
 * @ClassName A
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/4/19 7:27
 */
public class A {
    public static void main(String[] args) {

        Data data = new Data();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"A").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"B").start();

        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.increment();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"C").start();
        new Thread(()->{
            for (int i = 0; i < 10; i++) {
                try {
                    data.decrement();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        },"D").start();
    }
}

//判断等待，业务，通知
class Data { // 数字 资源类

    private int number = 0;

    //+1
    public synchronized void increment() throws InterruptedException {
        while (number != 0) {
            this.wait();
        }
        // 等待
        number++;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        //通知线程，我+1完毕了
        this.notifyAll();
    }

    //-1
    public synchronized void decrement() throws InterruptedException {
        while (number == 0) {
            //等待
            this.wait();
        }
        number--;
        System.out.println(Thread.currentThread().getName() + "=>" + number);
        //通知线程，我-1完毕了
        this.notifyAll();
    }

}