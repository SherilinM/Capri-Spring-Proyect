package com.ironhack.userservice.services.impl;

import com.ironhack.userservice.dao.User;
import com.ironhack.userservice.dto.CreateUserDTO;
import com.ironhack.userservice.dto.UserDTO;
import com.ironhack.userservice.enums.Role;
import com.ironhack.userservice.repositories.UserRepository;
import com.ironhack.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No User found with ID: " + id);
        return user.get();
    }

    @Override
    public void deleteUser(Long id) {
        User user = findById(id);
        userRepository.delete(user);
    }

    @Override
    public User addUser(CreateUserDTO createUserDTO){
        return userRepository.save(convertNewUserDTOToUser(createUserDTO));
    }

    @Override
    public User updateUser(UserDTO userDTO){
        User user = findById(userDTO.getId());
        return userRepository.save(updateUserWithUserDTO(userDTO, user));
    }

    public boolean userMatchesLoggedInUser(Principal principal, Long userId){
        User user = findById(userId);
        String loggedInEmail = principal.getName().toLowerCase().trim();
        String userEmail = user.getEmail().toLowerCase().trim();
        return loggedInEmail.equals(userEmail);
    }

    @Override
    public User findByEmail(String email) {
        Optional<User> user = userRepository.findByEmail(email);
        if(user.isEmpty()) throw new UsernameNotFoundException("No user found with email: " + email);
        return user.get();
    }

    @Override
    public User userLogOnOrSignUp(Principal principal) {
        try{
            return findByEmail(principal.getName());
        } catch (UsernameNotFoundException e){
            User user = new User();
            user.setUsername(randomUsernameFromEmail(principal.getName()));
            user.setEmail(principal.getName());
            user.setName(user.getName());
            user.setBio("");
            user.setLocation("");
            user.setRoles(List.of(Role.USER));
            return userRepository.save(user);
        }
    }

    private String randomUsernameFromEmail(String email){
        String startOfEmail = email.substring(0, 3);
        int randomNumber = 111 + (int)(Math.random() * ((5464738 - 111) + 1));
        return startOfEmail.concat(String.valueOf(randomNumber));
    }

    private User updateUserWithUserDTO(UserDTO userDTO, User user) {
        if (userDTO.getName() != null) {
            user.setName(userDTO.getName());
        }
        if (userDTO.getUsername() != null) {
            user.setUsername(userDTO.getUsername());
        }
        if (userDTO.getEmail() != null) {
            user.setEmail(userDTO.getEmail());
        }
        if (userDTO.getLocation() != null) {
            user.setLocation(userDTO.getLocation());
        }
        if (userDTO.getBio() != null) {
            user.setBio(userDTO.getBio());
        }
        if (userDTO.getPictureUrl() != null) {
            user.setPictureUrl(userDTO.getPictureUrl());
        }
        user.setEditedDate(LocalDate.now());
        return user;
    }

    private User convertNewUserDTOToUser(CreateUserDTO createUserDTO) {
        return new User(
                createUserDTO.getName(),
                createUserDTO.getUsername(),
                createUserDTO.getEmail(),
                List.of(Role.USER),
                createUserDTO.getLocation(),
                createUserDTO.getBio(),
                createUserDTO.getPictureUrl()
        );
    }
}
