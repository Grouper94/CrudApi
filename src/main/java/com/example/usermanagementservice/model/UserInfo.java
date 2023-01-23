package com.example.usermanagementservice.model;

public class UserInfo {

    final String message = "There are 3 types of users: Young, Adults and Old";

    public UserInfo() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public String getMessage() {
        return message;
    }
}
