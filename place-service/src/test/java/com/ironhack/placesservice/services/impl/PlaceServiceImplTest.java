package com.ironhack.placesservice.services.impl;

import com.ironhack.placesservice.dao.*;
import com.ironhack.placesservice.dto.CreatePlaceDTO;
import com.ironhack.placesservice.dto.PlaceDTO;
import com.ironhack.placesservice.enums.Category;
import com.ironhack.placesservice.repositories.PlaceRepository;
import com.ironhack.placesservice.services.PlaceService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PlaceServiceImplTest {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private PlaceService placeService;

    Place place1;

    @BeforeEach
    void setUp() {
        place1 = new Place(
                "Test Place1",
                2L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location",
                "Test description",
                Category.RESTAURANT
        );
        placeRepository.save(place1);
    }

    @AfterEach
    void tearDown() {
        placeRepository.deleteAll();
    }

    @Test
    void findAll_Valid() {
        var placeList = placeService.findAll();
        assertEquals(1, placeList.size());
    }

    @Test
    void findById_Valid() {
        var place = placeService.findById(place1.getId());
        assertEquals(place1.getName(), place.getName());
    }

    @Test
    void findById_Valid_Properties() {
        var place = placeService.findById(place1.getId());
        assertEquals(place1.getName(), place.getName());
        assertEquals(place1.getDescription(),place.getDescription());
        assertEquals(place1.getLocation(), place.getLocation());
        assertEquals(place1.getAuthorId(), place.getAuthorId());
        assertEquals(Category.RESTAURANT, place.getCategory());
    }


    @Test
    void findById_ThrowsException() {
        assertThrows(ResponseStatusException.class, () -> placeService.findById(place1.getId() - 60L));
    }

    @Test
    void deletePlace() {
        var placeListBefore = placeService.findAll().size();
        placeService.deletePlace(place1.getId());
        var placeListAfter = placeService.findAll().size();
        assertEquals(placeListBefore - 1, placeListAfter);
    }

    @Test
    void deletePlace_ThrowsException() {
        assertThrows(ResponseStatusException.class, () ->  placeService.deletePlace(place1.getId() - 60L));
    }

    @Test
    void deletePlace_ThrowsException2() {
        placeService.deletePlace(place1.getId());
        assertThrows(ResponseStatusException.class, () ->  placeService.deletePlace(place1.getId()));
    }

    @Test
    void addPlace_Valid_DatabaseIncrease() {
        CreatePlaceDTO createPlaceDTO = new CreatePlaceDTO(
                "Test Place2",
                2L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location",
                "Test description",
                Category.RESTAURANT
        );
        var placeListBefore = placeService.findAll().size();
        Place place = placeService.addPlace(createPlaceDTO);
        var placeListAfter = placeService.findAll().size();
        assertEquals(placeListBefore + 1, placeListAfter);
    }

    @Test
    void addPlace_Valid_CheckValues() {
        CreatePlaceDTO createPlaceDTO = new CreatePlaceDTO(
                "Test Place2",
                10L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location",
                "Test description",
                Category.BAR
        );
        Place place = placeService.addPlace(createPlaceDTO);
        assertEquals(createPlaceDTO.getName(), place.getName());
        assertEquals(createPlaceDTO.getCategory(), place.getCategory());
        assertEquals(LocalDate.now().getDayOfMonth(), place.getCreatedDate().getDayOfMonth());
        assertEquals(LocalDate.now().getDayOfMonth(), place.getEditedDate().getDayOfMonth());
    }

    @Test
    void updatePlace_Valid_NoDataBaseIncrease() {
        PlaceDTO placeDTO = new PlaceDTO(
                place1.getId(),
                "Test Place2",
                10L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location",
                "Test description",
                Category.BAR
        );
        var placeListBefore = placeService.findAll().size();
        Place place = placeService.updatePlace(placeDTO);
        var placeListAfter = placeService.findAll().size();
        assertEquals(placeListBefore, placeListAfter);
    }

    @Test
    void updatePlace_Valid_TestUpdated() {
        place1.setCreatedDate(LocalDate.now().minusYears(50L));
        placeRepository.save(place1);
        System.out.println(place1.getCreatedDate());
        //String name, Long authorId, String imageUrl, String location, String description, Category category
        PlaceDTO placeDTO = new PlaceDTO(
                place1.getId(),
                "Test Place2",
                10L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location",
                "Test description",
                Category.BAR
        );
        placeService.updatePlace(placeDTO);
        Place updatedPlace = placeService.findById(place1.getId());
        assertEquals(place1.getId(), updatedPlace.getId());
        assertEquals(placeDTO.getName(), updatedPlace.getName());
        assertEquals(placeDTO.getCategory(), updatedPlace.getCategory());
        assertNotEquals(updatedPlace.getCreatedDate().getYear(), updatedPlace.getEditedDate().getYear());
    }

    @Test
    void updatePlace_Valid_TestNullFilter() {
        place1.setCreatedDate(LocalDate.now().minusYears(50L));
        placeRepository.save(place1);
        Place originalPlace = placeService.findById(place1.getId());
        PlaceDTO placeDTO = new PlaceDTO(
                place1.getId(),
                null,
                null,
                null,
                null,
                null,
                null
        );
        placeService.updatePlace(placeDTO);
        Place updatedPlace = placeService.findById(place1.getId());
        assertEquals(originalPlace.getId(), updatedPlace.getId());
        assertEquals(originalPlace.getName(), updatedPlace.getName());
        assertEquals(originalPlace.getCategory(), updatedPlace.getCategory());
        assertNotEquals(updatedPlace.getCreatedDate().getYear(), updatedPlace.getEditedDate().getYear());
    }

    @Test
    void updatePlace_ThrowsException() {
        PlaceDTO placeDTO = new PlaceDTO(
                place1.getId() + 65L,
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertThrows(ResponseStatusException.class, () ->  placeService.updatePlace(placeDTO));
    }

    @Test
    void findByUserId_valid() {
        var placeFound = placeService.findByUserId(place1.getAuthorId());
        assertEquals(place1.getName(), placeFound.get(0).getName());
    }

    @Test
    void findByUserId_Invalid() {
        assertThrows(ResponseStatusException.class, () -> placeService.findByUserId(place1.getAuthorId() - 65L));
    }
}