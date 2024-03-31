package com.ssafy.cnnect.badge.entity;

import com.ssafy.cnnect.video.entity.Category;
import jakarta.persistence.*;
import lombok.*;
import java.util.*;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="badge")
public class Badge {
    @Id
    @Column(name = "badge_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long badgeId;

    @Column(name = "badge_name", nullable = false)
    private String badgeName;

    @Enumerated(EnumType.STRING)
    @Column(name = "badge_condition", nullable = false)
    private BadgeCondition badgeCondition;

    @Column(name = "badge_count")
    private Long badgeCount;

    @Column(name = "badge_score")
    private Long badgeScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name ="category_id")
    private Category category;

}
