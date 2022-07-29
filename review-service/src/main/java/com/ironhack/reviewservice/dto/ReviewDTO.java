package com.ironhack.reviewservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ReviewDTO {

    private Long id;

    private String title;
    private String content;
    private Long placeId;
    private Long userId;
    private Long ratingId;

    public ReviewDTO(String title, String content, Long placeId, Long userId, Long ratingId) {
        this.title = title;
        this.content = content;
        this.placeId = placeId;
        this.userId = userId;
        this.ratingId = ratingId;
    }
}