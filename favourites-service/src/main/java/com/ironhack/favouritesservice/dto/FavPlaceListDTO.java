package com.ironhack.favouritesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavPlaceListDTO {

    List<PlaceDTO> favouritePlaces;
}
