package com.ironhack.ratingservice.services.impl;

import com.ironhack.ratingservice.dao.Rating;
import com.ironhack.ratingservice.dto.PlaceDTO;
import com.ironhack.ratingservice.dto.RatingDTO;
import com.ironhack.ratingservice.dto.TopPlaceDTO;
import com.ironhack.ratingservice.exceptions.*;
import com.ironhack.ratingservice.proxies.PlaceServiceProxy;
import com.ironhack.ratingservice.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServiceImpl implements com.ironhack.ratingservice.services.RatingService {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private PlaceServiceProxy placeServiceProxy;


    @Override
    public List<PlaceDTO> findByUserId(Long userId) {
        List<Rating> ratingList = ratingRepository.findByUserId(userId);
        if (ratingList.size() == 0) throw new RatingNotFoundException("You have not rated any places");
        return getPlaceDTOSFromRatingList(ratingList);
    }


    @Override
    public Double getAverageRatingForPlace(Long placeId) {
        return ratingRepository.getAverageRatingForPlace(placeId).orElseThrow(() -> new RatingNotFoundException("This place has yet to be rated"));
    }

    @Override
    public List<PlaceDTO> getTop10Places(int limit) {
        List<TopPlaceDTO> topPlaceDTOS = convertRepoResultToFavPlaceCountDTOList(limit);
        if (topPlaceDTOS.size() == 0) throw new RatingNotFoundException("No places have been rated yet");
        return getPlaceDTOSFromTopPlaceDTOList(topPlaceDTOS);
    }

    @Override
    public List<PlaceDTO> getTop10PlacesForUser(Long userId) {
        List<TopPlaceDTO> topPlaceDTOS = convertUsersTopPlacesToFavPlaceCountDTOList(userId, 10);
        if (topPlaceDTOS.size() == 0) throw new RatingNotFoundException("You have not rated any places");
        return getPlaceDTOSFromTopPlaceDTOList(topPlaceDTOS);
    }

    @Override
    public boolean userPreviouslyRatedPlace(Long placeId, Long userId) {
        Optional<Rating> rating = ratingRepository.findByPlaceIdAndUserId(placeId, userId);
        return rating.isPresent();
    }

    @Override
    public Rating ratePlace(RatingDTO ratingDTO) {
        if (userPreviouslyRatedPlace(ratingDTO.getPlaceId(), ratingDTO.getUserId())) {
            Rating rating = ratingRepository.findByPlaceIdAndUserId(ratingDTO.getPlaceId(), ratingDTO.getUserId()).get();
            rating.setRating(ratingDTO.getRating());
            return ratingRepository.save(rating);
        } else {
            Rating rating = new Rating(
                    ratingDTO.getRating(),
                    ratingDTO.getPlaceId(),
                    ratingDTO.getUserId());
            return ratingRepository.save(rating);
        }
    }

    @Override
    public Double findByUserIdAndPlaceId(RatingDTO ratingDTO) {
        Optional<Double> rating = ratingRepository.findByUserIdAndPlaceId(ratingDTO.getUserId(), ratingDTO.getPlaceId());
        System.out.println(rating);
        System.out.println(ratingDTO.getUserId());
        System.out.println(ratingDTO.getPlaceId());
        if(rating.isEmpty()){
            return null;
        } else {
            return rating.get();
        }
    }

    private List<PlaceDTO> getPlaceDTOSFromRatingList(List<Rating> ratingList) {
        List<PlaceDTO> placeDTOList = new ArrayList<>();
        for (Rating rating : ratingList) {
            placeDTOList.add(placeServiceProxy.findById(rating.getPlaceId()));
        }
        return placeDTOList;
    }

    private List<PlaceDTO> getPlaceDTOSFromTopPlaceDTOList(List<TopPlaceDTO> topPlaceDTOS) {
        List<PlaceDTO> placeDTOList = new ArrayList<>();
        for (TopPlaceDTO topPlaceDTO : topPlaceDTOS) {
            placeDTOList.add(placeServiceProxy.findById(topPlaceDTO.getPlaceId()));
        }
        return placeDTOList;
    }

    private List<TopPlaceDTO> convertRepoResultToFavPlaceCountDTOList(int limit) {
        List<Long[]> results = ratingRepository.getTopRatedPlacesLimitBy(limit);
        if (results.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Ratings have been completed");
        List<TopPlaceDTO> topPlaceDTOS = new ArrayList<>();
        for (Long[] result : results) {
            topPlaceDTOS.add(new TopPlaceDTO(result[0]));
        }
        return topPlaceDTOS;
    }

    private List<TopPlaceDTO> convertUsersTopPlacesToFavPlaceCountDTOList(Long userid, int limit) {
        List<Long[]> results = ratingRepository.getTopRatedPlacesByUserIdLimitBy(userid, limit);
        if (results.size() == 0)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Ratings have been completed");
        List<TopPlaceDTO> topPlaceDTOS = new ArrayList<>();
        for (Long[] result : results) {
            topPlaceDTOS.add(new TopPlaceDTO(result[0]));
        }
        return topPlaceDTOS;
    }
}
