package com.ironhack.placesservice.controllers;

import com.ironhack.placesservice.enums.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

public interface EnumController {

    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    Category[] getAllCategories();

}
