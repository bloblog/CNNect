package com.ssafy.cnnect.video.entity;

import jakarta.persistence.Id;
import org.bson.types.ObjectId;

public class Sentence {
    @Id
    private ObjectId sentenceId;

    private Long start;

    private String text;
}
