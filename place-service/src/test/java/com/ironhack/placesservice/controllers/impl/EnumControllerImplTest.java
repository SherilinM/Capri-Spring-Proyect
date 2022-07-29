package com.ironhack.placesservice.controllers.impl;

import com.ironhack.placesservice.enums.Category;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class EnumControllerImplTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllCuisines() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/categories"))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(Category.BAR.toString()));
        assertTrue(result.getResponse().getContentAsString().contains(Category.RESTAURANT.toString()));
        assertTrue(result.getResponse().getContentAsString().contains(Category.BRUNCH.toString()));
    }

}