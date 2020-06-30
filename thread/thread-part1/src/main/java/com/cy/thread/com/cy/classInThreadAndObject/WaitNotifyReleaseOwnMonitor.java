package com.cy.thread.com.cy.classInThreadAndObject;

/**
 * @program: thread
 * @description: 证明 wait 只释放当前的那把锁
 * @author: cy
 */
public class WaitNotifyReleaseOwnMonitor {

    private static final Object resourceA = new Object();
    private static final Object resourceB = new Object();

    public static void main(String[] args) {
        Runnable runnable1 = () -> {
            synchronized (resourceA) {  // 1. 当前线程获取 resourceA 锁
                System.out.println("ThreadA 获取到 resourceA 锁");
                synchronized (resourceB) {  // 2. 当前线程获取 resourceB 锁
                    System.out.println("ThreadA 获取到 resourceB 锁");
                    System.out.println("ThreadA 释放 resourceA 锁");
                    try {
                        resourceA.wait();   // 3. 当前线程释放 resourceA 锁
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("ThreadA 重新获取到 resourceA 锁");
                }
            }
        };

        Runnable runnable2 = () -> {
            synchronized (resourceA) {
                System.out.println("ThreadB 获取到 resourceA 锁");  // 4. 线程 A 释放掉了 resourceA , 所以这里可以获取到锁
                synchronized (resourceB) {  // 5. 线程 A 没有执行 resourceB.wait, 所以也就没有释放 resourceB, 所以线程 B 获取不到 resourceB
                    System.out.println("ThreadB 获取到 resourceB 锁");
                }
            }
        };

        new Thread(runnable1).start();
        new Thread(runnable2).start();
    }
}
