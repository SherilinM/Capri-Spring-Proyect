package com.ironhack.userservice.controllers.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ironhack.userservice.dao.User;
import com.ironhack.userservice.dto.CreateUserDTO;
import com.ironhack.userservice.dto.UserDTO;
import com.ironhack.userservice.enums.Role;
import com.ironhack.userservice.repositories.UserRepository;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
class UserControllerImplTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private final ObjectMapper objectMapper = new ObjectMapper();


    private User user1;
    private User user2;
    private List<User> userList;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
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
    void findAll_Valid() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/users"))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("TestUser1"));
        assertTrue(result.getResponse().getContentAsString().contains("TestUser2"));
        assertTrue(result.getResponse().getContentAsString().contains("USER"));
    }

    @Test
    void findById() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/users/" + user1.getId()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("TestUser1"));
        assertFalse(result.getResponse().getContentAsString().contains("TestUser2"));
        assertTrue(result.getResponse().getContentAsString().contains("USER"));
    }

    @Test
    void deleteUser() throws Exception {
        var repoSizeBefore = userRepository.findAll().size();
        MvcResult result = mockMvc.perform(delete("/api/v1/users/" + user1.getId()))
                .andExpect(status().isNoContent())
                .andReturn();
        var repoSizeAfter = userRepository.findAll().size();
        assertEquals(repoSizeBefore - 1, repoSizeAfter);
    }

    @Test
    void addUser_Valid() throws Exception {
        var repoSizeBefore = userRepository.findAll().size();
        CreateUserDTO createUserDTO = new CreateUserDTO(
                "newUser",
                "newUsername",
                "new@email.com",
                "newLocation",
                "newBio",
                "newUrl"
        );
        String body = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        var repoSizeAfter = userRepository.findAll().size();
        assertEquals(repoSizeBefore + 1, repoSizeAfter);
    }

    @Test
    void addUser_Invalid_BadRequestDueToNulls() throws Exception {
        var repoSizeBefore = userRepository.findAll().size();
        CreateUserDTO createUserDTO = new CreateUserDTO(
                null,
                null,
                null,
                null,
                null,
                null
        );
        String body = objectMapper.writeValueAsString(createUserDTO);
        mockMvc.perform(post("/api/v1/users")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andReturn();
        var repoSizeAfter = userRepository.findAll().size();
        assertEquals(repoSizeBefore, repoSizeAfter);
    }

    @Test
    void updateUser_Valid_CheckResponse() throws Exception {
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
        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult result = mockMvc.perform(put("/api/v1/users/edit")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        User updatedUser = userRepository.findById(user1.getId()).get();
        assertTrue(result.getResponse().getContentAsString().contains(userDTO.getName()));
        assertTrue(result.getResponse().getContentAsString().contains(userDTO.getUsername()));
        assertTrue(result.getResponse().getContentAsString().contains(userDTO.getEmail()));
        assertTrue(result.getResponse().getContentAsString().contains(userDTO.getLocation()));
        assertTrue(result.getResponse().getContentAsString().contains(userDTO.getBio()));
        assertTrue(result.getResponse().getContentAsString().contains(userDTO.getPictureUrl()));

    }

    @Test
    void updateUser_Valid_CheckRepoUpdated() throws Exception {
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
        String body = objectMapper.writeValueAsString(userDTO);
        MvcResult result = mockMvc.perform(put("/api/v1/users/edit")
                        .content(body).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isAccepted())
                .andReturn();
        User updatedUser = userRepository.findById(user1.getId()).get();
        assertEquals(userDTO.getName(), updatedUser.getName());
        assertEquals(userDTO.getUsername(), updatedUser.getUsername());
        assertEquals(userDTO.getEmail(), updatedUser.getEmail());
        assertEquals(userDTO.getLocation(), updatedUser.getLocation());
        assertEquals(userDTO.getLocation(), updatedUser.getLocation());
        assertEquals(userDTO.getBio(), updatedUser.getBio());
    }

    @Test
    void findByEmail() throws Exception {
        MvcResult result = mockMvc.perform(get("/api/v1/users/email/" + user1.getEmail()))
                .andExpect(status().isOk())
                .andReturn();
        assertTrue(result.getResponse().getContentAsString().contains("TestUser1"));
        assertFalse(result.getResponse().getContentAsString().contains("TestUser2"));
        assertTrue(result.getResponse().getContentAsString().contains("USER"));
    }

}
