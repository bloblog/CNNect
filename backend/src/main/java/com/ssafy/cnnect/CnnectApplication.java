package com.ssafy.cnnect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.ssafy.cnnect.video.repository")
public class CnnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnnectApplication.class, args);
    }

}
