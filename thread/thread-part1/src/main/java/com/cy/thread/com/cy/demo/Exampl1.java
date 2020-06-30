package com.cy.thread.com.cy.demo;

/**
 * @program: thread
 * @description:
 * @author: cy
 */
public class Exampl1 {

    private static int count = 0;
    private static final Object object = new Object();

    static class Runner implements Runnable {

        @Override
        public void run() {
            while (count <= 100) {
                synchronized (object) {
                    System.out.println(Thread.currentThread().getName() + ": " + count++);
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

    public static void main(String[] args) throws InterruptedException {
        new Thread(new Runner(), "偶数").start();
        Thread.sleep(50);
        new Thread(new Runner(), "奇数").start();
    }

}
