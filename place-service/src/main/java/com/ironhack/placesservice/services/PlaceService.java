package com.ironhack.placesservice.services;

import com.ironhack.placesservice.dao.*;
import com.ironhack.placesservice.dto.CreatePlaceDTO;
import com.ironhack.placesservice.dto.PlaceDTO;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

public interface PlaceService {

    List<Place> findAll();

    Place findById(Long id) throws ResponseStatusException;

    void deletePlace(Long id) throws ResponseStatusException;

    Place addPlace(CreatePlaceDTO createPlaceDTO) throws ResponseStatusException;

    Place updatePlace(PlaceDTO placeDTO) throws ResponseStatusException;

    List<Place> findByUserId(Long id);
}
