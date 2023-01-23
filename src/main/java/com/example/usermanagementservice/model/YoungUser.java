package com.example.usermanagementservice.model;

import java.util.List;

public class YoungUser extends User {
//     String userType ;
//     String userOccupation ;


    @Override
    public String getUserOccupation() {
       // return userOccupation = "School" ;
        return "School" ;
    }

    @Override
    public String getUserType () {
        //return userType = "Young" ;
        return "Young" ;
    }






}
