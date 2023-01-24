package com.example.usermanagementservice.model;

import javax.persistence.DiscriminatorValue;

import javax.persistence.Entity;




@Entity
//@DiscriminatorValue("OldUser")
public class OldUser extends User {

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
    public String getUserOccupation() {
        return userOccupation   ;
    }

    @Override
    public String getUserType () {
        return  userType  ;
    }
}
