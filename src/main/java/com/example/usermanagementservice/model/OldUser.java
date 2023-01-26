package com.example.usermanagementservice.model;


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
    public String gEtOccupation() {
        return "userOccupation : "+userOccupation  ;
    }

    @Override
    public String gEtType() {
        return "userType : " + userType ;
    }
}
