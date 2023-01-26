package com.example.usermanagementservice.unitTests.controller;

import com.example.usermanagementservice.controller.UserController;
import com.example.usermanagementservice.controller.UserControllerImpl;
import com.example.usermanagementservice.exceptions.UserNotFoundException;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;
import java.util.*;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

    static final String NAME_1 = "John" ;
    static final String SUR_NAME_1 = "Lwe" ;
    static final String NAME_2 = "George" ;
    static final String SUR_NAME_2 = "Georgiou" ;


    @Test
    void addUser_thenReturnValidResponse() throws Exception {

        final User USER_1 = new User (3, NAME_2, SUR_NAME_1,77);
        
        Mockito.when(userService.addUser(USER_1)).thenReturn(USER_1);
        ResponseEntity<Void> expected = userController.addUser(NAME_2, SUR_NAME_1,77) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertNull(expected.getBody());

    }

    @Test
    void updateUser_whenGivenIdExists_thenReturnValidResponse() throws Exception {
     
        final User USER_2 = new User (4, NAME_2, SUR_NAME_2,55);
        
        doNothing().when(userService).updateUser(USER_2);
        ResponseEntity<Void> expected = userController.updateUser(USER_2) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful() );
    }

    @Test
    void findUserById_whenUserExists_thenReturnValidResponseAndUser() throws Exception {
        final User USER_1 = new User (3, NAME_2, SUR_NAME_1,77);
        Mockito.when(userService.getUserById(3)).thenReturn(Optional.of(USER_1));
        ResponseEntity<Optional<User>> expected = userController.findUserById(USER_1.getId()) ;

        assertThat(Objects.requireNonNull(expected.getBody()).get().getId()).isEqualTo(3);
        assertTrue(expected.getStatusCode().is2xxSuccessful()) ;
    }

    @Test
    void findUsersByName_whenUserExists_thenReturnValidResponseAndListOfUsers()  {

        final User USER_1 = new User (3, NAME_2, SUR_NAME_1,77);
        final User USER_2 = new User (4, NAME_2, SUR_NAME_2,55);
        
        final List<User> USERS = new ArrayList<>(Arrays.asList(USER_1,USER_2));
        Mockito.when(userService.getUserByName(USER_1.getName())).thenReturn(USERS);
        ResponseEntity<List<User>> expected = userController.findUserByName(USER_1.getName()) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertThat(Objects.requireNonNull(expected.getBody()).get(0).getName()).isEqualTo(USER_1.getName());
        assertThat(expected.getBody().get(1).getName()).isEqualTo(USER_2.getName());
    }

    @Test
    void findAll_whenUsersExists_thenReturnValidResponse() throws Exception {

        final User USER_1 = new User (3, NAME_1, SUR_NAME_1,77);
        final User USER_2 = new User (4, NAME_2, SUR_NAME_2,55);
        
        final  List<User> USERS = new ArrayList<>(Arrays.asList(USER_1,USER_2));
        Mockito.when(userService.getAllUsers()).thenReturn(USERS);
        ResponseEntity <List<User>> expected = userController.findAll() ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertTrue(expected.hasBody());
    }

    @Test
    void deleteUserById_whenUserExists_thenReturnValidResponse() throws Exception {
        
        final User USER_1 = new User (3, NAME_2, SUR_NAME_1,77);
        doNothing().when(userService).deleteUser((USER_1).getId());
        ResponseEntity<Optional<String>> expected = userController.deleteUser(USER_1.getId()) ;

        assertTrue(expected.getStatusCode().is2xxSuccessful());
        assertThat(expected.getBody()).isNull();
    }
    
    @Test
    void updateUser_whenGivenIdNotExists_thenReturn_404Response() throws Exception {

        final User USER_1 = new User (3566, NAME_2, SUR_NAME_1,77);

        doThrow(new UserNotFoundException()).when(userService).updateUser(USER_1);
        ResponseEntity<Void> expected = userController.updateUser(USER_1) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
        assertThat(expected.getBody()).isNull();
    }


    @Test
    void findUserById_whenUserNotExists_thenReturn_404Response() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).getUserById(101);

        ResponseEntity<Optional<User>> expected = userController.findUserById(101) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
    }

    @Test
    void findUsersByName_whenUserNotExists_thenReturnNonValidResponse()  {
        final User USER_1 = new User (3566, NAME_2, SUR_NAME_1,77);

        Mockito.when(userService.getUserByName(USER_1.getName())).thenReturn(null);
        ResponseEntity<List<User>> expected = userController.findUserByName(NAME_2) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
    }

    @Test
    void findAll_whenUsersNotExists_thenReturnNonValidResponse() throws Exception {

        Mockito.when(userService.getAllUsers()).thenReturn(List.of() );
        ResponseEntity <List<User>> expected = userController.findAll() ;

        assertTrue(expected.getStatusCode().is4xxClientError());
    }

    @Test()
    void deleteUserById_whenUserNotExists_thenReturn404Response() throws Exception {
        doThrow(new UserNotFoundException()).when(userService).deleteUser(101);

        ResponseEntity<Optional<String>> expected = userController.deleteUser(101) ;

        assertTrue(expected.getStatusCode().is4xxClientError());
    }

//    @Test
//    void addNonValidUser_thenReturnNonValidResponse() throws Exception {
//
//        final User USER_1 = new User ();
//
//        Mockito.when(userService.addUser(null)).thenReturn(null) ;
//        ResponseEntity<Void> expected = userController.addUser(null,null,USER_1.getAge()) ;
//        System.out.println(expected.getStatusCodeValue());
//        assertTrue(expected.getStatusCode().is4xxClientError());
//        //assertNull(expected.getBody());
//
//    }
}

