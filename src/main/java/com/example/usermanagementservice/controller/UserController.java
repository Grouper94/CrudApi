package com.example.usermanagementservice.controller;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.model.UserInfoMonthly;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public interface UserController {

     @Operation(
             tags = {"Note"},
             summary = "Creates  a user by giving user's attributes")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "User has been Added Successfully"),
             @ApiResponse(responseCode = "404", description = "User has Not Been  Created")
     })
     ResponseEntity<Void> addUser (String name,String surname,int age) ;

     @Operation(tags = {"Note"},
             summary = "Update an EXISTING user's data")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "User has been UPDatAdded Successfully"),
             @ApiResponse(responseCode = "404", description = "Id Does Not exist")
     })
     ResponseEntity<Void> updateUser ( User user) throws Exception;


     @Operation(
             tags = {"Note"},
             summary = "Creates  x  users ",
           parameters =  @Parameter(name = "X",description = "How many Users Do You Want To Create??",example="4"))

     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Users has been Added Successfully"),
             @ApiResponse(responseCode = "404", description = "User has Not Been  Created")
     })
     ResponseEntity<Void> addXRandomUsers(int X) throws Exception;

     @Operation(tags = {"Note"},
             summary = "Returns the user with the specific id",
              parameters = {@Parameter(name = "id",description = "id of User to be searched",example="1")})

     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found the User"),
             @ApiResponse(responseCode = "404", description = "User Not Found")
     })
     ResponseEntity<Optional<User>> findUserById(Integer id);

     @Operation(
             tags = {"Note"},
             summary = "Returns the user OR users with the specific name",
             parameters = {@Parameter(name = "name",description = "name of User/s to be searched",example="George")})
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found the User"),
             @ApiResponse(responseCode = "404", description = "User/s Not Found")
     })
     ResponseEntity<List<User>> findUserByName( String name) ;

     @Operation(tags = {"Note"},
             summary = "Returns All The Users")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found All The Users"),
             @ApiResponse(responseCode = "404",description = "No Users Found")
     })
     ResponseEntity<List<User>> findAll();

     @Operation(tags = {"Note"},
             summary = "Deletes a user with the specific id",
             parameters = {@Parameter(name = "id", description = "id of User to be deleted", example = "1")})
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "User has been Deleted Successfully"),
             @ApiResponse(responseCode = "404", description = "Id Not Found")
     })
     ResponseEntity<Optional<String>> deleteUser(int id) ;

     @Operation(tags = {"Note"},
             summary = "Deletes All Users")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200", description = "Users has been Deleted Successfully"),
             @ApiResponse(responseCode = "404", description = "Users has Not Been  deleted")
     })
     ResponseEntity<Optional<String>> deleteAllUsers();

     @Operation(tags = {"Note"},
             summary = "Returns  user's type and occupation with the specific id",
             parameters = {@Parameter(name = "id",description = "id of User to be searched",example="1")})

     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found the User"),
             @ApiResponse(responseCode = "404", description = "User Not Found")
     })
     ResponseEntity <List<String>> findUserOccupationAndTypeById(Integer id);


     @Operation(tags = {"Note"},
             summary = "Returns  User's Info")

     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found"),
             @ApiResponse(responseCode = "404", description = "Error")
     })
     ResponseEntity <String> getUserInfo() ;

     @Operation(tags = {"Note"},
             summary = "Returns The Users With Name and Age Greater Than The Provided")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Found  The Users"),
             @ApiResponse(responseCode = "404",description = "No Users Found")
     })
     ResponseEntity<List<User>> findByNameAndAge( String name , int age);

     @Operation(
             tags = {"Note"},
             summary = "Return all Users for each month")
     @ApiResponses(value = {
             @ApiResponse(responseCode = "200",description = "Users has been Printed Successfully"),
             @ApiResponse(responseCode = "404", description = "User has Not Been  Created")
     })
     ResponseEntity<List< UserInfoMonthly >> returnUsersByMonth () throws CloneNotSupportedException;

}
