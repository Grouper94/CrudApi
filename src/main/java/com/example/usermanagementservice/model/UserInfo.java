package com.example.usermanagementservice.model;

public class UserInfo {

    private static UserInfo userInfo = new UserInfo() ;
    
    final String message = "There are 3 types of users: Young, Adults and Old";
    
    private  UserInfo() {
        try {
        Thread.sleep(10000);
        } catch (InterruptedException e) {
        e.printStackTrace();
        }
    }
    
    public static UserInfo getUserInfo()  {
        return  userInfo  ;
    }

    public String getMessage() {
        return message;
    }
}

