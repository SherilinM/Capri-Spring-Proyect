package com.ironhack.userservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserDTO {


    @NotNull
    private String name;
    @NotNull
    private String username;
    @Email
    @NotNull
    private String email;
    private String location;
    private String bio;
    private String pictureUrl;

}
