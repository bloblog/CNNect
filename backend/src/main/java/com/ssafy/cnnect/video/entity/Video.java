package com.ssafy.cnnect.video.entity;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document(collection = "data")
@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Video {
    @Id
    private String id;

    private String video_id;

    private Long category_id;

    private String full_script;

    private List<Sentence> sentence_list;

    private String video_date;

    private String video_name;

    private String video_thumbnail;

    private Long video_level;

    private List<String> word_list;


}
