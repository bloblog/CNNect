package com.ssafy.cnnect.search.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.Setting;

import java.util.List;

@Getter
@Setter
@Builder
@Document(indexName = "search")
@Setting(replicas = 0)
@AllArgsConstructor
@NoArgsConstructor
public class SearchDocument { // 출력되는 제목 검색 결과 document
    @Id
    private String id;

    @Field(name = "video_id", type = FieldType.Keyword, index = false)
    private String videoId;

    @Field(name = "video_name", type = FieldType.Text)
    private String videoName;

    @Field(name = "video_thumbnail", type = FieldType.Text, index = false)
    private String videoThumbnail;

    @Field(name = "video_level", type = FieldType.Long, index = false)
    private Long videoLevel;

    @Field(name = "senteceList", type = FieldType.Object)
    private List<SentenceDocument> sentenceList;


}
