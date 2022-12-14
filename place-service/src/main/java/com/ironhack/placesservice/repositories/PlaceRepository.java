package com.ironhack.placesservice.repositories;

import com.ironhack.placesservice.dao.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceRepository extends JpaRepository<Place, Long> {

    List<Place> findByAuthorId(Long id);
}
