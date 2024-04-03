package com.ssafy.cnnect.search.entity;

import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

@Getter
@Setter
@Builder
@Document(indexName = "sentence")
@Setting(replicas = 0)
@AllArgsConstructor
@NoArgsConstructor
public class SentenceDocument {
    @Field(name = "start", type = FieldType.Long, index = false)
    private Long start;

    @Field(name = "text", type = FieldType.Text, index = true)
    private String text;
}
