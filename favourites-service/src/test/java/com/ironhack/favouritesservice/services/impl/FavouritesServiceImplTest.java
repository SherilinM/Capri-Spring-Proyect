package com.ironhack.favouritesservice.services.impl;

import com.ironhack.favouritesservice.dao.Favourite;
import com.ironhack.favouritesservice.dto.*;
import com.ironhack.favouritesservice.repositories.FavouriteRepository;
import com.ironhack.favouritesservice.services.FavouritesService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class FavouritesServiceImplTest {

    @Autowired
    private FavouriteRepository favouriteRepository;
    @Autowired
    private FavouritesService favouritesService;

    Favourite testFav1;
    Favourite testFav2;
    Favourite testFav3;
    Favourite testFav4;

    @BeforeEach
    void setUp() {
        testFav1 = new Favourite(1L, 1L);
        testFav2 = new Favourite(1L, 2L);
        testFav3 = new Favourite(2L, 3L);
        testFav4 = new Favourite(3L, 4L);
        favouriteRepository.saveAll(List.of(testFav1,testFav2,testFav3,testFav4));
    }

    @AfterEach
    void tearDown() {
        favouriteRepository.deleteAll();
    }

    @Test
    void findAll() {
        var repoSize = favouritesService.findAll().size();
        assertEquals(4, repoSize);
    }

    @Test
    void findById() {
        Favourite favourite = favouritesService.findById(testFav1.getId());
        assertEquals(testFav1.getPlaceId(), favourite.getPlaceId());
        assertEquals(testFav1.getUserId(), favourite.getUserId());
    }

    @Test
    void findById_Throws() {
        assertThrows(ResponseStatusException.class, () -> favouritesService.findById(testFav1.getId() - 50L));
    }

    @Test
    void findByUserId() {
        var repoSize = favouritesService.findByUserId(testFav1.getUserId()).size();
        assertEquals(2, repoSize);
    }

    @Test
    void getAllPlacesByUserId() {
        var placeListDTO = favouritesService.getAllPlacesByUserId(1L);
        assertEquals(2, placeListDTO.size());
        assertNotNull(placeListDTO.get(0).getName());
        assertNotNull(placeListDTO.get(0).getCategory());
        assertNotNull(placeListDTO.get(0).getAuthorId());

    }

    @Test
    void addToFavourites() {
        var repoSizeBefore = favouritesService.findAll().size();
        Favourite favourite = favouritesService.addToFavourites(new FavouriteDTO(1L, 1L));
        var repoSizeAfter = favouritesService.findAll().size();
        assertEquals(repoSizeBefore + 1, repoSizeAfter);
        assertNotNull(favourite.getUserId());
        assertNotNull(favourite.getPlaceId());
    }

    @Test
    void removeFromFavourites_Valid() {
        var repoSizeBefore = favouritesService.findAll().size();
        favouritesService.removeFromFavourites(new FavouriteDTO(1L, 1L));
        var repoSizeAfter = favouritesService.findAll().size();
        assertEquals(repoSizeBefore - 1, repoSizeAfter);
        assertThrows(ResponseStatusException.class, () -> favouritesService.findById(testFav1.getId()));
    }

    @Test
    void mostFavouritedPlacesLimitedBy() {
        List<Favourite> favouriteList = List.of(
                new Favourite(1L, 1L),
                new Favourite(1L, 1L),
                new Favourite(1L, 1L),
                new Favourite(1L, 1L),
         new Favourite(1L, 2L),
        new Favourite(2L, 3L),
        new Favourite(3L, 4L),
        new Favourite(1L, 5L),
         new Favourite(1L, 6L),
        new Favourite(2L, 7L),
        new Favourite(3L, 8L),
        new Favourite(1L, 9L),
         new Favourite(1L, 10L),
        new Favourite(2L, 11L),
        new Favourite(2L, 12L),
        new Favourite(2L, 13L),
        new Favourite(3L, 14L));
        favouriteRepository.saveAll(favouriteList);
        var placeList = favouritesService.mostFavouritedPlacesLimitedBy(5);
        assertEquals(5, placeList.size());
        assertEquals(1L, placeList.get(0).getId());
        System.out.println(placeList);
    }

    @Test
    void isPlaceFavourited_Valid(){
        boolean value = favouritesService.isPlaceFavourited(new FavouriteDTO(1L, 1L));
        assertTrue(value);
    }

    @Test
    void False(){
        boolean value = favouritesService.isPlaceFavourited(new FavouriteDTO(65L, 1L));
        assertFalse(value);
    }

}