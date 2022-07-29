package com.ironhack.placesservice.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.placesservice.dao.*;
import com.ironhack.placesservice.dto.CreatePlaceDTO;
import com.ironhack.placesservice.dto.PlaceDTO;
import com.ironhack.placesservice.enums.*;
import com.ironhack.placesservice.repositories.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class PlaceControllerImplTest {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    Place place1;
    Place place2;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        place1 = new Place(
                "Test Place1",
                2L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location",
                "Test description",
                Category.RESTAURANT
        );
        place2 = new Place(
                "Test Place2",
                1L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location",
                "Test description",
                Category.BAR
        );
        placeRepository.saveAll(List.of(place1, place2));
    }

    @AfterEach
    void tearDown() {
        placeRepository.deleteAll();
    }


    @Test
    void findAll_Valid() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/places"))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Test Place1"));
        assertTrue(result.getResponse().getContentAsString().contains("Test Place2"));
        assertTrue(result.getResponse().getContentAsString().contains("Salt"));
        assertTrue(result.getResponse().getContentAsString().contains("Pepper"));
        assertTrue(result.getResponse().getContentAsString().contains("GLUTEN_FREE"));
        assertTrue(result.getResponse().getContentAsString().contains("BRITISH"));
    }

    @Test
    void findById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/places/" + place1.getId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("Test Place1"));
        assertFalse(result.getResponse().getContentAsString().contains("Test Place2"));
        assertTrue(result.getResponse().getContentAsString().contains("GLUTEN_FREE"));
        assertFalse(result.getResponse().getContentAsString().contains("VEGAN"));
        assertTrue(result.getResponse().getContentAsString().contains("BRITISH"));
        assertFalse(result.getResponse().getContentAsString().contains("AMERICAN"));
    }

    @Test
    void findByUserId_Valid() throws Exception {
        Place place3 = new Place(
                "Test Place3",
                2L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location 3",
                "Test description 3",
                Category.RESTAURANT
        );
        placeRepository.save(place3);
        MvcResult result = mockMvc.perform(get("/api/v1/places/user/" + place1.getAuthorId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(place1.getName()));
        assertTrue(result.getResponse().getContentAsString().contains(place2.getName()));
        assertFalse(result.getResponse().getContentAsString().contains(place3.getName()));
    }

    @Test
    void deletePlace() throws Exception {
        var repoSizeBefore = placeRepository.findAll().size();
        MvcResult result = mockMvc.perform(delete("/api/v1/places/delete/" + place1.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
        var repoSizeAfter = placeRepository.findAll().size();
        assertEquals(repoSizeBefore - 1, repoSizeAfter);
    }

    @Test
    void addPlace_Valid() throws Exception {
        var repoSizeBefore = placeRepository.findAll().size();
        CreatePlaceDTO createPlaceDTO = new CreatePlaceDTO(
                "Test Place3",
                2L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location 3",
                "Test description 3",
                Category.RESTAURANT
        );
        String body = objectMapper.writeValueAsString(createPlaceDTO);
        mockMvc.perform(post("/api/v1/places/add")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        var repoSizeAfter = placeRepository.findAll().size();
        assertEquals(repoSizeBefore + 1, repoSizeAfter);
    }

    @Test
    void addPlace_Invalid_BadRequestDueToNulls() throws Exception {
        var repoSizeBefore = placeRepository.findAll().size();
        CreatePlaceDTO createPlaceDTO = new CreatePlaceDTO(
                null,
                null,
                null,
                null,
                null,
                null
        );
        String body = objectMapper.writeValueAsString(createPlaceDTO);
        mockMvc.perform(post("/api/v1/places/add")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        var repoSizeAfter = placeRepository.findAll().size();
        assertEquals(repoSizeBefore, repoSizeAfter);
    }

    @Test
    void updatePlace_Valid_CheckResponse() throws Exception {
        PlaceDTO placeDTO = new PlaceDTO(
                place1.getId(),
                "Test Place3",
                2L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location 3",
                "Test description 3",
                Category.RESTAURANT
        );
        String body = objectMapper.writeValueAsString(placeDTO);
        MvcResult result = mockMvc.perform(put("/api/v1/places/edit")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        Place updatedPlace = placeRepository.findById(place1.getId()).get();
        assertTrue(result.getResponse().getContentAsString().contains(placeDTO.getName()));
        assertTrue(result.getResponse().getContentAsString().contains(placeDTO.getCategory().toString()));
    }

    @Test
    void updatePlace_Valid_CheckRepoUpdated() throws Exception {
        PlaceDTO placeDTO = new PlaceDTO(
                place1.getId(),
                "Test Place3",
                2L,
                "https://images.immediate.co.uk/production/volatile/sites/30/2020/08/chorizo-mozarella-gnocchi-bake-cropped-9ab73a3.jpg",
                "Test location 3",
                "Test description 3",
                Category.RESTAURANT
        );
        String body = objectMapper.writeValueAsString(placeDTO);
        MvcResult result = mockMvc.perform(put("/api/v1/places/edit")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        Place updatedPlace = placeRepository.findById(place1.getId()).get();
        assertEquals(place1.getId(), updatedPlace.getId());
        assertEquals(placeDTO.getName(), updatedPlace.getName());
        assertEquals(placeDTO.getCategory(), updatedPlace.getCategory());
    }

}