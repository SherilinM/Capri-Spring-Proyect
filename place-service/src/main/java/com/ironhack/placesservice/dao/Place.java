package com.ironhack.placesservice.dao;

import com.ironhack.placesservice.enums.Category;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Long authorId;

    private String location;

    private String imageUrl;
    private String description;

    @Enumerated(EnumType.STRING)
    private Category category;

    private LocalDate createdDate = LocalDate.now();
    private LocalDate editedDate = LocalDate.now();

    public Place(String name, Long authorId, String imageUrl, String location, String description, Category category) {
        this.name = name;
        this.authorId = authorId;
        this.imageUrl = imageUrl;
        this.location = location;
        this.description = description;
        this.category = category;
    }

}
