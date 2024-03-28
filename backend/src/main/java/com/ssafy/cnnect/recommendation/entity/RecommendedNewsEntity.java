package com.ssafy.cnnect.recommendation.entity;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "recommended_news")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecommendedNewsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "recommend_id")
    private String recommendId;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "video_level")
    private int videoLevel;

    @Column(name = "video_thumbnail")
    private String videoThumbnail;

    @Column(name = "video_date")
    private String videoDate;

    @ElementCollection
    @CollectionTable(name = "sentence_list", joinColumns = @JoinColumn(name = "recommended_news_id"))
    @Column(name = "sentence")
    private List<String> sentenceList;

    @ElementCollection
    @CollectionTable(name = "word_list", joinColumns = @JoinColumn(name = "recommended_news_id"))
    @Column(name = "word")
    private List<String> wordList;

    @Column(name = "category_id")
    private int categoryId;
}
