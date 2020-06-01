package com.example.jizhen;

import com.example.jizhen.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

@Slf4j
@SpringBootApplication
public class JizhenApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(JizhenApplication.class, args);
        process();
    }

    public static void process() throws FileNotFoundException {
        String jarPath = FileUtils.getJarPath();
        log.info("扫描文件...路径: [{}]", jarPath);
        List<File> files = FileUtils.scanFiles(jarPath);
    }

}
