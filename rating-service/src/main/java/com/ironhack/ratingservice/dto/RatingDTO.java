package com.ironhack.ratingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class RatingDTO {

    private Long id;

    @Max(5)
    @Min(0)
    private double rating;
    private Long placeId;
    private Long userId;

    public RatingDTO(double rating, Long placeId, Long userId) {
        this.rating = rating;
        this.placeId = placeId;
        this.userId = userId;
    }
}