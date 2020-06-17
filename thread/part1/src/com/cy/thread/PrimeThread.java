package com.cy.thread;

public class PrimeThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 20; i++) {
            System.out.println("PrimeThread --- " + i);
        }
    }

    public static void main(String[] args) {

        PrimeThread primeThread = new PrimeThread();
        primeThread.start();

        for (int i = 0; i < 20; i++) {
            System.out.println("main --- " + i);
        }
    }
}
