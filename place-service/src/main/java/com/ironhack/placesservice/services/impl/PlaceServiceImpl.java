package com.ironhack.placesservice.services.impl;

import com.ironhack.placesservice.dao.*;
import com.ironhack.placesservice.dto.CreatePlaceDTO;
import com.ironhack.placesservice.dto.PlaceDTO;
import com.ironhack.placesservice.repositories.PlaceRepository;
import com.ironhack.placesservice.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PlaceServiceImpl implements PlaceService {

    @Autowired
    private PlaceRepository placeRepository;

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    public Place findById(Long id) {
        Optional<Place> place = placeRepository.findById(id);
        if (place.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Place found with ID: " + id);
        return place.get();
    }

    public void deletePlace(Long id) {
        Place place = findById(id);
        placeRepository.delete(place);
    }

    public Place addPlace(CreatePlaceDTO createPlaceDTO){
        return placeRepository.save(convertNewPlaceDTOToPlace(createPlaceDTO));
    }

    public Place updatePlace(PlaceDTO placeDTO){
        Place place = findById(placeDTO.getId());
        return placeRepository.save(updatePlaceWithPlaceDTO(placeDTO, place));
    }

    @Override
    public List<Place> findByUserId(Long id) {
        List<Place> placeList = placeRepository.findByAuthorId(id);
        if(placeList.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user has no favourite places saved");
        return placeList;
    }

    private Place updatePlaceWithPlaceDTO(PlaceDTO placeDTO, Place place) {
        if (placeDTO.getName() != null) {
            place.setName(placeDTO.getName());
        }
        if (placeDTO.getAuthorId() != null) {
            place.setAuthorId(placeDTO.getAuthorId());
        }

        if (placeDTO.getLocation() != null) {
            place.setLocation(placeDTO.getLocation());
        }

        if (placeDTO.getDescription() != null) {
            place.setDescription(placeDTO.getDescription());
        }

        if (placeDTO.getCategory() != null) {
            place.setCategory(placeDTO.getCategory());
        }
        place.setEditedDate(LocalDate.now());
        return place;
    }

    private Place convertNewPlaceDTOToPlace(CreatePlaceDTO createPlaceDTO) {
        //String name, Long authorId, String imageUrl, String location, String description, Category category
        return new Place(
                createPlaceDTO.getName(),
                createPlaceDTO.getAuthorId(),
                createPlaceDTO.getImageUrl(),
                createPlaceDTO.getLocation(),
                createPlaceDTO.getDescription(),
                createPlaceDTO.getCategory()
        );
    }


}
