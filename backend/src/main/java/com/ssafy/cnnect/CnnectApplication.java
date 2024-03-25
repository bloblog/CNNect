package com.ssafy.cnnect;

import com.ssafy.cnnect.search.repository.SearchElasticsearchRepository;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.elasticsearch.repository.config.EnableElasticsearchRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.ssafy.cnnect.video.repository")
@EnableElasticsearchRepositories(basePackages = "com.ssafy.cnnect.search.repository")
public class CnnectApplication {

    public static void main(String[] args) {
        SpringApplication.run(CnnectApplication.class, args);
    }

}
