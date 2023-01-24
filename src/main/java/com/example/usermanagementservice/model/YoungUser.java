package com.example.usermanagementservice.model;


import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
//@DiscriminatorValue("YoungUser")
public class YoungUser extends User {
    public YoungUser  () {
        super();
        this.userOccupation = "School" ;
        this.userType = "Young" ;
    }

    public YoungUser  (String name,String surname,int age) {
        super(name,surname,age);
        this.userOccupation = "School" ;
        this.userType = "Young" ;
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
