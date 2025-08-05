package com.http.rest;

import org.apache.catalina.User;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class RestTemplateController {

    public void get(){

        RestTemplate restTemplate = new RestTemplate();
        /**
         * getForObject
         */

        // 带路径参数（占位符 {id}）
        User user = restTemplate.getForObject(
                "https://api.example.com/user/{id}",
                User.class,
                1  // 替换 {id} 的值
        );


        // 带查询参数（通过 Map 传递）
        Map<String, Object> params = new HashMap<>();
        params.put("name", "Alice");
        params.put("age", 20);
        List<User> users = restTemplate.getForObject(
                "https://api.example.com/users?name={name}&age={age}",
                List.class,
                params
        );

        // 返回对象
        User forObject = restTemplate.getForObject("http://localhost:8080/http/HelloHTTP", User.class);

        // 返回字符串
        String forObject1 = restTemplate.getForObject("http://localhost:8080/http/HelloHTTP", String.class);

        //返回整数（如状态码）
        Integer forObject2 = restTemplate.getForObject("http://localhost:8080/http/HelloHTTP", Integer.class);


        //getForEntity()
        ResponseEntity<User> response = restTemplate.getForEntity(
                "https://api.example.com/user/1",
                User.class
        );

        /**
         * exchange
         */
        //返回响应实体
        ResponseEntity<String> exchange = restTemplate.exchange(
                "http://localhost:8080/http/HelloHTTP",
                HttpMethod.GET,
                null,
                String.class
        );
        //获取响应体
        String body = exchange.getBody();
        //获取状态码
        HttpStatusCode statusCode = exchange.getStatusCode();
        int statusCodeValue = exchange.getStatusCodeValue();
        //获取响应头
        HttpHeaders headers = exchange.getHeaders();
        String s = Objects.requireNonNull(headers.getContentType()).toString();


        /**
         * 发送 DELETE 请求，无返回值
         */
        restTemplate.delete("https://api.example.com/user/1");


    }

    public void post() {
        RestTemplate restTemplate = new RestTemplate();
        /**
         * postForObject
         */
        String s1 = restTemplate.postForObject("http://localhost:8080/http/HelloHTTP", null, String.class);

        // postForEntity
        ResponseEntity<User> response = restTemplate.postForEntity("https://api.example.com/users",newUser,User.class);

    }
    public void put() {
        RestTemplate restTemplate = new RestTemplate();

        // 路径参数 {id} 指定要更新的资源
        restTemplate.put(
                "https://api.example.com/users/{id}",
                User,
                1  // 替换 {id}
        );

    }
    public void delete() {
        RestTemplate restTemplate = new RestTemplate();

        // 删除 ID=1 的用户
        restTemplate.delete("https://api.example.com/users/{id}", 1);

    }
    public void exchange() {
        RestTemplate restTemplate = new RestTemplate();

// 1. 构建请求头（如带 Token 认证）
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer xxx");
        headers.setContentType(MediaType.APPLICATION_JSON);

// 2. 构建请求实体（包含头和体）
        User requestBody = new User("Charlie");
        HttpEntity<User> requestEntity = new HttpEntity<>(requestBody, headers);

// 3. 发送 POST 请求（支持 GET、PUT 等任意方法）
        ResponseEntity<User> response = restTemplate.exchange(
                "https://api.example.com/users",
                HttpMethod.POST,  // 指定 HTTP 方法
                requestEntity,
                User.class  // 响应体类型
        );

    }


}
