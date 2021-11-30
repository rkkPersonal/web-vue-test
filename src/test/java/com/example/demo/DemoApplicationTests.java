package com.example.demo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;
import org.apache.catalina.User;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootTest
class DemoApplicationTests {

    public static RestTemplate restTemplate = new RestTemplate();

    @Test
    void contextLoads() {
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newFixedThreadPool(100);

        CountDownLatch countDownLatch = new CountDownLatch(100);


        for (int i = 0; i < 100; i++) {
            executorService.submit(() -> {
                countDownLatch.countDown();
                Random random = new Random();
                try {
                    int i1 = random.nextInt(5) * 1000;
                    Thread.sleep(i1);
                    System.out.println("开始倒计时：");

                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                Users build = Users.builder().name("steven").address("山西").date(new Date().toString()).build();
                ObjectMapper objectMapper = new ObjectMapper();
                try {
                    String request = objectMapper.writeValueAsString(build);
                    HttpHeaders httpHeaders = new HttpHeaders();
                    httpHeaders.add("Content-Type", "application/json;charset=utf-8");
                    ResponseEntity<String> exchange = restTemplate.exchange("http://localhost:8081/second", HttpMethod.POST, new HttpEntity<>(request, httpHeaders), String.class);
                } catch (JsonProcessingException e) {

                }
            });
        }

    }

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Builder
class Users {

    private String name;
    private String province;
    private String city;
    private String address;
    private String zip;
    private String date;
    private String email;


}
