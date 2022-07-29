package com.ironhack.userservice.services;

import com.ironhack.userservice.dao.User;
import com.ironhack.userservice.dto.CreateUserDTO;
import com.ironhack.userservice.dto.UserDTO;
import com.ironhack.userservice.enums.Role;
import com.ironhack.userservice.repositories.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.SecurityContextProvider;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;

    private User user1;
    private User user2;
    private List<User> userList;

    @BeforeEach
    void setUp() {
        user1 = new User(
                "TestUser1",
                "username1",
                "test@email.1",
                List.of(Role.USER),
                "testLocation1",
                "testBio1",
                "testUrl1"
        );
        user2 = new User(
                "TestUser2",
                "username2",
                "test@email.2",
                List.of(Role.USER),
                "testLocation2",
                "testBio2",
                "testUrl2"
        );
        userList = List.of(user1, user2);
        userRepository.saveAll(userList);
    }

    @AfterEach
    void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    void findAll_Valid() {
        var repoSize = userService.findAll().size();
        assertEquals(userList.size(), repoSize);
    }

    @Test
    void findById_Valid() {
        var foundUser = userService.findById(user1.getId());
        assertEquals(user1.getName(), foundUser.getName());
    }

    @Test
    void findById_Invalid_ThrowsException() {
        assertThrows(ResponseStatusException.class, () -> userService.findById(user1.getId() - 50L));
    }

    @Test
    void deleteUser() {
        var userListBefore = userService.findAll().size();
        userService.deleteUser(user1.getId());
        var userListAfter = userService.findAll().size();
        assertEquals(userListBefore - 1, userListAfter);
    }

    @Test
    void deleteUser_ThrowsException() {
        assertThrows(ResponseStatusException.class, () ->  userService.deleteUser(user1.getId() - 60L));
    }

    @Test
    void deleteUser_ThrowsException2() {
        userService.deleteUser(user1.getId());
        assertThrows(ResponseStatusException.class, () ->  userService.deleteUser(user1.getId()));
    }

    @Test
    void addUser_Valid_DatabaseIncrease() {
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "newUser",
                "newUsername",
                "new@email.com",
                "newLocation",
                "newBio",
                "newUrl"
        );
        var userListBefore = userService.findAll().size();
        User user = userService.addUser(createUserDTO);
        var userListAfter = userService.findAll().size();
        assertEquals(userListBefore + 1, userListAfter);
    }

    @Test
    void addUser_Valid_CheckValues() {
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "newUser",
                "newUsername",
                "new@email.com",
                "newLocation",
                "newBio",
                "newUrl"
        );
        User user = userService.addUser(createUserDTO);
        assertEquals(createUserDTO.getName(), user.getName());
        assertEquals(createUserDTO.getUsername(), user.getUsername());
        assertEquals(createUserDTO.getEmail(), user.getEmail());
        assertEquals(createUserDTO.getLocation(), user.getLocation());
        assertEquals(createUserDTO.getLocation(), user.getLocation());
        assertEquals(createUserDTO.getBio(), user.getBio());
        assertEquals(LocalDate.now().getDayOfMonth(), user.getCreatedDate().getDayOfMonth());
        assertEquals(LocalDate.now().getDayOfMonth(), user.getEditedDate().getDayOfMonth());
    }

    @Test
    void updateUser_Valid_NoDataBaseIncrease() {
        UserDTO userDTO = new UserDTO(
                user1.getId(),
                "newUser",
                "newUsername",
                "new@email.com",
                "newLocation",
                "newBio",
                "newUrl",
                List.of(Role.USER)
        );
        var userListBefore = userService.findAll().size();
        User user = userService.updateUser(userDTO);
        var userListAfter = userService.findAll().size();
        assertEquals(userListBefore, userListAfter);
    }

    @Test
    void updateUser_Valid_TestUpdated() {
        user1.setCreatedDate(LocalDate.now().minusYears(50L));
        userRepository.save(user1);
        System.out.println(user1.getCreatedDate());
        UserDTO userDTO = new UserDTO(
                user1.getId(),
                "newUser",
                "newUsername",
                "new@email.com",
                "newLocation",
                "newBio",
                "newUrl",
                List.of(Role.USER)
        );
        User updatedUser = userService.updateUser(userDTO);
        assertEquals(userDTO.getName(), updatedUser.getName());
        assertEquals(userDTO.getUsername(), updatedUser.getUsername());
        assertEquals(userDTO.getEmail(), updatedUser.getEmail());
        assertEquals(userDTO.getLocation(), updatedUser.getLocation());
        assertEquals(userDTO.getLocation(), updatedUser.getLocation());
        assertEquals(userDTO.getBio(), updatedUser.getBio());
        assertNotEquals(updatedUser.getCreatedDate().getYear(), updatedUser.getEditedDate().getYear());
    }

    @Test
    void updateUser_Valid_TestNullFilter() {
        user1.setCreatedDate(LocalDate.now().minusYears(50L));
        userRepository.save(user1);
        User originalUser = userService.findById(user1.getId());
        UserDTO userDTO = new UserDTO(
                user1.getId(),
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        userService.updateUser(userDTO);
        User updatedUser = userService.findById(user1.getId());
        assertEquals(originalUser.getName(), updatedUser.getName());
        assertEquals(originalUser.getUsername(), updatedUser.getUsername());
        assertEquals(originalUser.getEmail(), updatedUser.getEmail());
        assertEquals(originalUser.getLocation(), updatedUser.getLocation());
        assertEquals(originalUser.getLocation(), updatedUser.getLocation());
        assertEquals(originalUser.getBio(), updatedUser.getBio());
        assertNotEquals(updatedUser.getCreatedDate().getYear(), updatedUser.getEditedDate().getYear());
    }

    @Test
    void updateUser_ThrowsException() {
        UserDTO userDTO = new UserDTO(
                user1.getId() + 65L,
                null,
                null,
                null,
                null,
                null,
                null,
                null
        );
        assertThrows(ResponseStatusException.class, () ->  userService.updateUser(userDTO));
    }

    @Test
    void findByEmail_Valid() {
        var user = userService.findByEmail(user2.getEmail());
        assertEquals(user.getEmail(), user2.getEmail());
    }

    @Test
    void verify(){
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(user1.getEmail());
        var answer = userService.userMatchesLoggedInUser(mockPrincipal, user1.getId());
    }

    @Test
    void userLogOnOrSignUp() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn("newemail@newemail.com");
        var userListBefore = userService.findAll().size();
        userService.userLogOnOrSignUp(mockPrincipal);
        var userListAfter = userService.findAll().size();
        assertEquals(userListBefore + 1, userListAfter);
    }

    @Test
    void userLogOnOrSignUp_Valid_Existing_User() {
        Principal mockPrincipal = Mockito.mock(Principal.class);
        Mockito.when(mockPrincipal.getName()).thenReturn(user1.getEmail());
        var userListBefore = userService.findAll().size();
        userService.userLogOnOrSignUp(mockPrincipal);
        var userListAfter = userService.findAll().size();
        assertEquals(userListBefore, userListAfter);
    }
}