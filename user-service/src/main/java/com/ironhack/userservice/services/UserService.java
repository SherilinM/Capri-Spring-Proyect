package com.ironhack.userservice.services;

import com.ironhack.userservice.dao.User;
import com.ironhack.userservice.dto.CreateUserDTO;
import com.ironhack.userservice.dto.UserDTO;

import java.security.Principal;
import java.util.List;

public interface UserService {
    List<User> findAll();

    User findById(Long id);

    void deleteUser(Long id);

    User addUser(CreateUserDTO createUserDTO);

    User updateUser(UserDTO userDTO);

    boolean userMatchesLoggedInUser(Principal principal, Long userId);

    User findByEmail(String email);

    User userLogOnOrSignUp(Principal principal);
}
