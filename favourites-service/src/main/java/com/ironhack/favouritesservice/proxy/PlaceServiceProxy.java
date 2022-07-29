package com.ironhack.favouritesservice.proxy;

import com.ironhack.favouritesservice.dto.PlaceDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

@FeignClient("recipe-service")
public interface PlaceServiceProxy {

    @GetMapping("api/v1/places/{id}")
    @ResponseStatus(HttpStatus.OK)
    PlaceDTO findById(@PathVariable(name = "id") Long id);

}
