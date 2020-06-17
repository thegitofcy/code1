package com.cy.thread;

import com.cy.util.FileUtil;

/**
 * 创建线程, 用于下载网络文件
 */
public class FileDownThread extends Thread {

    private String url;
    private String targetName;

    public FileDownThread(String url, String targetName) {
        this.url = url;
        this.targetName = targetName;
    }

    @Override
    public void run() {
        FileUtil.download(url, targetName);
    }

    public static void main(String[] args) {
        FileDownThread t1 = new FileDownThread("https://www.baidu.com/img/flexible/logo/pc/result@2.png", "1.jpg");
        FileDownThread t2 = new FileDownThread("https://www.baidu.com/img/flexible/logo/pc/result@2.png", "2.jpg");
        FileDownThread t3 = new FileDownThread("https://www.baidu.com/img/flexible/logo/pc/result@2.png", "3.jpg");

        t1.start();
        t2.start();
        t3.start();

        for (int i = 0; i < 20; i++) {
            System.out.println("mian === " + i);
        }
    }
}

