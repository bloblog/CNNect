package com.ssafy.cnnect.search.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Builder
@Document(indexName = "sentence")
@Setting(replicas = 0)
@AllArgsConstructor
@NoArgsConstructor
public class SentenceDocument {
    @Field(name = "start", type = FieldType.Long, index = false)
    private Long start;

    @Field(name = "text", type = FieldType.Text)
    private String text;
}
