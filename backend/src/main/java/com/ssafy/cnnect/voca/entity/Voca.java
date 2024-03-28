package com.ssafy.cnnect.voca.entity;

import com.ssafy.cnnect.user.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "word_list")
public class Voca {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long wordListId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "word_content", nullable = false)
    private String wordContent;

    @Column(name = "word_mean", nullable = false)
    private String wordMean;

    @Column(name = "word_date", nullable = false)
    private LocalDate wordDate;
}
