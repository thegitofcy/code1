package com.cy.example.design;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DesignApplicationTests {

    @Test
    void contextLoads() {
        List<String> processJiChaDataStructure = new ArrayList<>();
        processJiChaDataStructure.add("fcaseBaoBei");
        processJiChaDataStructure.add("casFilBaoBei");
        processJiChaDataStructure.add("fcaseJieAn");
        processJiChaDataStructure.add("casFilJieAn");
        processJiChaDataStructure.add("casFilYiJiao");
        boolean xxx = processJiChaDataStructure.stream().anyMatch(str -> str.equals("fcaseBaoBei"));
        System.out.println(xxx);
    }

}
