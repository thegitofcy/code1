package com.cy.thread;


import com.cy.util.FileUtil;

import java.util.concurrent.*;

/**
 * 实现 Callable 的方式创建线程
 * 1. 实现 Callable 接口, 并指定返回值泛型
 * 2. 重写 call 方法
 * 3. 创建对象
 * 4. 创建 ExecutorService 服务
 * 5. 提交执行
 * 6. 获取返回值
 * 7. 关闭服务
 */
public class CallableExample implements Callable<Boolean> {

    private String url;
    private String targetName;

    public CallableExample(String url, String targetName) {
        this.url = url;
        this.targetName = targetName;
    }

    @Override
    public Boolean call() throws Exception {
        FileUtil.download(url, targetName);
        return true;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CallableExample callable1 = new CallableExample("https://www.baidu.com/img/flexible/logo/pc/result@2.png", "1.jpg");
        CallableExample callable2 = new CallableExample("https://www.baidu.com/img/flexible/logo/pc/result@2.png", "2.jpg");
        CallableExample callable3 = new CallableExample("https://www.baidu.com/img/flexible/logo/pc/result@2.png", "3.jpg");

        // 创建线程池
        ExecutorService executorService = Executors.newFixedThreadPool(3);

        // 提交执行
        Future<Boolean> submit1 = executorService.submit(callable1);
        Future<Boolean> submit2 = executorService.submit(callable2);
        Future<Boolean> submit3 = executorService.submit(callable3);

        // 获取返回值
        Boolean aBoolean1 = submit1.get();
        Boolean aBoolean2 = submit2.get();
        Boolean aBoolean3 = submit3.get();

        // 关闭服务
        executorService.shutdown();
    }
}
