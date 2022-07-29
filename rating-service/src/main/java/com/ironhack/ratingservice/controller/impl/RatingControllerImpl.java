package com.ironhack.ratingservice.controller.impl;

import com.ironhack.ratingservice.dao.Rating;
import com.ironhack.ratingservice.dto.RatingDTO;
import com.ironhack.ratingservice.dto.PlaceDTO;
import com.ironhack.ratingservice.services.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ratings")
public class RatingControllerImpl implements com.ironhack.ratingservice.controller.RatingController {

    @Autowired
    private RatingService ratingService;

    @Override
    @GetMapping("/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaceDTO> findByUserId(@PathVariable(name = "userId") Long userId) {
        return ratingService.findByUserId(userId);
    }

    @Override
    @GetMapping("/{placeId}/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public boolean userPreviouslyRatedPlace(@PathVariable(name = "placeId") Long placeId,
                                             @PathVariable(name = "userId") Long userId) {
        return ratingService.userPreviouslyRatedPlace(placeId, userId);
    }

    @Override
    @GetMapping("/place/{placeId}")
    @ResponseStatus(HttpStatus.OK)
    public Double getAverageRatingForPlace(@PathVariable(name = "placeId") Long placeId) {
        return ratingService.getAverageRatingForPlace(placeId);
    }

    @Override
    @GetMapping("/top10places")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaceDTO> getTop10Places() {
        return ratingService.getTop10Places(10);
    }

    @Override
    @GetMapping("/top10places/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaceDTO> getTop10PlacesForUser(@PathVariable(name = "userId") Long userId) {
        return ratingService.getTop10PlacesForUser(userId);
    }

    @Override
    @PutMapping("/rateplace")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Rating ratePlace(@RequestBody RatingDTO ratingDTO) {
        return ratingService.ratePlace(ratingDTO);
    }

    @PutMapping("/usersrating")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Double findByUserIdAndPlaceId(@RequestBody RatingDTO ratingDTO) {
        return ratingService.findByUserIdAndPlaceId(ratingDTO);
    }


}
