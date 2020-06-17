package com.cy.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Slf4j
public class FileUtil {

    /**
     * 从网络地址下载文件
     * @param url 网络文件路径
     * @param targetName 下载名称
     */
    public static void download(String url, String targetName){
        try {
            FileUtils.copyURLToFile(new URL(url), new File(targetName));
            log.info("[{}] 路径文件下载完成, 名称为: [{}]", url, targetName);
        } catch (IOException e) {
            e.printStackTrace();
            log.error("IO 异常, File download 错误!");
        }
    }

}
