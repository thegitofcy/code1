package com.cy.thread;

import lombok.extern.slf4j.Slf4j;

/**
 * 龟兔赛跑
 */
@Slf4j
public class RaceThread implements Runnable {

    private static String winner;

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (getWinner(i)) {
                break;
            }
            log.info("[{}] 已经跑了 [{}] 米!", Thread.currentThread().getName(), i+1);
        }
    }

    public boolean getWinner(int m){
        if(winner != null) {
            return true;
        } else {
            if (m >= 99) {
                winner = Thread.currentThread().getName();
                log.info("winner is [{}]", winner);
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        RaceThread raceThread = new RaceThread();

        new Thread(raceThread, "乌龟").start();
        new Thread(raceThread, "兔子").start();
    }
}
