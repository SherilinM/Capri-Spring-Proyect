package com.ironhack.favouritesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FavouriteDTO {

    @NotNull
    private Long userId;
    @NotNull
    private Long placeId;

}