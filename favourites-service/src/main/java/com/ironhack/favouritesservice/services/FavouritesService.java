package com.ironhack.favouritesservice.services;

import com.ironhack.favouritesservice.dao.Favourite;
import com.ironhack.favouritesservice.dto.*;
import com.ironhack.favouritesservice.dto.PlaceDTO;

import java.util.List;

public interface FavouritesService {

    List<Favourite> findAll();

    Favourite findById(Long id);

    List<Favourite> findByUserId(Long id);

    List<PlaceDTO> getAllPlacesByUserId(Long id);

    Favourite addToFavourites(FavouriteDTO favouriteDTO);

    void removeFromFavourites(FavouriteDTO favouriteDTO);

    List<PlaceDTO> mostFavouritedPlacesLimitedBy(int i);

    boolean isPlaceFavourited(FavouriteDTO favouriteDTO);

}
