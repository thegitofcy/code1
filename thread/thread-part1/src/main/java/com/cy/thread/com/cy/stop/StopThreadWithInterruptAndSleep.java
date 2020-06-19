package com.cy.thread.com.cy.stop;

/**
 * @program: thread  演示
 * @description: sleep 的时候, 检测到 interrupt, 会报出异常, 通过异常停止线程
 * @author: cy
 */
public class StopThreadWithInterruptAndSleep {

    public static void main(String[] args) throws InterruptedException {
//        stopByInterrupt();
//        stopByInterruptWithSleep();
        stopByInterruptWithSleepEveryLoop();
    }

    /** 普通情况下停止线程.*/
    public static void stopByInterrupt() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            while (num < Integer.MAX_VALUE / 2 && !Thread.currentThread().isInterrupted()) {
                if (num % 10000 == 0) {
                    System.out.println(num + " 是 10000 的倍数!");
                }
                num++;
            }
            System.out.println("线程运行完毕");
        });

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }

    /** 阻塞情况下停止线程.*/
    public static void stopByInterruptWithSleep() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            while (num < Integer.MAX_VALUE / 2 && !Thread.currentThread().isInterrupted()) {
                if (num % 10000 == 0) {
                    System.out.println(num + " 是10000 的倍数!");
                }
                num++;
            }
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程运行完毕");
        });

        thread.start();
        Thread.sleep(100);
        thread.interrupt();
    }

    /** 每次循环都阻塞的情况下停止线程.*/
    public static void stopByInterruptWithSleepEveryLoop() throws InterruptedException {
        Thread thread = new Thread(() -> {
            int num = 0;
            try {
                while (num < Integer.MAX_VALUE / 2 && !Thread.currentThread().isInterrupted()) {
                    if (num % 10000 == 0) {
                        System.out.println(num + " 是10000 的倍数!");
                    }
                    num++;
                    Thread.sleep(10000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("线程运行完毕");
        });

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
