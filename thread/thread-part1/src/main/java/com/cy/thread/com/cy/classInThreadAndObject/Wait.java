package com.cy.thread.com.cy.classInThreadAndObject;

/**
 * @program: thread
 * @description: 展示 wait 和 notify 的基本用法
 * 1: 代码执行顺序
 * 2. wait 释放锁
 * @author: cy
 */
public class Wait {

    private static Object object = new Object();

    static class Thread1 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " 获取到锁, 开始执行");
                try {
                    System.out.println(Thread.currentThread().getName() + " 将要调用 wait 方法, 调用 wait 会释放锁");
                    object.wait();  // 调用 wait 会释放锁. 当其他线程调用锁对象的 notify 方法后, 会唤醒此线程, 然后此线程可以继续执行.
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 再次获取到锁, 开始执行");
            }
        }
    }

    static class Thread2 extends Thread {
        @Override
        public void run() {
            synchronized (object) {
                System.out.println(Thread.currentThread().getName() + " 获取到锁, 开始执行");
                object.notify();    // 唤醒 等待 object 锁的线程.
                System.out.println(Thread.currentThread().getName() + " 执行完毕, 释放锁");
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();

        thread1.start();
        Thread.sleep(200);  // 保证 thread1 先执行,先获取到锁.
        thread2.start();
    }
}
