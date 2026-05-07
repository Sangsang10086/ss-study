package com.allinto.auth.rest;


import cn.hutool.http.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
class AuthControllerTest {


    private static List<Map<String, String>> getElements(String baseUrl, String hrefStr) {
        String response = HttpUtil.get(baseUrl + hrefStr);
        Document documentHtml = Jsoup.parse(response);
        //查找一级分类
        Element div = documentHtml.getElementsByClass("col-list").get(0);
        Elements divItems = div.getElementsByClass("col-item-a");
        List<Map<String, String>> list = new ArrayList<>();
        for (Element divItem : divItems) {
            String title = divItem.text();
            String href = divItem.attr("href");
            Map<String, String> map = new HashMap<>();
            map.put(href, title);
            list.add(map);
            List<Map<String, String>> listChild = getElements(baseUrl, href);
            list.addAll(listChild);
        }
        return list;
    }

    @Test
    void test() throws InterruptedException {
        String baseUrl = "https://www.cnhnb.com";
        getElements(baseUrl, "/p/");

    }


}