package com.example.usermanagementservice.model;

import javax.persistence.*;



public class AdultUser extends User {

    String userOccupation ;
    String userType ;
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
    public String Occupation() {
        return "userOccupation : "+userOccupation  ;
    }

    @Override
    public String Type () {
        return "userType : " + userType ;
    }
}
