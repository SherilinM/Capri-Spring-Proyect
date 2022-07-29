package com.ironhack.reviewservice.service.impl;

import com.ironhack.reviewservice.dao.Review;
import com.ironhack.reviewservice.dto.ReviewDTO;
import com.ironhack.reviewservice.dto.ReviewResponse;
import com.ironhack.reviewservice.repositories.ReviewRepository;
import com.ironhack.reviewservice.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public List<ReviewResponse> getByPlaceId(Long placeId) {
        return reviewRepository.getReviewResponseByPlaceId(placeId);
    }

    @Override
    public List<ReviewResponse> getByUserId(Long userId) {
        return reviewRepository.getReviewResponseByUserId(userId);
    }


    @Override
    public Review getById(Long id) {
        Optional<Review> review = reviewRepository.findById(id);
        if(review.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No review found with id: " + id);
        return review.get();
    }

    @Override
    public Review editReview(ReviewDTO reviewDTO) {
        Review review = getById(reviewDTO.getId());
        if(reviewDTO.getTitle() != null){
            review.setTitle(reviewDTO.getTitle());
        }
        if(reviewDTO.getContent() != null) {
            review.setContent(reviewDTO.getContent());
        }
        review.setEditedDate(LocalDate.now());
        return reviewRepository.save(review);
    }

    @Override
    public Review addReview(ReviewDTO reviewDTO) {
        return reviewRepository.save(createReviewFromDTO(reviewDTO));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.delete(getById(id));
    }

    @Override
    public boolean isPreviouslyReviewed(Long userId, Long placeId) {
        Optional<Review> optionalReview = reviewRepository.findByUserIdAndPlaceId(userId, placeId);
        return optionalReview.isPresent();
    }


    private Review createReviewFromDTO(ReviewDTO reviewDTO) {
        return new Review(
                reviewDTO.getTitle(),
                reviewDTO.getContent(),
                reviewDTO.getPlaceId(),
                reviewDTO.getUserId(),
                reviewDTO.getRatingId()
        );
    }

}
