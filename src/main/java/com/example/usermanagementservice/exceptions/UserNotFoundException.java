package com.example.usermanagementservice.exceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException() {
        super("User  Not Found in DB");
    }



}
