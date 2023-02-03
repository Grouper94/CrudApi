package com.example.usermanagementservice.model;

import java.util.ArrayList;
import java.util.List;

public class UserInfoMonthly implements Cloneable{

    private List<String> userList;
    private String month;

    public  UserInfoMonthly() {}

    public UserInfoMonthly(List<String> userList, String month) {
        this.userList = userList ;
        this.month = month ;
    }

    public void loadDefaultUsers(){
        try {
            System.out.println("Waiting UserInfo Different load...");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        userList = new ArrayList<>();
        userList.add("User_1");
        userList.add("User_2");
        userList.add("User_3");
        userList.add("User_4");
        userList.add("User_5");
        month = "January" ;
    }

    public void addUserToList(String user){
        userList.add(user);
    }

    public List<String> getUserList() {
        return userList;
    }


    public void setMonth(String month) {
        this.month = month ;
    }

    public  String getMonth() {
        return month ;
    }

    @Override
    public UserInfoMonthly clone() throws CloneNotSupportedException {
       // return (UserInfoMonthly) super.clone();
        List<String> tmp = new ArrayList<>() ;
        for (String user : this.userList){
            tmp.add(user) ;
        }
        return new UserInfoMonthly(tmp,this.month) ;
    }

}

