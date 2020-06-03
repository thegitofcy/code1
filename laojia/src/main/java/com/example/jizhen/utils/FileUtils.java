package com.example.jizhen.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Slf4j
public class FileUtils {

    private static List<File> files = new ArrayList<>();
    /**
     * 扫描路径下所有文件
     * @param path
     * @return
     */
    public static List<File> scanFiles(String path) {
        File baseFile  = new File(path);
        List<File> filesInPath = new ArrayList<>(Arrays.asList(baseFile.listFiles()));
        if (filesInPath.size() == 0) {
            log.info("there is no file in path: [{}]", path);
        }
        filesInPath.forEach(file -> {
            if (file.isFile()) files.add(file);
            else if (file.isDirectory()) {
                scanFiles(file.getAbsolutePath());
            }
        });
        return files;
    }

    /**
     * 扫描在指定路径下所有 type 类型的文件.
     * @param path
     * @param type
     * @return
     */
    public static List<File> scanFilesByType(String path, String type) {
        if (!type.startsWith(".")) {
           throw new IllegalArgumentException("The type of file must be start witeh '.'");
        }
        List<File> files = scanFiles(path);
        List<File> filesByType = new ArrayList<>();
        files.forEach(file -> {
            String name = file.getName().toUpperCase();
            if (name.endsWith(type.toUpperCase())) {
                log.info("scan file: [{}]", file.getAbsoluteFile());
                filesByType.add(file);
            }
        });
        log.info("[{}] files type: [{}] were be scanned!",filesByType.size(), type);
        return filesByType;
    }

    public static String getJarPath() throws FileNotFoundException {
        return  new File(ResourceUtils.getURL("classpath:").getPath()).getParentFile().getParentFile().getParent();
    }
}
