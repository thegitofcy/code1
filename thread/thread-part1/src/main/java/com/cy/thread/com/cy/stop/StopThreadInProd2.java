package com.cy.thread.com.cy.stop;

/**
 * @program: thread
 * @description: 实际开发中, 停止线程的最佳方式一: 在catch 中再次打上 interrupt 标记
 * @author: cy
 */
public class StopThreadInProd2 implements Runnable {

    @Override
    public void run() {
        while (true) {
            if (!Thread.currentThread().isInterrupted()) {
                System.out.println("Interrupted, 停止线程");
                break;
            }
            System.out.println("run....");
            reInterrupt();
        }
    }

    // 在 catch 中, 再次打 interrupt 标记. 相当于在 sleep 清除标记后, 重新将中断标记恢复
    public void reInterrupt() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadInProd2());

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
