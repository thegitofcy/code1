package com.cy.thread.com.cy.stop;

/**
 * @program: thread
 * @description: 实际开发中, 停止线程的最佳方式一: 在方法上抛出异常.
 * @author: cy
 */
public class StopThreadInProd1 implements Runnable {

    @Override
    public void run() {
        try {// sleep 会清除掉 interrupt 标记, 所以 try/catch 放在 while 里边, 不会停止线程, 需要放在 while 外边.
            while (true && !Thread.currentThread().isInterrupted()) {
                System.out.println("run....");
                throwInterruptedException();
            }
        } catch (InterruptedException e) {
            System.out.println("进行保存日志逻辑");
            e.printStackTrace();
        }
    }

    // 不能在方法内用 try/catch, 需要在方法上抛出异常, 这样在 run()方法中就会强制去 try/catch
    public void throwInterruptedException() throws InterruptedException {
        Thread.sleep(2000);
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThreadInProd1());

        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
    }
}
