package com.example.usermanagementservice.intergrationTest;

import com.example.usermanagementservice.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;

public interface TestH2Repository extends JpaRepository<User,Integer> {
   User  findByName(String name);
}