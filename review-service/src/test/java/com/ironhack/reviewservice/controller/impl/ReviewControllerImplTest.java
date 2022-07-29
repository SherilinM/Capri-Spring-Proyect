package com.ironhack.reviewservice.controller.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.reviewservice.dao.Review;
import com.ironhack.reviewservice.dto.ReviewDTO;
import com.ironhack.reviewservice.repositories.ReviewRepository;
import com.ironhack.reviewservice.service.ReviewService;
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
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class ReviewControllerImplTest {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();

    private Review review1;
    private Review review2;
    private Review review3;
    private Review review4;
    private Review review5;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();

        review1 = new Review("Title 1", "Title content 1", 1L, 1L, 1L);
        review2 = new Review("Title 2", "Title content 2", 1L, 2L, 2L);
        review3 = new Review("Title 3", "Title content 3", 2L, 3L, 3L);
        review4 = new Review("Title 4", "Title content 4", 3L, 4L, 4L);
        review5 = new Review("Title 5", "Title content 5", 4L, 4L, 5L);

        reviewRepository.saveAll(List.of(review1, review2, review3, review4, review5));
    }

    @AfterEach
    void tearDown() {
        reviewRepository.deleteAll();
    }

    @Test
    void getByPlaceId() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/reviews/place/" + review1.getPlaceId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(review1.getTitle()));
        assertTrue(result.getResponse().getContentAsString().contains(review2.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review5.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review4.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review3.getTitle()));
        assertEquals("Hello", result.getResponse().getContentAsString());
    }

    @Test
    void getByUserId() throws Exception{
        MvcResult result = mockMvc.perform(get("/api/v1/reviews/user/" + review1.getPlaceId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(review1.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review5.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review4.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review3.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review2.getTitle()));
    }

    @Test
    void getById() throws Exception{
        MvcResult result = mockMvc.perform(get("/api/v1/reviews/" + review1.getId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains(review1.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review5.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review4.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review3.getTitle()));
        assertFalse(result.getResponse().getContentAsString().contains(review2.getTitle()));
    }

    @Test
    void editReview() throws Exception{
        ReviewDTO reviewDTO = new ReviewDTO(review1.getId(), "NewTitle", "NewContent",
                review1.getPlaceId(), review1.getUserId(), review1.getRatingId());
        int repoSizeBefore = reviewRepository.findAll().size();
        String body = objectMapper.writeValueAsString(reviewDTO);
        MvcResult result = mockMvc.perform(put("/api/v1/reviews/edit")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        int repoSizeAfter = reviewRepository.findAll().size();
        assertEquals(repoSizeBefore, repoSizeAfter);
        assertTrue(result.getResponse().getContentAsString().contains(reviewDTO.getContent()));
        assertEquals(reviewDTO.getTitle(), reviewRepository.findById(review1.getId()).get().getTitle());
    }

    @Test
    void addReview() throws Exception {
        ReviewDTO reviewDTO = new ReviewDTO( "NewTitle", "NewContent",
                review1.getPlaceId(), review1.getUserId(), review1.getRatingId());
        int repoSizeBefore = reviewRepository.findAll().size();
        String body = objectMapper.writeValueAsString(reviewDTO);
        MvcResult result = mockMvc.perform(post("/api/v1/reviews/add")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        int repoSizeAfter = reviewRepository.findAll().size();
        assertEquals(repoSizeBefore + 1, repoSizeAfter);
        assertTrue(result.getResponse().getContentAsString().contains(reviewDTO.getContent()));
    }

    @Test
    void deleteReview() throws Exception{
        int repoSizeBefore = reviewRepository.findAll().size();
        MvcResult result = mockMvc.perform(delete("/api/v1/reviews/delete/" + review1.getId()))
                .andExpect(status().isAccepted())
                .andReturn();
        int repoSizeAfter = reviewRepository.findAll().size();
        assertEquals(repoSizeBefore - 1, repoSizeAfter);
        Optional<Review> reviewOptional = reviewRepository.findById(review1.getId());
        assertTrue(reviewOptional.isEmpty());
    }
}