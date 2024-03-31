package com.ssafy.cnnect.recommendation.entity;


import com.ssafy.cnnect.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recommended_news")
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class RecommendedNews {
    @Id
    @Column(name = "recommended_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recommendedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "video_id")
    private String videoId;

    @Column(name = "video_name")
    private String videoName;

    @Column(name = "video_level")
    private int videoLevel;

    @Column(name = "video_thumbnail")
    private String videoThumbnail;
}
