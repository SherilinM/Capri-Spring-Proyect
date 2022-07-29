package com.ironhack.placesservice.dto;

import com.ironhack.placesservice.enums.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CreatePlaceDTO {

    @NotNull
    private String name;
    @NotNull
    private Long authorId;
    @NotNull
    @NotNull
    @Enumerated(EnumType.STRING)
    private String imageUrl;
    @NotNull
    private String location;
    @NotNull
    private String description;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Category category;



}