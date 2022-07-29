package com.ironhack.userservice.dao;

import com.ironhack.userservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.time.LocalDate;
import java.util.List;

@MappedSuperclass
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public abstract class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    @Email
    private String email;
    @ElementCollection()
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Role> roles;
    private LocalDate createdDate = LocalDate.now();
    private LocalDate editedDate = LocalDate.now();

    public Users(String name, String username, String email, List<Role> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.roles = roles;
    }
}