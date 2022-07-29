package com.ironhack.reviewservice.utils;

import com.ironhack.reviewservice.dao.Review;
import com.ironhack.reviewservice.repositories.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public void run(String... args) {

        reviewRepository.saveAll(List.of(new Review("Title 1", "Title content 1", 1L, 1L, 1L),
        new Review("Title 2", "Title content 2", 1L, 2L, 2L),
        new Review("Title 3", "Title content 3", 2L, 3L, 3L),
        new Review("Title 4", "Title content 4", 3L, 4L, 4L),
        new Review("Title 5", "Title content 5", 4L, 4L, 5L)));
    }


}
