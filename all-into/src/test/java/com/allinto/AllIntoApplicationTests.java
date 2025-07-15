package com.allinto;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.script.ScriptException;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
class AllIntoApplicationTests {

    @Test
    void contextLoads() throws IOException, ScriptException {
        File file = new File("D:\\data\\latlon.json");
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String ,Object> map = objectMapper.readValue(file, Map.class);


        Object objectOther = map.get("other");
        List<Map<String ,Object>> listMap4 = (List<Map<String ,Object>>) objectOther;


        Object object = map.get("municipalities");
        List<Map<String ,Object>> listMap1 = (List<Map<String ,Object>>) object;

        Object object1 = map.get("provinces");
        List<Map<String ,Object> > list1 = (List<Map<String ,Object>>) object1;

        List<Map<String ,Object>> listMap2 = new ArrayList<>();
        listMap2.addAll(list1);
// 3. 定义结果列表
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (Map<String, Object> stringObjectMap : list1) {
            Object object2 = stringObjectMap.get("cities");
            String data = object2.toString();
            // 1. 去除首尾的[]
            String content = data.substring(1, data.length() - 1);

            // 2. 分割为单个城市的字符串（处理分隔符}, {）
            String[] cityStrs = content.split("}, \\{");



            // 4. 遍历每个城市字符串，解析为Map
            for (String cityStr : cityStrs) {
                // 去除字符串中可能残留的{或}
                cityStr = cityStr.replaceAll("[{}]", "").trim();

                // 分割为n和g的键值对（按", "分割）
                String[] keyValues = cityStr.split(", ");

                // 创建当前城市的Map
                Map<String, Object> cityMap = new HashMap<>();
                for (String kv : keyValues) {
                    // 按=分割键和值（注意值中可能包含逗号，但这里键固定为n和g，值中逗号不影响）
                    String[] parts = kv.split("=", 2); // 限制分割为2部分，避免值中=的影响（虽然这里没有）
                    if (parts.length == 2) {
                        String key = parts[0].trim();
                        String value = parts[1].trim();
                        cityMap.put(key, value); // 存入Map，值类型为Object（这里实际是String）
                    }
                }
                resultList.add(cityMap);
            }
        }

        List<Map<String,Object>> finalList = new ArrayList<>();
        finalList.addAll(listMap1);
        finalList.addAll(listMap2);
        finalList.addAll(resultList);
        finalList.addAll(listMap4);

        File file1 = new File("D:\\data\\cityDataJson.json");
        File fileFinal = new File("D:\\data\\cityDataFinal.js");

        ObjectMapper objectMapper1 = new ObjectMapper();
        List<Map<String ,Object>> mapList23 = objectMapper1.readValue(file1, List.class);



        for (Map<String, Object> objectMap : mapList23) {
            Object object2 = objectMap.get("list");
            List<Map<String ,Object>> listMap12 = (List<Map<String ,Object>>) object2;
            for (Map<String, Object> finalList1Map : listMap12) {
                for (Map<String, Object> finalListMap : finalList) {
                    if(finalList1Map.get("name").toString().contains(finalListMap.get("n").toString())){
                        finalList1Map.put("lon", finalListMap.get("g").toString().split(",")[0]);
                        finalList1Map.put("lat",finalListMap.get("g").toString().split("\\|")[0].split(",")[1]);
                    }
                }
            }
        }
        String s1 = objectMapper1.writeValueAsString(mapList23);
        Files.write(fileFinal.toPath(), s1.getBytes(StandardCharsets.UTF_8));
        System.out.println(mapList23);
        System.out.println("111");
    }





}
