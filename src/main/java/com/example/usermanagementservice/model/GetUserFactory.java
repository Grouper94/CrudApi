package com.example.usermanagementservice.model;

import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@Component
public class GetUserFactory {

    public User
    getUser(int  age) {


        if ( age >= 1  &  age <= 18 ) {
            return new YoungUser() ;
        }

        else if (age > 18 & age < 65 ) {
            return new AdultUser() ;
        }

        else if (age >= 65 ) {
            return new OldUser() ;
        }
        return null ;
    }
}
