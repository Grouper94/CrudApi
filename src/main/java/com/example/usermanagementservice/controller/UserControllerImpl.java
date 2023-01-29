package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.exceptions.UserNotFoundException;
import com.example.usermanagementservice.model.*;
import com.example.usermanagementservice.service.UserService;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ToString
@RestController
@RequestMapping("/crud")
@AllArgsConstructor
public class UserControllerImpl implements UserController {
    private final UserService userService ;

    @Override
    @PostMapping("/AddUser")
    public ResponseEntity<Void>addUser(@RequestParam String name ,@RequestParam String surname , @RequestParam int age) {

        User user = new User(name,surname,age);
        try {
            userService.addUser(user);
        } catch (Exception rte ) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Override
    @PutMapping("/Update")
    public ResponseEntity<Void> updateUser (@RequestBody User user) {
        try {
            userService.updateUser(user);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(HttpStatus.OK);


    }

    @Override
    @GetMapping("/findUserById/{id}")
    public ResponseEntity<Optional<User>> findUserById( @PathVariable Integer id)  {
        Optional< User > user;
        try {
            user = userService.getUserById(id);
        }
        catch (UserNotFoundException unfe){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        user.toString() ;
        System.out.println(user.toString());

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Override
    @GetMapping("/findUserByName/{name}")

    public ResponseEntity<List<User>> findUserByName(@PathVariable String name) {

        List<User> users =  userService.getUserByName(name);

        if(  users == null  ||  users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @Override
    @GetMapping("/findAll")

    public ResponseEntity<List<User>> findAll() {
        List <User> users;
        try {
            users = userService.getAllUsers();
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        if (users.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(users,HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/Delete/{id}")

    public ResponseEntity<Optional<String>> deleteUser(@PathVariable int id)  {

        try {

            userService.deleteUser(id);

        } catch (UserNotFoundException unfe){

            Optional <String> msg =Optional.of( "Id not Found");
            return new ResponseEntity<>(msg,HttpStatus.NOT_FOUND);

        }

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Override
    @PostMapping("/AddUsers/{X}")
    public ResponseEntity<Void>addXRandomUsers( @PathVariable int X) {

        try {
            userService.addXRandomUsers(X);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Override
    @DeleteMapping("/Delete")
    public ResponseEntity<Optional<String>> deleteAllUsers() {
        try {

            userService.deleteAllUsers();

        } catch (Exception e) {

            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Override
    @GetMapping("/findUserTypeById/{id}")
     public ResponseEntity <List<String>> findUserOccupationAndTypeById(@PathVariable Integer id) {

        GetUserFactory getUserFactory = new GetUserFactory() ;
        Optional<User> user ;
        try {
           user = userService.getUserById(id);

        } catch (UserNotFoundException idfe ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

          user = Optional.ofNullable(getUserFactory.getUser(user.get().getAge()));





//        #1 Sol
//         AdultUser adultUser = new AdultUser() ;
//         OldUser   oldUser   = new OldUser() ;
//         YoungUser youngUser = new YoungUser() ;
//
//        if (    usr.getClass().equals(  adultUser.getClass()  ) ) {
//            adultUser = (AdultUser) usr;
//            return new ResponseEntity<>( List.of(adultUser.getUserType(),adultUser.getUserOccupation()),HttpStatus.OK);
//        }
//        else if ( usr.getClass().equals(youngUser.getClass())) {
//            youngUser = (YoungUser) usr;
//            return new ResponseEntity<>( List.of(youngUser.getUserType(),youngUser.getUserOccupation()),HttpStatus.OK);
//        }
//        else {
//            oldUser = (OldUser) usr;
//            return new ResponseEntity<>( List.of(oldUser.getUserType(),oldUser.getUserOccupation()),HttpStatus.OK);
//        }

        return new ResponseEntity<>( List.of(user.get().gEtType(),user.get().gEtOccupation()),HttpStatus.OK);
    }

    @Override
    @GetMapping("/userinfo")
    public ResponseEntity <String> getUserInfo() {
        UserInfo userInfo = UserInfo.getUserInfo() ;
        try {
            userInfo.getMessage() ;

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(userInfo.getMessage(),HttpStatus.OK);
    }


    @Override
    @GetMapping("/findByNameAndAge")
    public ResponseEntity<List<User>> findByNameAndAge( @RequestParam  String name , @RequestParam int age) {
        List <User>  allUsers = userService.getAllUsers() ;

        List<User> users = allUsers.stream()
                .filter( user -> user.getAge() > age  &&  user.greaterThanGivenName(name)    )
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList());


             return new ResponseEntity<List<User>>(users,HttpStatus.OK) ;
    }

    @Override
    @GetMapping("/UsersByMonth")
    public ResponseEntity< List< UserInfoMonthly > > returnUsersByMonth () throws CloneNotSupportedException {
        UserInfoMonthly userInfoMonthly = new UserInfoMonthly () ;
        userInfoMonthly.loadDefaultUsers();

        UserInfoMonthly userInfoMonthly1 = userInfoMonthly.clone() ;
        userInfoMonthly1.setMonth("February");

        UserInfoMonthly userInfoMonthly2 = userInfoMonthly.clone() ;
        userInfoMonthly2.addUserToList("User_6");
        userInfoMonthly2.setMonth("March");

        UserInfoMonthly userInfoMonthly3 = userInfoMonthly.clone() ;
        userInfoMonthly3.addUserToList("User_7");
        userInfoMonthly3.setMonth("April");

        UserInfoMonthly userInfoMonthly4 = userInfoMonthly.clone() ;
        userInfoMonthly4.addUserToList("User_10");
        userInfoMonthly4.addUserToList("User_11");
        userInfoMonthly4.setMonth("May");

        UserInfoMonthly userInfoMonthly5 = userInfoMonthly.clone() ;
        userInfoMonthly5.addUserToList("User_32");
        userInfoMonthly5.setMonth("June");

        List<UserInfoMonthly> usrs = new ArrayList<>() ;
        usrs.add(userInfoMonthly);
        usrs.add(userInfoMonthly1);
        usrs.add(userInfoMonthly2);
        usrs.add(userInfoMonthly3);
        usrs.add(userInfoMonthly4);
        usrs.add(userInfoMonthly5);

        return  new ResponseEntity<>(usrs,HttpStatus.OK);
    }
}




