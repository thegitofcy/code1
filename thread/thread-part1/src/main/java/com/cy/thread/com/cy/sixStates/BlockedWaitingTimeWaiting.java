package com.cy.thread.com.cy.sixStates;

/**
 * @program: thread
 * @description: Thread 状态间转化. 此类演示 Blocked, Waiting, TimeWaiting 三种状态
 * @author: cy
 */
public class BlockedWaitingTimeWaiting implements Runnable{
    @Override
    public void run() {
        method();
    }

    public synchronized void method() {
        try {
            wait();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }


    public static void main(String[] args) throws InterruptedException {
        Thread thread1 = new Thread(new BlockedWaitingTimeWaiting());
        thread1.start();

        Thread thread2 = new Thread(new BlockedWaitingTimeWaiting());
        thread2.start();

        System.out.println("thread1: " + thread1.getState());
        System.out.println("thread2: " + thread2.getState());

        Thread.sleep(13000);
        System.out.println("thread1: " + thread1.getState());
        System.out.println("thread2: " + thread2.getState());
    }
}
