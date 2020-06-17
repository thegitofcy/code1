package com.cy.thread;

public class PrimeRunnable implements Runnable{
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("PrimeRunnable === " + i);
        }
    }

    public static void main(String[] args) {
        new Thread(new PrimeRunnable()).start();

        for (int i = 0; i < 200; i++) {
            System.out.println("mian === " + i);
        }
    }
}
