package com.ironhack.ratingservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Max(5)
    @Min(0)
    private double rating;
    private Long placeId;
    private Long userId;

    public Rating(double rating, Long placeId, Long userId) {
        this.rating = rating;
        this.placeId = placeId;
        this.userId = userId;
    }
}
