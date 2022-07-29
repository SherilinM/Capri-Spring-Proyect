package com.ironhack.ratingservice.services;

import com.ironhack.ratingservice.dao.Rating;
import com.ironhack.ratingservice.dto.RatingDTO;
import com.ironhack.ratingservice.dto.PlaceDTO;

import java.util.List;

public interface RatingService {

    List<PlaceDTO> findByUserId(Long userId);

    Double getAverageRatingForPlace(Long placeId);

    List<PlaceDTO> getTop10Places(int i);

    List<PlaceDTO> getTop10PlacesForUser(Long userId);

    boolean userPreviouslyRatedPlace(Long placeId, Long userId);

    Rating ratePlace(RatingDTO ratingDTO);

    Double findByUserIdAndPlaceId(RatingDTO ratingDTO);
}
