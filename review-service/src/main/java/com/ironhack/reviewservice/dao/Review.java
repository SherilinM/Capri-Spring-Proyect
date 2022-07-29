package com.ironhack.reviewservice.dao;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private String content;
    private Long placeId;
    private Long userId;
    private Long ratingId;
    private LocalDate createdDate = LocalDate.now();
    private LocalDate editedDate = LocalDate.now();


    public Review(String title, String content, Long placeId, Long userId, Long ratingId) {
        this.title = title;
        this.content = content;
        this.placeId = placeId;
        this.userId = userId;
        this.ratingId = ratingId;
    }
}
