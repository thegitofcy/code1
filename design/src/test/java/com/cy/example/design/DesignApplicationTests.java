package com.cy.example.design;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;

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

    @Test
    void run() {
        List<Map<String, Object>> zaibans = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("rpt_zjj_cde", "1");
        map.put("years", 0);
        map.put("count", 2);
        zaibans.add(map);

        Map<String, Object> mapp = new HashMap<>();
        mapp.put("rpt_zjj_cde", "1");
        mapp.put("years", 0);
        mapp.put("count", 2);
        zaibans.add(mapp);

        Map<String, Object> map1= new HashMap<>();
        map1.put("rpt_zjj_cde", "1");
        map1.put("years", 2);
        map1.put("count", 1);
        zaibans.add(map1);

        Map<String, Object> map2= new HashMap<>();
        map2.put("rpt_zjj_cde", "1");
        map2.put("years", 4);
        map2.put("count", 3);
        zaibans.add(map2);

        Map<String, Object> map3= new HashMap<>();
        map3.put("rpt_zjj_cde", "4");
        map3.put("years", 3);
        map3.put("count", 1);
        zaibans.add(map3);

        Map<String, Object> map4= new HashMap<>();
        map4.put("rpt_zjj_cde", "4");
        map4.put("years", 4);
        map4.put("count", 2);
        zaibans.add(map4);

        Map<String, List<Integer>> list = new HashMap<>();
        makeZaiBanResult(zaibans, list);

        System.out.println();
    }


    public void makeZaiBanResult(List<Map<String, Object>> zaibans, Map<String, List<Integer>> list){
        for (Map<String, Object> zaiban : zaibans) {
            String rptZjjCde = (String)zaiban.get("rpt_zjj_cde");
            Integer years = (Integer)zaiban.get("years");
            Integer count = (Integer)zaiban.get("count");
            List<Integer> danweiZaiBans = list.get(rptZjjCde);
            if (null == danweiZaiBans) {
                danweiZaiBans = new ArrayList<>();
                for (int i = 0; i < 5; i++) {
                    danweiZaiBans.add(0);
                }
            }
            int index = -1;
            if (0 == years || 1 == years || 2 == years) {
                index = years;
            } else if (3 == years || 4 == years) {
                index = 3;
            } else {
                index = 4;
            }
            Integer integer = danweiZaiBans.get(index);
            danweiZaiBans.set(index, integer + count);
            list.put(rptZjjCde, danweiZaiBans);
        }
    }

    @Test
    public void run2() {
        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();
        set1.add("1");
        set1.add("2");
        set1.add("3");
        set2.add("4");
        set2.add("5");
        set2.add("6");
        set1.addAll(set2);

        List<String> newList = new ArrayList<>(set1);
        newList.forEach(s -> {
            System.out.print(s + ",");
        });
    }


    @Test
    public void run3() {
        List<String> lists = Arrays.asList("1", "2", "3");
        System.out.println(lists.contains("2"));
        String[] danweiArr = {"4","5","2","3","1"};

        for (int i = 0; i < lists.size(); i++) {
            String s = lists.get(i);
            for (int j = i + 1; j < danweiArr.length; j++) {
                if (s.equals(danweiArr[j])) {
                    String tmp = danweiArr[i];
                    danweiArr[i] = danweiArr[j];
                    danweiArr[j] = tmp;
                    break;
                }
            }
        }

        List<String> strings = Arrays.asList(danweiArr);
        strings.forEach(s -> System.out.print(s + ","));
    }
}
