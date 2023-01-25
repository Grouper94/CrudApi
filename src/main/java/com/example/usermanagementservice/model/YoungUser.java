package com.example.usermanagementservice.model;


public class YoungUser extends User {

    String userOccupation ;
    String userType ;
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
    public String Occupation() {
       return "userOccupation : "+userOccupation  ;
    }

    @Override
    public String Type () {
         return "userType : " + userType ;
    }

}
