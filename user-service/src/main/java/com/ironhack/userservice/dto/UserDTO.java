package com.ironhack.userservice.dto;

import com.ironhack.userservice.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDTO {

    private Long id;
    private String name;
    private String username;
    @Email
    private String email;
    private String location;
    private String bio;
    private String pictureUrl;
    private List<Role> roles;

    public UserDTO(String name, String username, String email, String location, String bio, String pictureUrl, List<Role> roles) {
        this.name = name;
        this.username = username;
        this.email = email;
        this.location = location;
        this.bio = bio;
        this.pictureUrl = pictureUrl;
        this.roles = roles;
    }
}
