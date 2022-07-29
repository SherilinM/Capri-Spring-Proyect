package com.ironhack.ratingservice.utils;

import com.github.javafaker.Faker;
import com.ironhack.ratingservice.dao.Rating;
import com.ironhack.ratingservice.repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private RatingRepository ratingRepository;
    @Autowired
    private Faker faker;


    @Override
    public void run(String... args) {

        List<Rating> sampleRatings = IntStream.rangeClosed(1, 100)
                .mapToObj(i -> new Rating(
                        faker.number().numberBetween(1, 5),
                        faker.number().numberBetween(1L, 23L),
                        faker.number().numberBetween(1L, 21L)
                ))
                .collect(Collectors.toList());

        ratingRepository.saveAll(sampleRatings);
    }

}
