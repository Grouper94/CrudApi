package com.example.usermanagementservice.model;

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
    public String gEtOccupation() {
        return "userOccupation : "+userOccupation  ;
    }

    @Override
    public String gEtType() {
        return "userType : " + userType ;
    }
}
