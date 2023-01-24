package com.example.usermanagementservice.model;


import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Table(name = "userdb")
@Entity
@Inheritance(strategy=InheritanceType.SINGLE_TABLE)

//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(name = "type", discriminatorType = DiscriminatorType.STRING)
//@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
//@DiscriminatorColumn(discriminatorType = DiscriminatorType.STRING,
     //   name = "TYPE")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema
public abstract   class User {

    public User ( String name, String surname, int age  ) {

        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

 //strategy = GenerationType.IDENTITY)
    @Parameter(required = false, hidden = true)
    private  Integer id;

    @Size(min = 0, max = 100)
    @Column(name = "firstname")
    private String name;

    @Size(min = 0, max = 100)
    private String surname;

    private int age;
    @Column(name = "diffages")
    protected String userType;

    @Column(name = "occupation")
    protected String userOccupation ;

    public abstract String getUserOccupation() ;
    public abstract String  getUserType() ;



}
