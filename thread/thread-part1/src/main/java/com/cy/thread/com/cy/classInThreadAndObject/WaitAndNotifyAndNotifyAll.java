package com.cy.thread.com.cy.classInThreadAndObject;


/**
 * @program: thread
 * @description: notify 和 notifyAll 的区别
 * 1: notify : 随机唤醒一个需要当前锁的线程
 * 2: notifyAll: 唤醒所有需要当前锁的线程
 * @author: cy
 */
public class WaitAndNotifyAndNotifyAll {

    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) throws InterruptedException {
        Runnable runnable1 = () -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + " 获取到锁, 开始执行");
                try {
                    System.out.println(Thread.currentThread().getName() + " 将要开始 wait, 会释放锁");
                    resourceA.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 再次获取到锁, 执行完毕");
            }
        };

        Runnable runnable2 = () -> {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName() + " 获取到锁, 开始执行, 将会执行 notifyAll");
                resourceA.notifyAll();
                System.out.println(Thread.currentThread().getName() + " 执行 notifyAll 完毕毕");
            }
        };

        // 当启动这个线程的时候, 当执行 wait 进入等待, 并释放锁后, 由于没有其他线程获取 resourceB锁, 导致没有线程可以
        // 调用 resourceB.notify方法,  也就导致没有线程可以唤醒 此线程, 所以陷入无尽的等待
        Runnable runnable13= () -> {
            synchronized (resourceB) {
                System.out.println(Thread.currentThread().getName() + " 获取到锁, 开始执行");
                try {
                    System.out.println(Thread.currentThread().getName() + " 将要开始 wait, 会释放锁");
                    resourceB.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + " 再次获取到锁, 执行完毕");
            }
        };



        new Thread(runnable1).start();
        new Thread(runnable1).start();
//        new Thread(runnable13).start();
        Thread.sleep(200);
        new Thread(runnable2).start();
    }
}
