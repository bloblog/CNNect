package com.ssafy.cnnect.video.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import org.bson.types.ObjectId;

@Getter
public class Sentence {
    @Id
    private ObjectId sentenceId;

    private Long start;

    private String text;
}
