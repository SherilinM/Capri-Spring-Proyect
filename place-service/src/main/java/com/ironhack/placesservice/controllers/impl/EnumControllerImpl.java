package com.ironhack.placesservice.controllers.impl;

import com.ironhack.placesservice.controllers.EnumController;
import com.ironhack.placesservice.enums.Category;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
public class EnumControllerImpl implements EnumController {

    @Override
    @GetMapping("/categories")
    @ResponseStatus(HttpStatus.OK)
    public Category[] getAllCategories() {
        return Category.class.getEnumConstants();
    }

}
