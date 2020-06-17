package com.cy.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 模拟卖票
 */
@Slf4j
public class TicketThread implements Runnable {

    private int ticketNums = 10;

    @Override
    public synchronized void run() {
        while (ticketNums > 0) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            log.info("[{}] 拿到了第 [{}] 张票!", Thread.currentThread().getName(), ticketNums--);
        }
    }

    public static void main(String[] args) {
        TicketThread ticketThread = new TicketThread();

        new Thread(ticketThread, "老王").start();
        new Thread(ticketThread, "老张").start();
        new Thread(ticketThread, "黄牛").start();
    }
}
