package com.ssafy.cnnect.userHistory.entity;

import com.ssafy.cnnect.user.entity.User;
import com.ssafy.cnnect.userSentence.entity.UserSentence;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name="user_history")
public class UserHistory {
    @Id
    @Column(name = "history_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long historyId;

    @Column(name = "history_status", nullable = false)
    private boolean historyStatus;

    @Column(name = "history_sentence")
    private String historySentence;

    @Column(name = "history_time")
    private Double historyTime;

    @Column(name = "video_id", nullable = false)
    private String videoId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToMany(mappedBy = "userHistory", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<UserSentence> userSentenceList;

    public void addUserSentence(UserSentence userSentence) {
        userSentence.setUserHistory(this);
        userSentenceList.add(userSentence);
    }
}
