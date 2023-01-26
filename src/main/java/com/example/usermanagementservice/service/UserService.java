package com.example.usermanagementservice.service;


import com.example.usermanagementservice.exceptions.UserNotFoundException;
import com.example.usermanagementservice.model.User;

import java.util.List;
import java.util.Optional;


public interface UserService {

    User addUser( User user ) throws Exception;

    void updateUser( User user ) throws UserNotFoundException;

    void addXRandomUsers( int X ) throws Exception;

    Optional< User > getUserById( Integer id ) throws UserNotFoundException;

    List< User > getUserByName( String name ) ;

    List< User > getAllUsers() throws UserNotFoundException;

    void deleteAllUsers() throws Exception;

    void deleteUser( int id ) throws UserNotFoundException;

}

