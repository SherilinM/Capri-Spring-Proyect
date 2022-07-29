package com.ironhack.reviewservice.dto;

import java.time.LocalDate;

public interface ReviewResponse {

    String getId();
    String getTitle();
    String getContent();
    String getEmail();
    String getName();
    Long getUserId();
    LocalDate getCreatedDate();
    LocalDate getEditedDate();
    double getRating();
    Long getRatingId();

}
