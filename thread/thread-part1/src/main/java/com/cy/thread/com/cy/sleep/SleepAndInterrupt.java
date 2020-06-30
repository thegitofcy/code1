package com.cy.thread.com.cy.sleep;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

/**
 * @program: thread
 * @description:
 * @author: cy
 */
public class SleepAndInterrupt {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            try {
                for (int i = 0; i < 100; i++) {
                    if(Thread.currentThread().isInterrupted()) break;
                    System.out.println(LocalDateTime.now());
                    TimeUnit.SECONDS.sleep(1);
                }
            } catch (InterruptedException e) {
                System.out.println("WTF!!!! 老子被中断了");
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(5000);
        thread.interrupt();
    }
}
