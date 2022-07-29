package com.ironhack.ratingservice.controller;

import com.ironhack.ratingservice.dao.Rating;
import com.ironhack.ratingservice.dto.RatingDTO;
import com.ironhack.ratingservice.dto.PlaceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface RatingController {

    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<PlaceDTO> findByUserId(@PathVariable(name = "userId") Long userId);

    @GetMapping("/{placeId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    boolean userPreviouslyRatedPlace(@PathVariable(name = "placeId") Long placeId,
                                      @PathVariable(name = "userId") Long userId);

    @GetMapping("/place/{placeId}")
    @ResponseStatus(HttpStatus.OK)
    Double getAverageRatingForPlace(@PathVariable(name = "placeId") Long placeId);

    @GetMapping("/top10places")
    @ResponseStatus(HttpStatus.OK)
    List<PlaceDTO> getTop10Places();

    @GetMapping("/top10foruser/{userId}")
    @ResponseStatus(HttpStatus.OK)
    List<PlaceDTO> getTop10PlacesForUser(@PathVariable(name = "userId") Long userId);

    @PutMapping("/rateplace")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Rating ratePlace(@RequestBody RatingDTO ratingDTO);

    @PutMapping("/usersrating")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Double findByUserIdAndPlaceId(RatingDTO ratingDTO);
}
