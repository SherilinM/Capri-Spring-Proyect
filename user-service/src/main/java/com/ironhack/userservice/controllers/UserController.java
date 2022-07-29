package com.ironhack.userservice.controllers;

import com.ironhack.userservice.dao.User;
import com.ironhack.userservice.dto.CreateUserDTO;
import com.ironhack.userservice.dto.UserDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

public interface UserController {
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    List<User> findAll();

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    User findById(@PathVariable(name = "id") Long id);

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    User findByEmail(@PathVariable(name = "email") String email);

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void deleteUser(@PathVariable(name = "id") Long id);

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    User addUser(@Valid @RequestBody CreateUserDTO createUserDTO);

    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    User updateUser(@RequestBody @Valid UserDTO userDTO);

    @PutMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    boolean userMatchesLoggedInUser(Long userId);

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    User loadUserProfile();

}
