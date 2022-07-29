package com.ironhack.favouritesservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.favouritesservice.controller.FavouriteController;
import com.ironhack.favouritesservice.dao.Favourite;
import com.ironhack.favouritesservice.dto.FavouriteDTO;
import com.ironhack.favouritesservice.repositories.FavouriteRepository;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class FavouriteControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private FavouriteController favouriteController;
    @Autowired
    private FavouriteRepository favouriteRepository;

    Favourite testFav1;
    Favourite testFav2;
    Favourite testFav3;
    Favourite testFav4;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        testFav1 = new Favourite(1L, 1L);
        testFav2 = new Favourite(1L, 2L);
        testFav3 = new Favourite(2L, 3L);
        testFav4 = new Favourite(3L, 4L);
        favouriteRepository.saveAll(List.of(testFav1,testFav2,testFav3,testFav4));
    }

    @AfterEach
    void tearDown() {
        favouriteRepository.deleteAll();
    }

    @Test
    void findAll() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/favourites"))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("\"placeId\":1"));
        assertTrue(result.getResponse().getContentAsString().contains("\"placeId\":2"));
        assertTrue(result.getResponse().getContentAsString().contains("\"placeId\":3"));
        assertTrue(result.getResponse().getContentAsString().contains("\"placeId\":4"));
    }

    @Test
    void findById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/favourites/" + testFav1.getId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("\"placeId\":1"));
        assertFalse(result.getResponse().getContentAsString().contains("\"placeId\":2"));
        assertFalse(result.getResponse().getContentAsString().contains("\"placeId\":3"));
        assertFalse(result.getResponse().getContentAsString().contains("\"placeId\":4"));
    }

    @Test
    void findById_Throws() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/favourites/" + (testFav1.getId() + 600L)))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @Test
    void getAllFavouritePlacesByUserId() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/favourites/userid/" + testFav1.getUserId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("\"id\":1"));
        assertTrue(result.getResponse().getContentAsString().contains("\"id\":2"));
        assertTrue(result.getResponse().getContentAsString().contains("category"));
        assertTrue(result.getResponse().getContentAsString().contains("name"));
        assertFalse(result.getResponse().getContentAsString().contains("\"id\":4"));
    }


    @Test
    void addToFavourites() throws Exception {
       var repoSizeBefore = favouriteRepository.findAll().size();
       String body = objectMapper.writeValueAsString(new FavouriteDTO(22L, 33L));
        mockMvc.perform(post("/api/v1/favourites/add")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
        var repoSizeAfter = favouriteRepository.findAll().size();
        assertEquals(repoSizeBefore + 1, repoSizeAfter);
    }

    @Test
    void addToFavourites_Throws() throws Exception {
        var repoSizeBefore = favouriteRepository.findAll().size();
        String body = objectMapper.writeValueAsString(new FavouriteDTO(null, null));
        mockMvc.perform(post("/api/v1/favourites/add")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
        var repoSizeAfter = favouriteRepository.findAll().size();
        assertEquals(repoSizeBefore, repoSizeAfter);
    }

}