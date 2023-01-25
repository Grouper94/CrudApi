package com.example.usermanagementservice.model;

import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;
import javax.persistence.Transient;



public class OldUser extends User {

    String userOccupation ;

    String userType ;

    public OldUser() {
        super();
        this.userOccupation = "Pension" ;
        this.userType = "Old" ;
    }

    public OldUser (String name,String surname,int age) {
        super(name,surname,age);
        this.userOccupation = "Pension" ;
        this.userType = "Old" ;
    }

    @Override
    public String Occupation() {
        return "userOccupation : "+userOccupation  ;
    }

    @Override
    public String Type () {
        return "userType : " + userType ;
    }
}
