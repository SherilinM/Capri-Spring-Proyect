package com.ironhack.favouritesservice.utils;

import com.github.javafaker.Faker;
import com.ironhack.favouritesservice.dao.Favourite;
import com.ironhack.favouritesservice.repositories.FavouriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private FavouriteRepository favouriteRepository;

    @Autowired
    private Faker faker;

    @Override
    public void run(String... args) {

        List<Favourite> mostPopularRecipe = createFavouritesForRecipe(1L, 20);
        List<Favourite> secondMostPopular = createFavouritesForRecipe(2L, 10);
        List<Favourite> thirdMostPopular = createFavouritesForRecipe(3L, 5);

        List<Favourite> favourites = IntStream.rangeClosed(1, 30)
                .mapToObj(i -> new Favourite(
                        faker.number().numberBetween(1L, 21L),
                        faker.number().numberBetween(1L, 23L)
                )).collect(Collectors.toList());

        favouriteRepository.saveAll(favourites);
        favouriteRepository.saveAll(mostPopularRecipe);
        favouriteRepository.saveAll(secondMostPopular);
        favouriteRepository.saveAll(thirdMostPopular);
    }

    private List<Favourite> createFavouritesForRecipe(Long recipeId, int amount) {
        List<Favourite> favourites = IntStream.rangeClosed(1, amount)
                .mapToObj(i -> new Favourite(
                        faker.number().numberBetween(1L, 21L),
                        recipeId
                )).collect(Collectors.toList());
        return favourites;
    }
}
