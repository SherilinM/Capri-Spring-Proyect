package com.ironhack.ratingservice.repositories;

import com.ironhack.ratingservice.dao.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findByUserId(Long id);

    Optional<Rating> findByPlaceIdAndUserId(Long placeId, Long userId);

    @Query(value = "SELECT AVG(r.rating) FROM Rating r WHERE r.placeId = :placeId")
    Optional<Double> getAverageRatingForPlace(@Param("placeId") Long placeId);

    @Query(value = "SELECT place_id FROM rating GROUP BY place_id ORDER BY AVG(rating) DESC LIMIT :limit", nativeQuery = true)
    List<Long[]> getTopRatedPlacesLimitBy(@Param("limit") int limit);

    @Query(value = "SELECT place_id FROM rating WHERE user_id = :userId GROUP BY place_id ORDER BY AVG(rating) DESC LIMIT :limit", nativeQuery = true)
    List<Long[]> getTopRatedPlacesByUserIdLimitBy(@Param("userId") Long userId, @Param("limit") int limit);

    @Query(value = "SELECT r.rating FROM Rating r WHERE r.userId = :userId AND r.placeId = :placeId")
    Optional<Double> findByUserIdAndPlaceId(@Param("userId")Long userId,@Param("placeId") Long placeId);

}
