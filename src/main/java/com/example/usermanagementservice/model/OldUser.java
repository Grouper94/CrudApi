package com.example.usermanagementservice.model;

public class OldUser extends User {



    @Override
    public String getUserOccupation() {
       return  "Pension" ;
    }

    @Override
    public String getUserType () {
          return  "Old" ;
    }
}
