package com.ssafy.cnnect.search.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Builder
@Document(indexName = "result")
@Setting(replicas = 0)
@AllArgsConstructor
@NoArgsConstructor
public class SearchResultDocument {

    private String video_id;
    private String video_date;
    private String video_name;
    private String text;
}
