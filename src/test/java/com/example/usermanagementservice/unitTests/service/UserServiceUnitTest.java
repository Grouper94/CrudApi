package com.example.usermanagementservice.unitTests.service;

import com.example.usermanagementservice.exceptions.UserNotFoundException;
import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repsitory.UserRepository;
import com.example.usermanagementservice.service.UserServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

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

    static final String NAME_1 = "John" ;

    static final String SUR_NAME_1 = "Lwe" ;

    static final String NOT_USED = "Jim" ;

    static final String NAME_2 = "George" ;

    static final String SUR_NAME_2 = "Georgiou" ;


    @Test
    void addUser_thenReturnUser()  {

        final User ACTUAL = new User(1, NAME_1, SUR_NAME_1, 23);

        when(userRepository.save(Mockito.any(User.class))).thenReturn(ACTUAL);
        User expected = service.addUser(ACTUAL);

        assertThat(expected).isNotNull();
        assertThat(expected.getId()).isEqualTo(ACTUAL.getId());
    }

    @Test
    void updateUser_whenIdExists_thenReturnVoid()  {

       final User ACTUAL = new User(1, NAME_1, SUR_NAME_1, 23);

        when(userRepository.findById(ACTUAL.getId())).thenReturn(Optional.of(ACTUAL));

        when(userRepository.save(ACTUAL)).thenReturn(ACTUAL);

        service.updateUser(ACTUAL);

        assertThat(ACTUAL).isNotNull();
        assertThat(ACTUAL.getAge()).isEqualTo(23);
    }

    @Test
    void getUserById_whenUserExists_thenReturnUser()  {

        final User ACTUAL = new User(2, NAME_2, SUR_NAME_2, 54);

        when(userRepository.findById(ACTUAL.getId())).thenReturn(Optional.of(ACTUAL));

        Optional<User> expected = service.getUserById(ACTUAL.getId());

        assertThat(expected).isNotNull();
        assertThat(Optional.of(ACTUAL)).isEqualTo(expected);
    }

    @Test
    void getUserByName_whenUsersExist_thenReturnListOfUsers()  {
        final User USER_1 = new User(1, NAME_2, SUR_NAME_1, 54);
        final User USER_2 = new User(2, NAME_2, SUR_NAME_2, 64);

        when( userRepository.findByName(NAME_2) ).thenReturn( List.of ( USER_1,USER_2) );

        List<User> users = service.getUserByName(NAME_2);

        assertThat(users).isNotNull();
        assertThat(users.get(0).getName()).isEqualTo(users.get(1).getName());
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void getAllUsers_whenUsersExist_thenReturnListOfAllUsers()  {
        final  User USER_1 = new User(1, NAME_1, SUR_NAME_1, 23);
        final  User USER_2 = new User(2, NAME_2, SUR_NAME_2, 54);

        when( userRepository.findAll() ).thenReturn( List.of ( USER_1, USER_2 ) );
        List<User> users = service.getAllUsers();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void deleteUserById_whenUserExists_thenReturnVoid()  {

       final User ACTUAL = new User(1, NAME_1, SUR_NAME_1, 23) ;
       when(userRepository.existsById(ACTUAL.getId())).thenReturn(Boolean.TRUE) ;
        doNothing().when(userRepository).deleteById((ACTUAL).getId());

        assertAll(()->service.deleteUser(1));
    }

    @Test
    void getUserById_whenUserNotExists_thenReturnNull()  {

        when(userRepository.findById(1231)).thenReturn(null);

        assertThrows(NullPointerException.class ,()-> service.getUserById(1231));
    }

    @Test
    void getUserByName_whenUsersNotExist_thenReturnNull()  {

        when(userRepository.findByName(NOT_USED)).thenReturn(null);

        List<User> users = service.getUserByName(NOT_USED) ;

        assertNull(users );

    }

    @Test
    void updateUser_whenIdNotExists_thenReturnVoid()  {

        final User ACTUAL = new User(4554, NAME_2, SUR_NAME_2, 23);

        assertThrows(UserNotFoundException.class ,()-> service.updateUser(ACTUAL));
    }

    @Test
    void getAllUsers_whenUsersNotExist_thenReturnEmptyList()  {

        when( userRepository.findAll() ).thenReturn( List.of (  ) );

        final List<User> USERS = service.getAllUsers(); 

        assertTrue(USERS.isEmpty()) ;
    }

    @Test
    void deleteUserById_whenUserNotExists_thenReturnVoid()  {

        when(userRepository.existsById(123)).thenReturn(Boolean.FALSE) ;

        assertThrows(UserNotFoundException.class ,()-> service.deleteUser(123));
    }

    @Test
    void addNonValidUser_thenReturnUser()  {

        final User ACTUAL = new User(1, NAME_1, SUR_NAME_1, 23);

        when(userRepository.save(null)).thenThrow(RuntimeException.class );

        assertThrows(RuntimeException.class ,()-> service.addUser(null));


    }


}



