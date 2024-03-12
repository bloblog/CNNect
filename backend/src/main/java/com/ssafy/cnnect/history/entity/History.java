package com.ssafy.cnnect.history.entity;

import com.ssafy.cnnect.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @Column(name = "history_status", nullable = false)
    private boolean historyStatus;

    @Column(name = "history_sentence")
    private String historySentence;

    @Column(name = "history_date", nullable = false)
    private LocalDate historyDate;

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;


}
