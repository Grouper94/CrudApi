package com.example.usermanagementservice.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.MappedSuperclass;


@Entity
//@DiscriminatorValue("AdultUser")
public class AdultUser extends User {
    public AdultUser () {
        super();
        this.userOccupation = "Work" ;
        this.userType = "Adult" ;
    }

    public AdultUser (String name,String surname,int age) {
        super(name,surname,age);
        this.userOccupation = "Work" ;
        this.userType = "Adult" ;
    }


    @Override
    public String getUserOccupation() {
        return userOccupation  ;
    }

    @Override
    public String getUserType () {
        return userType ;
    }
}
