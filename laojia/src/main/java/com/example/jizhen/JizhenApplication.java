package com.example.jizhen;

import com.example.jizhen.utils.ExcelUtils;
import com.example.jizhen.utils.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Slf4j
@SpringBootApplication
public class JizhenApplication {

    public static void main(String[] args) throws FileNotFoundException {
        SpringApplication.run(JizhenApplication.class, args);
        process();
    }

    public static void process() {
//        String jarPath = FileUtils.getJarPath();
        String jarPath = "/Users/cy/Desktop/test";
        log.info("扫描文件...路径: [{}]", jarPath);
        List<File> files = FileUtils.scanFilesByType(jarPath, ".xlsx");
        List<Object> fields = new ArrayList<>();
        files.forEach(file -> {
            try {
                List<Map<Integer, String>> datas = ExcelUtils.readExcel(file);
                log.info("scan [{}] 条数据", datas.size());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        });
    }
}
