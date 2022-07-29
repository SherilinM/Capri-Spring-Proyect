package com.ironhack.placesservice.utils;

import com.github.javafaker.Faker;
import com.ironhack.placesservice.dao.*;
import com.ironhack.placesservice.enums.Category;
import com.ironhack.placesservice.repositories.PlaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Component
public class SampleDataLoader implements CommandLineRunner {

    @Autowired
    private PlaceRepository placeRepository;
    @Autowired
    private Faker faker;


    @Override
    public void run(String... args) {

        Category[] categories = Category.class.getEnumConstants();

        List<String> sampleMethod = IntStream.rangeClosed(1, 5)
                .mapToObj(i -> faker.chuckNorris().fact()).collect(Collectors.toList());


        //Place(String name, List<String> method, Long authorId, String imageUrl, String location, description,cat)
        List<Place> samplePlaces = IntStream.rangeClosed(1, 20)
                .mapToObj(i -> new Place(
                        faker.commerce().productName(),           //Place name
                        faker.number().numberBetween(1L, 20L),    //Autor id
                        randomPlaceImage(),
                        faker.address().fullAddress(),
                        faker.lorem().characters(50,200),
                        categories[faker.number().numberBetween(0, categories.length)]))
                .collect(Collectors.toList());

        //placeRepository.saveAll(realSamplePlaces());
        placeRepository.saveAll(samplePlaces);
    }

    private String randomPlaceImage() {
        List<String> imageList = List.of(
                "../../../../../assets/images/16.jpeg",
                "../../../../../assets/images/19.jpeg",
                "../../../../../assets/images/amazonia4.jpeg",
                "../../../../../assets/images/azotea1.jpeg",
                "../../../../../assets/images/habanera.jpeg",
                "../../../../../assets/images/habanera2.jpeg",
                "../../../../../assets/images/habanera3.jpeg",
                "../../../../../assets/images/habanera4.jpeg",
                "../../../../../assets/images/marieta.jpeg",
                "../../../../../assets/images/marieta2.jpeg",
                "../../../../../assets/images/marieta3.jpeg",
                "../../../../../assets/images/marieta4.jpeg",
                "../../../../../assets/images/perrachica.jpeg",
                "../../../../../assets/images/perrachica2.jpeg",
                "../../../../../assets/images/perrachica3.jpeg",
                "../../../../../assets/images/perrachica4.jpeg",
                "../../../../../assets/images/pumpumcafe.jpeg",
                "../../../../../assets/images/pumpumcafe2.jpeg",
                "../../../../../assets/images/pumpumcafe4.jpeg"
        );
        return imageList.get(faker.number().numberBetween(0, 9));
    }

}
