package com.ironhack.favouritesservice.dto;

import com.ironhack.favouritesservice.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PlaceDTO {


    private Long id;
    private String name;
    private Long authorId;
    private String imageUrl;

    private String location;

    private String description;

    private Category category;

    public PlaceDTO(String name, Long authorId, String imageUrl, String location, String description, Category category) {
        this.name = name;
        this.authorId = authorId;
        this.imageUrl = imageUrl;
        this.location = location;
        this.description = description;
        this.category = category;
    }
}

