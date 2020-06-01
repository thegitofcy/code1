package com.example.jizhen.utils;

import com.alibaba.excel.ExcelReader;
import com.alibaba.excel.metadata.ExcelHeadProperty;
import com.alibaba.excel.metadata.Sheet;
import com.alibaba.excel.read.context.AnalysisContext;
import com.alibaba.excel.read.event.AnalysisEventListener;
import com.alibaba.excel.support.ExcelTypeEnum;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.apache.commons.codec.Resources.getInputStream;

@Slf4j
@Data
public class ExcelUtils {

    public static void getExcelFields(File file){

    }

    /**
     * 读 Excel
     */
    public static List<Map<Integer, String>> readExcel(File file) throws FileNotFoundException {
        List<Map<Integer, String>> datas = new ArrayList<Map<Integer, String>>();
        InputStream inputStream = new FileInputStream(file);
        try {
            ExcelReader reader = new ExcelReader(inputStream, ExcelTypeEnum.XLSX, null,
                    new AnalysisEventListener<Map<Integer, String>>() {
                        @Override
                        public void invoke(Map<Integer, String> data, AnalysisContext context) {
                            Sheet currentSheet = context.getCurrentSheet();
                            log.info("scan [{}], current SheetNo: [{}], currentRowNum: [{}]",
                                    file.getAbsoluteFile(),
                                    context.getCurrentSheet().getSheetNo(),
                                    context.getCurrentRowNum());
                            datas.add(data);
                        }
                        @Override
                        public void doAfterAllAnalysed(AnalysisContext context) {

                        }

                    });
            reader.read();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return datas;
    }
}
