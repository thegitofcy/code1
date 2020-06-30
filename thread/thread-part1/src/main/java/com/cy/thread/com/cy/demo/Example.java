package com.cy.thread.com.cy.demo;

import lombok.SneakyThrows;

/**
 * @program: thread
 * @description: 2个线程交替打印 0~100 的奇数偶数
 * @author: cy
 */
public class Example {
    private static int count = 0;
    private static final Object object = new Object();

    public static void main(String[] args) {
//        basedOnSynchronized();
        basedOnWaitAndNotify();
    }

    /**
     * 基于 synchronized
     */
    public static void basedOnSynchronized() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while ( count < 100) {
                    synchronized (object) {
                        if ((count & 1) == 0) {
                            System.out.println(Thread.currentThread().getName() + " : " + count ++);
                        }
                    }
                }
            }
        }, "偶数").start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                while ( count < 100) {
                    synchronized (object) {
                        if ((count & 1) == 1) {
                            System.out.println(Thread.currentThread().getName() + " : " + count++);
                        }
                    }
                }
            }
        }, "奇数").start();
    }


    public static void  basedOnWaitAndNotify() {
        new Thread(new TurningRunner(), "偶数").start();
        new Thread(new TurningRunner(), "奇数").start();
    }

    static class TurningRunner implements Runnable {
        @Override
        public void run() {
            while (count <= 100) {
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + " : " + count ++);
                    object.notify();
                    if (count <= 100) {
                        try {
                            object.wait();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}
