package com.ironhack.favouritesservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class FavouritePlaceCountDTO {

    long favourited_count;
    Long place_id;


}
