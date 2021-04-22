package com.chen.unsafe;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @ClassName ListTest
 * @Description TODO
 * @Author xiaochen
 * @Date 2021/4/19 9:27
 */
// java.util.ConcurrentModificationException 并发修改异常
public class ListTest {
    public static void main(String[] args) {
        // 并发下 ArrayList 不安全，用Synchronized
        /**
         * 解决方案
         * 1、List<String> list = new Vector<>()
         * 2、List<String> list = Collections.synchronizedList(new ArrayList<>());
         * 3、List<String> list = new CopyOnWriteArrayList<>();
         */
        // CopyOnWrite 写入时复制 COW 计算机程序设计领域的一种优化策略；
        // 多个线程调用的时候，List，读取的时候，固定的，写入（覆盖）
        // 在写入的时候避免覆盖，造成数据问题
        List<String> list = new CopyOnWriteArrayList<>();
        for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                list.add(UUID.randomUUID().toString().substring(0, 5));
                System.out.println(list);
            }, String.valueOf(i)).start();
        }

    }
}
