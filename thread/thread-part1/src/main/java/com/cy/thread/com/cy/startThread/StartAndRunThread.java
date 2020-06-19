package com.cy.thread.com.cy.startThread;

/**
 * @program: thread
 * @description: 对比 start()  和 run() 两种方式
 * @author: cy
 */
public class StartAndRunThread {

    public static void main(String[] args) {
        Runnable runnable = () -> System.out.println(Thread.currentThread().getName());
        runnable.run();
        new Thread(runnable).start();
    }
}
