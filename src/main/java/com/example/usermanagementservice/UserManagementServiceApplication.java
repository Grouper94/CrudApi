package com.example.usermanagementservice;
import com.example.usermanagementservice.service.UserService;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
//@EnableJpaRepositories("com.example.usermanagementservice.repsitory")
//@EntityScan("com.example.usermanagementservice.model")
@OpenAPIDefinition(
        info = @Info(title = "Api App", version = "1.0.0"),
        tags = {@Tag(name = "Note", description = "CRUD operations")})

@AllArgsConstructor
public class UserManagementServiceApplication implements CommandLineRunner {
public static void main(String[] args) { 

    SpringApplication.run(UserManagementServiceApplication.class, args);
}

private UserService userService;

    @Override
    public void run(String... args) throws Exception { }

}
