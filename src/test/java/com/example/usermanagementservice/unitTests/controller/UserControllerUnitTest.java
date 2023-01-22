package com.example.usermanagementservice.unitTests.controller;

import com.example.usermanagementservice.controller.UserController;
import com.example.usermanagementservice.controller.UserControllerImpl;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;


@WebMvcTest(UserController.class)
class UserControllerUnitTest {
    @Autowired
    private  UserControllerImpl userController;

    @Autowired
    ObjectMapper mapper ;

    @MockBean
    UserService userService ;

    static final String name1 = "John" ;

    static final String surName1 = "Lwe" ;

    static final String notUsed = "Jim" ;

    static final String name2 = "George" ;

    static final String surName2 = "Georgiou" ;


    @Test
    void addUser_thenReturnValidResponse() throws Exception {

        User user1 = new User (3,name2,surName1,77);
        Mockito.when(userService.addUser(user1)).thenReturn(user1);
        ResponseEntity<Void> expected = userController.addUser(name2,surName1,77) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertThat(expected.getBody()).isNull();

    }

    @Test
    void updateUser_whenGivenIdExists_thenReturnValidResponse() throws Exception {
     
        User user2 = new User (4,name2,surName2,55);
        doNothing().when(userService).updateUser(user2);
        ResponseEntity<Void> expected = userController.updateUser(user2) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful() );
    }

    @Test
    void findUserById_whenUserExists_thenReturnValidResponseAndUser() throws Exception {
        User user1 = new User (3,name2,surName1,77);
        Mockito.when(userService.getUserById(3)).thenReturn(Optional.of(user1));
        ResponseEntity<Optional<User>> expected = userController.findUserById(user1.getId()) ;

        assertThat(Objects.requireNonNull(expected.getBody()).get().getId()).isEqualTo(3);
        assertTrue(expected.getStatusCode().is2xxSuccessful()) ;
    }

    @Test
    void findUsersByName_whenUserExists_thenReturnValidResponseAndListOfUsers()  {
        
        User user1 = new User (3,name2,surName1,77);
        User user2 = new User (4,name2,surName2,55);
        
        List<User> userList = new ArrayList<>(Arrays.asList(user1,user2));
        Mockito.when(userService.getUserByName(user1.getName())).thenReturn(userList);
        ResponseEntity<List<User>> expected = userController.findUserByName(user1.getName()) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertThat(Objects.requireNonNull(expected.getBody()).get(0).getName()).isEqualTo(user1.getName());
        assertThat(expected.getBody().get(1).getName()).isEqualTo(user2.getName());
    }

    @Test
    void findAll_whenUsersExists_thenReturnValidResponse() throws Exception {

        User user1 = new User (3,name2,surName1,77);
        User user2 = new User (4,name2,surName2,55);
        
        List<User> users = new ArrayList<>(Arrays.asList(user1,user2));
        Mockito.when(userService.getAllUsers()).thenReturn(users);
        ResponseEntity <List<User>> expected = userController.findAll() ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertTrue(expected.hasBody());
    }

    @Test
    void deleteUserById_whenUserExists_thenReturnValidResponse() throws Exception {
        
        User user1 = new User (3,name2,surName1,77);
        doNothing().when(userService).deleteUser((user1).getId());
        ResponseEntity<Optional<String>> expected = userController.deleteUser(user1.getId()) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertThat(expected.getBody()).isNull();
    }
    
    @Test
    void updateUser_whenGivenIdNotExists_thenReturn_404Response() throws Exception {

        User user1 = new User (3566,name2,surName1,77);
        
        doThrow(new Exception()).when(userService).updateUser(user1);
        ResponseEntity<Void> expected = userController.updateUser(user1) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
        assertThat(expected.getBody()).isNull();
    }


    @Test
    void findUserById_whenUserNotExists_thenReturn_404Response() throws Exception {
        doThrow(new Exception()).when(userService).getUserById(101);

        ResponseEntity<Optional<User>> expected = userController.findUserById(101) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
    }

    @Test
    void findUsersByName_whenUserNotExists_thenReturnNonValidResponse()  {
        User user1 = new User (3566,name2,surName1,77);

        Mockito.when(userService.getUserByName(user1.getName())).thenReturn(null);
        ResponseEntity<List<User>> expected = userController.findUserByName(name2) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
    }

    @Test()
    void deleteUserById_whenUserNotExists_thenReturn404Response() throws Exception {
        doThrow(new Exception()).when(userService).deleteUser(101);

        ResponseEntity<Optional<String>> expected = userController.deleteUser(101) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
    }
}