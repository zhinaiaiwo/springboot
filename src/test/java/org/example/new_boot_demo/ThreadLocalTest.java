package org.example.new_boot_demo;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {

    @Test
    public void ThreadLocalTestGet(){
        ThreadLocal tl = new ThreadLocal();

        new Thread(()->{
            tl.set("张三");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
        }, "第一").start();

        new Thread(()->{
            tl.set("李四");
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
            System.out.println(Thread.currentThread().getName() + ": " + tl.get());
        }, "第二").start();
    }
}
