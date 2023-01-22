package com.example.usermanagementservice.unitTests.service;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repsitory.UserRepository;
import com.example.usermanagementservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.webjars.NotFoundException;

import java.io.NotActiveException;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceUnitTest {
    @InjectMocks
    private UserServiceImpl service;
    @Mock
    private UserRepository userRepository;

    static final String name1 = "John" ;

    static final String surName1 = "Lwe" ;

    static final String notUsed = "Jim" ;

    static final String name2 = "George" ;

    static final String surName2 = "Georgiou" ;


    @Test
    void addUser_thenReturnUser()  {

        User actual = new User(1, name1, surName1, 23);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(actual);
        User expected = service.addUser(actual);

        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isEqualTo(actual.getId());
    }

    @Test
    void updateUser_whenIdExists_thenReturnVoid()  {

        User actual = new User(1, name1, surName1, 23);

        actual.setAge(25);

        when(userRepository.findById(actual.getId())).thenReturn(Optional.of(actual));

        when(userRepository.save(actual)).thenReturn(actual);

        service.updateUser(actual);

        assertThat(actual).isNotNull();
        assertThat(actual.getAge()).isEqualTo(25);
    }

    @Test
    void getUserById_whenUserExists_thenReturnUser()  {

        User actual = new User(2, name2, surName2, 54);

        when(userRepository.findById(actual.getId())).thenReturn(Optional.of(actual));

        Optional<User> expected = service.getUserById(actual.getId());

        assertThat(expected).isNotNull();
        assertThat(Optional.of(actual)).isEqualTo(expected);
    }

    @Test
    void getUserByName_whenUsersExist_thenReturnListOfUsers()  {
        User user1 = new User(1, name2, surName1, 54);
        User user2 = new User(2, name2, surName2, 64);

        when( userRepository.findByName( name2 ) ).thenReturn( List.of ( user1,user2) );

        List<User> users = service.getUserByName(name2);

        assertThat(users).isNotNull();
        assertThat(users.get(0).getName()).isEqualTo(users.get(1).getName());
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void getAllUsers_whenUsersExist_thenReturnListOfAllUsers()  {
        User user1 = new User(1, name1, surName1, 23);
        User user2 = new User(2, name2, surName2, 54);

        when( userRepository.findAll() ).thenReturn( List.of ( user1, user2 ) );
        List<User> users = service.getAllUsers();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void deleteUserById_whenUserExists_thenReturnVoid()  {
        User actual = new User(1, name1, surName1, 23);

        doNothing().when(userRepository).deleteById((actual).getId());
        assertAll(()->service.deleteUser(1));
    }

    @Test
    void getUserById_whenUserNotExists_thenReturnNull()  {

        when(userRepository.findById(1231)).thenReturn(null);

        assertThrows(NullPointerException.class ,()-> service.getUserById(1231));
    }

    @Test
    void getUserByName_whenUsersNotExist_thenReturnNull()  {

        when(userRepository.findByName( notUsed )).thenReturn(null);

        List<User> users = service.getUserByName(notUsed) ;

        assertTrue(users == null) ;

    }

    @Test
    void updateUser_whenIdNotExists_thenReturnVoid()  {

        User actual = new User(4554, name2, surName2, 23);
        actual.setAge(25);
        assertThrows(RuntimeException.class ,()-> service.updateUser(actual));
    }
}



