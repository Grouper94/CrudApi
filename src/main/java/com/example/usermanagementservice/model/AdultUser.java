package com.example.usermanagementservice.model;

public class AdultUser extends User {


    @Override
    public String getUserOccupation() {
       return "Work" ;
    }

    @Override
    public String getUserType () {
         return   "Adult" ;
    }
}
