package com.ironhack.placesservice.controllers;

import com.ironhack.placesservice.dao.*;
import com.ironhack.placesservice.dto.CreatePlaceDTO;
import com.ironhack.placesservice.dto.PlaceDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface PlaceController {

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<Place> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    Place findById(@PathVariable(name = "id") Long id);

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deletePlace(@PathVariable(name = "id") Long id);

    @PostMapping("/add")
    @ResponseStatus(HttpStatus.CREATED)
    Place addPlace(@RequestBody CreatePlaceDTO createPlaceDTO);

    @PutMapping("edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    Place updatePlace(@RequestBody PlaceDTO PlaceDTO);

    @GetMapping("/user/{id}")
    @ResponseStatus(HttpStatus.OK)
    public List<Place> findByUserId(@PathVariable(name = "id") Long id);

}
