package com.ironhack.reviewservice.controller.impl;

import com.ironhack.reviewservice.controller.ReviewController;
import com.ironhack.reviewservice.dao.Review;
import com.ironhack.reviewservice.dto.ReviewDTO;
import com.ironhack.reviewservice.dto.ReviewResponse;
import com.ironhack.reviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reviews")
public class ReviewControllerImpl implements ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Override
    @GetMapping("/place/{placeId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getByPlaceId(@PathVariable(name = "placeId") Long placeId){
        return reviewService.getByPlaceId(placeId);
    }

    @Override
    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    public List<ReviewResponse> getByUserId(@PathVariable(name = "userId") Long userId){
        return reviewService.getByUserId(userId);
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Review getById(@PathVariable(name = "id") Long id){
        return reviewService.getById(id);
    }

    @Override
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Review editReview(@RequestBody ReviewDTO reviewDTO) {
        return reviewService.editReview(reviewDTO);
    }

    @Override
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Review addReview(ReviewDTO reviewDTO) {
        return reviewService.addReview(reviewDTO);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void deleteReview(Long id) {
        reviewService.deleteReview(id);
    }

    @Override
    public boolean previouslyReviewed(Long userId, Long placeId) {
        return reviewService.isPreviouslyReviewed(userId,placeId);
    }


}
