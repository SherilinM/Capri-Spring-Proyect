package com.ironhack.userservice.controllers.impl;

import com.ironhack.userservice.dao.User;
import com.ironhack.userservice.dto.CreateUserDTO;
import com.ironhack.userservice.dto.UserDTO;
import com.ironhack.userservice.repositories.UserRepository;
import com.ironhack.userservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/users")
public class UserControllerImpl implements com.ironhack.userservice.controllers.UserController {
    
    @Autowired
    private UserService userService;
    
    
    @Override
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll(){
        return userService.findAll();
    }

    @Override
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable(name = "id") Long id){
        return userService.findById(id);
    }

    @GetMapping("/email/{email}")
    @ResponseStatus(HttpStatus.OK)
    public User findByEmail(String email) {
        return userService.findByEmail(email);
    }

    @Override
    @GetMapping("/{id}/verify")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public boolean userMatchesLoggedInUser(@PathVariable(name="id") Long userId) {
        Principal principal = SecurityContextHolder.getContext().getAuthentication();
        return userService.userMatchesLoggedInUser(principal,userId);
    }

    @Override
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable(name = "id") Long id){
        userService.deleteUser(id);
    }

    @Override
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@Valid @RequestBody CreateUserDTO createUserDTO){
        return userService.addUser(createUserDTO);
    }

    @Override
    @PutMapping("/edit")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public User updateUser(@RequestBody @Valid UserDTO userDTO){
        return userService.updateUser(userDTO);
    }

    @GetMapping("/profile")
    @ResponseStatus(HttpStatus.OK)
    public User loadUserProfile(){
        return userService.userLogOnOrSignUp(SecurityContextHolder.getContext().getAuthentication());
    }


}
