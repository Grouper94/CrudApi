package com.example.usermanagementservice.model;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Table(name = "userdb")
@Entity
//@Inheritance(strategy=InheritanceType.SINGLE_TABLE)

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Schema
public  class User {

    public User ( String name, String surname, int age  ) {

        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Parameter(required = false, hidden = true)
    private  Integer id;

    @Size(min = 0, max = 100)
    @Column(name = "firstname")
    private String name;

    @Size(min = 0, max = 100)
    private String surname;

    private int age;


     public  String gEtOccupation() {return "No User Occupation yet";}
     public  String gEtType()  {return "No User Type yet " ;}

    public boolean greaterThanGivenName (String name) {
        if( this.name.compareTo(name)  > 0  ) {
            return true ;
        }
        return false ;

    }



}
