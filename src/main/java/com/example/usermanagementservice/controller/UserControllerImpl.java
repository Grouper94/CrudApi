package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.exceptions.IdNotFoundException;
import com.example.usermanagementservice.model.GetUserFactory;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.model.UserInfo;
import com.example.usermanagementservice.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


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
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_GATEWAY);
        }

        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Override
    @PutMapping("/Update")
    public ResponseEntity<Void> updateUser(@RequestBody  User user)  {
        try {
            userService.getUserById(user.getId());
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
        catch (IdNotFoundException infe){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @Override
    @GetMapping("/findUserByName/{name}")

    public ResponseEntity<List<User>> findUserByName(@PathVariable String name) {

        List<User> users =  userService.getUserByName(name);

        if(  users == null  ||  users.isEmpty())
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(users, HttpStatus.OK);



//        try {
//            users = userService.getUserByName(name);
//            System.out.println(users);
//
//        }
//         catch (Exception e) {
//             return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
////        if (users.isEmpty())
////            return new ResponseEntity<>(HttpStatus.NOT_FOUND);



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

        } catch (IdNotFoundException infe){

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
            Optional <String> msg =Optional.of( "Noooooo Waay");
            return new ResponseEntity<>(msg,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>( HttpStatus.OK);
    }

    @Override
    @GetMapping("/findUserTypeById/{id}")
     public ResponseEntity <List<String>> findUserOccupationAndTypeById(Integer id) {

        GetUserFactory getUserFactory = new GetUserFactory() ;
        Optional<User> user ;
        try {
           user = userService.getUserById(id);

        } catch (IdNotFoundException idfe ) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        User usr =  getUserFactory.getUser(user.get().getAge());

        return new ResponseEntity<>( List.of(usr.getUserType(), usr.getUserOccupation()),HttpStatus.OK);
    }

    @Override
    @GetMapping("/userinfo")
    public ResponseEntity <String> getUserInfo() {
        UserInfo userInfo = new UserInfo() ;
        try {
            userInfo.getMessage() ;

        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<String>(userInfo.getMessage(),HttpStatus.OK);




    }

}


