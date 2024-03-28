package com.ssafy.cnnect.userSentence.entity;

import com.ssafy.cnnect.userHistory.entity.UserHistory;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_sentence")
public class UserSentence {
    @Id
    @Column(name = "user_sentence_id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userSentenceId;

    @Column(name = "sentence_order", nullable = false)
    private int sentenceOrder;

    @Column(name = "sentence_content", nullable = false)
    private String sentenceContent;

    @Column(name = "sentence_score")
    private Double sentenceScore;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "history_id", nullable = false)
    private UserHistory userHistory;
}
