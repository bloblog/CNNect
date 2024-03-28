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
@Document(indexName = "cnnect.data")
@Setting
@AllArgsConstructor
@NoArgsConstructor
public class SearchDocument {
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

    @Field(name = "video_date", type = FieldType.Text)
    private String videoDate;

    @Field(name = "sentence_list", type = FieldType.Nested)
    private List<SentenceDocument> sentenceList;

    @Field(name = "full_script", type = FieldType.Text)
    private String fullScript;

    @Field(name = "category_id", type = FieldType.Long)
    private Long categoryId;

    @Field(name = "word_list", type = FieldType.Object)
    private List<String> wordList;

}
