package com.example.usermanagementservice.service;


import com.example.usermanagementservice.exceptions.IdNotFoundException;
import com.example.usermanagementservice.model.User;
import org.webjars.NotFoundException;

import java.util.List;
import java.util.Optional;


public interface UserService {

    User addUser( User user ) throws Exception;

    void updateUser( User user ) throws IdNotFoundException;

    void addXRandomUsers( int X ) throws Exception;

    Optional< User > getUserById( Integer id ) throws IdNotFoundException;

    List< User > getUserByName( String name ) ;

    List< User > getAllUsers() throws IdNotFoundException;

    void deleteAllUsers() throws Exception;

    void deleteUser( int id ) throws IdNotFoundException;

}

