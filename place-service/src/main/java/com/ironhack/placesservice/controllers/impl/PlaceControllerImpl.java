package com.ironhack.placesservice.controllers.impl;

import com.ironhack.placesservice.controllers.PlaceController;
import com.ironhack.placesservice.dao.Place;
import com.ironhack.placesservice.dto.CreatePlaceDTO;
import com.ironhack.placesservice.dto.PlaceDTO;
import com.ironhack.placesservice.services.PlaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/places")
public class PlaceControllerImpl implements PlaceController {

    @Autowired
    private PlaceService placeService;

    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Place> findAll(){
        return placeService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Place findById(@PathVariable(name = "id") Long id){
        return placeService.findById(id);
    }

    @Override
    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Place> findByUserId(@PathVariable(name = "id") Long id){
        return placeService.findByUserId(id);
    }

    @Override
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePlace(@PathVariable(name = "id") Long id){
        placeService.deletePlace(id);
    }

    @Override
    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    public Place addPlace(@Valid @RequestBody CreatePlaceDTO createPlaceDTO){
        return placeService.addPlace(createPlaceDTO);
    }

    @Override
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public Place updatePlace(@RequestBody @Valid PlaceDTO PlaceDTO){
        return placeService.updatePlace(PlaceDTO);
    }


}
