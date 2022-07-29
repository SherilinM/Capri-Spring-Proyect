package com.ironhack.favouritesservice.controller.impl;

import com.ironhack.favouritesservice.controller.FavouriteController;
import com.ironhack.favouritesservice.dao.*;
import com.ironhack.favouritesservice.dto.*;
import com.ironhack.favouritesservice.dto.PlaceDTO;
import com.ironhack.favouritesservice.services.impl.FavouritesServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/favourites")
public class FavouriteControllerImpl implements FavouriteController {

    @Autowired
    private FavouritesServiceImpl favouritesService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Favourite> findAll(){
        return favouritesService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Favourite findById(@PathVariable Long id){
        return favouritesService.findById(id);
    }

    @Override
    @GetMapping("/userid/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<PlaceDTO> getAllFavouritePlacesByUserId(@PathVariable Long id){
        return favouritesService.getAllPlacesByUserId(id);
    }

    @Override
    @PutMapping("/placeisfavourited")
    @ResponseStatus(HttpStatus.CREATED)
    public boolean isPlaceFavourited(@Valid @RequestBody FavouriteDTO favouriteDTO){
        return favouritesService.isPlaceFavourited(favouriteDTO);
    }

    @Override
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Favourite addToFavourites(@Valid @RequestBody FavouriteDTO favouriteDTO){
        return favouritesService.addToFavourites(favouriteDTO);
    }

    @Override
    @PutMapping("/remove")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeFromFavourites(@RequestBody FavouriteDTO favouriteDTO){
        favouritesService.removeFromFavourites(favouriteDTO);
    }

    @Override
    @GetMapping("/top10")
    public List<PlaceDTO> getTop10FavouritedPlaces() {
        return favouritesService.mostFavouritedPlacesLimitedBy(10);
    }
}
