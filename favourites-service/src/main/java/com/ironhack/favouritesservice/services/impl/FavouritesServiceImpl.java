package com.ironhack.favouritesservice.services.impl;

import com.ironhack.favouritesservice.dao.Favourite;
import com.ironhack.favouritesservice.dto.*;
import com.ironhack.favouritesservice.dto.FavouritePlaceCountDTO;
import com.ironhack.favouritesservice.dto.PlaceDTO;
import com.ironhack.favouritesservice.proxy.PlaceServiceProxy;
import com.ironhack.favouritesservice.repositories.FavouriteRepository;
import com.ironhack.favouritesservice.services.FavouritesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class FavouritesServiceImpl implements FavouritesService {

    @Autowired
    private FavouriteRepository favouriteRepository;
    @Autowired
    private PlaceServiceProxy placeService;

    @Override
    public List<Favourite> findAll(){
        return favouriteRepository.findAll();
    }

    @Override
    public Favourite findById(Long id){
        Optional<Favourite> favourite = favouriteRepository.findById(id);
        if(favourite.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Favourite Exists with Id:" + id);
        return favourite.get();
    }

    @Override
    public List<Favourite> findByUserId(Long id){
        return favouriteRepository.findByUserId(id);
    }

    @Override
    public List<PlaceDTO> getAllPlacesByUserId(Long id){
        return placeListFromFavouritesList(findByUserId(id));
    }

    @Override
    public Favourite addToFavourites(FavouriteDTO favouriteDTO){
        return favouriteRepository.save(createFavouriteFromDTO(favouriteDTO));
    }

    @Override
    public void removeFromFavourites(FavouriteDTO favouriteDTO){
        Optional<Favourite> favourite = favouriteRepository.findByUserIdAndPlaceId(favouriteDTO.getUserId(), favouriteDTO.getPlaceId());
        if(favourite.isEmpty()) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to find favourite by UserId and PlaceId");
        favouriteRepository.delete(favourite.get());
    }

    @Override
    public List<PlaceDTO> mostFavouritedPlacesLimitedBy(int i) {
        List<FavouritePlaceCountDTO> favouritePlaceCountDTOS = getFavouritePlaceCountDTOS(i);
        return getPlaceDTOSFromFavouritePlaceCountDTOS(favouritePlaceCountDTOS);
    }

    @Override
    public boolean isPlaceFavourited(FavouriteDTO favouriteDTO) {
        Optional<Favourite> favourite = favouriteRepository.findByUserIdAndPlaceId(favouriteDTO.getUserId(), favouriteDTO.getPlaceId());
        if(favourite.isEmpty()){
            return false;
        }else {
            return true;
        }
    }

    private List<PlaceDTO> getPlaceDTOSFromFavouritePlaceCountDTOS(List<FavouritePlaceCountDTO> favouritePlaceCountDTOS) {
        List<PlaceDTO> favPlaceList = new ArrayList<>();
        for(FavouritePlaceCountDTO favouritePlaceCountDTO : favouritePlaceCountDTOS) {
            favPlaceList.add(placeService.findById(favouritePlaceCountDTO.getPlace_id()));
        }
        return favPlaceList;
    }

    private List<FavouritePlaceCountDTO> getFavouritePlaceCountDTOS(int i) {
        return convertRepoResultToFavPlaceCountDTOList(i);
    }

    private List<FavouritePlaceCountDTO> convertRepoResultToFavPlaceCountDTOList(int i) {
        List<long[]> results = favouriteRepository.getMostFavouritedPlacesLimitedUpto(i);
        if(results.size() == 0) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No Places have been favourited");
        List<FavouritePlaceCountDTO> favouritePlaceCountDTOS = new ArrayList<>();
        for(long[] result : results){
            favouritePlaceCountDTOS.add(new FavouritePlaceCountDTO(result[0], result[1]));
        }
        return favouritePlaceCountDTOS;
    }

    private Favourite createFavouriteFromDTO(FavouriteDTO favouriteDTO) {
        return new Favourite(favouriteDTO.getUserId(), favouriteDTO.getPlaceId());
    }


    private List<PlaceDTO> placeListFromFavouritesList(List<Favourite> favouritesList) {
        List<PlaceDTO> favPlaceList = new ArrayList<>();
        for (Favourite favourite : favouritesList) {
            favPlaceList.add(placeService.findById(favourite.getPlaceId()));
        }
        return favPlaceList;
    }
}
