package com.ironhack.favouritesservice.repositories;
import com.ironhack.favouritesservice.dao.Favourite;
import com.ironhack.favouritesservice.dto.FavouritePlaceCountDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavouriteRepository extends JpaRepository<Favourite, Long> {

    List<Favourite> findByUserId(Long userId);

    @Query(value = "SELECT COUNT(place_id) AS favourited_count, place_id FROM favourite GROUP BY place_id ORDER BY favourited_count DESC LIMIT :limit", nativeQuery = true)
    List<long[]> getMostFavouritedPlacesLimitedUpto(@Param("limit") int limit);

    @Query(value = "SELECT f FROM Favourite f WHERE f.userId = :userId AND f.placeId = :placeId")
    Optional<Favourite> findByUserIdAndPlaceId(@Param("userId")Long userId,@Param("placeId") Long placeId);

}
