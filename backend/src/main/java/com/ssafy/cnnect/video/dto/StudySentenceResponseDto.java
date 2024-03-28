package com.ssafy.cnnect.video.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.bson.types.ObjectId;

@Builder
@Getter
@AllArgsConstructor
public class StudySentenceResponseDto {
    private int order;
    private Long start;
    private String text;
    private Double score;
}