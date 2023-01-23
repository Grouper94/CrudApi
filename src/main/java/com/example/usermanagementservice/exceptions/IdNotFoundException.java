package com.example.usermanagementservice.exceptions;

public class IdNotFoundException extends RuntimeException {

    public IdNotFoundException() {
        super("Id Does Not Found in DB");
    }



}
