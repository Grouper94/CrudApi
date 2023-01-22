package com.example.usermanagementservice.intergrationTest;

import com.example.usermanagementservice.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
class UserControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    ObjectMapper mapper;

    @Autowired
    private TestH2Repository h2Repository;

    HttpHeaders headers = new HttpHeaders();

    TestRestTemplate restTemplate = new TestRestTemplate();

    static final String name = "NewName" ;

    static final String name1 = "Eli" ;
    static final String surName = "NewSurname" ;

    static final String surName1 = "Gotier" ;

    static final String  uriGetIdTrue        =  "/crud/findUserById/1" ;
    static final String  uriGetNameTrue      =  "/crud/findUserByName/Rick" ;
    static final String  uriPostUserTrue     =  "/crud/AddUser?name="+name1+"&&surname="+surName1+"&age=41" ;
    static final String  uriGetAll           =  "/crud/findAll" ;
    static final String  uriPutUser          =  "/crud/Update" ;
    static final String  uriDeleteIdTrue     =  "/crud/Delete/4" ;
    static final String  uriGetIdFalse       =  "/crud/findUserById/10" ;
    static final String  uriGetNameFalse     =  "/crud/findUserByName/Elias" ;
    static final String  uriPostUserFalse    =  "/crud/AddUser?name=&&surname=Pierson&age=" ;

    static final String  uriDeleteIdFalse    =  "/crud/Delete/544" ;

    private static final String LOCALHOST_PREFIX = "http://localhost:";

    @Test
    void findAll_whenUsersExists_thenReturnValidResponse() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriGetAll),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43},"
                +"{\"id\":2,\"name\":\"Rick\",\"surname\":\"Robinson\",\"age\":73},"
                +"{\"id\":3,\"name\":\"George\",\"surname\":\"Palson\",\"age\":22}]" ;

        System.out.println(response2.getBody());
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    void findUserById_whenUserExists_thenReturnValidResponseAndUser() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String expected = "{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43}";

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriGetIdTrue),
                HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    void findUsersByName_whenUserExists_thenReturnValidResponseAndListOfUsers() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String expected = "[{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43},"
                         +"{\"id\":2,\"name\":\"Rick\",\"surname\":\"Robinson\",\"age\":73}]";

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriGetNameTrue),
                HttpMethod.GET, entity, String.class);
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    void  addUser_thenReturnValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriPostUserTrue),
                HttpMethod.POST, entity, String.class);

        assertTrue(response2.getStatusCode().is2xxSuccessful()) ;

        User actual = h2Repository.findByName(name1);

        assertEquals(name1,actual.getName());

        assertEquals(surName1,actual.getSurname());

        assertEquals(41,actual.getAge());

        h2Repository.deleteById(actual.getId());

    }

    @Test
    void updateUser_whenGivenIdExists_thenReturnValidResponse()  {

        HttpEntity<User> entity = new HttpEntity<User>(new User(1,name,surName,56));

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriPutUser),
                HttpMethod.PUT, entity, String.class);

        User user = h2Repository.findByName(name) ;

        assertEquals(user.getName(),name);

        assertEquals(user.getSurname(),surName);

        assertEquals(user.getAge(),56);

        assertTrue(response2.getStatusCode().is2xxSuccessful()) ;
    }

    @Test
    @Sql({"/RandomUser.sql"})
    void deleteUserById_whenUserExists_thenReturnValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriDeleteIdTrue),
                HttpMethod.DELETE, entity, String.class);

        assertTrue ( response2.getStatusCode().is2xxSuccessful() );
        assertFalse(h2Repository.existsById(4));
    }

    @Test
    void findUserById_whenUserNotExists_thenReturnNonValidResponseAndUser()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriGetIdFalse),
                HttpMethod.GET, entity, String.class);

        assertTrue( response2.getStatusCode().is4xxClientError() );
    }

    @Test
    void  addUser_thenReturnNonValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriPostUserFalse),
                HttpMethod.POST, entity, String.class);
        assertTrue ( response2.getStatusCode().is4xxClientError() );
    }

    @Test
    void findUsersByName_whenUserNotExists_thenReturnNonValidResponse()  {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(uriGetNameFalse),
                HttpMethod.GET, entity, String.class);

        assertTrue ( response2.getStatusCode().is4xxClientError() );

    }

    @Test
    void updateUser_whenGivenIdNotExists_thenReturnValidResponse()  {

        HttpEntity<User> entity = new HttpEntity<>(new User(16767, name, surName, 56));

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort( uriPutUser  ),
                HttpMethod.PUT, entity, String.class);

        assertTrue( response2.getStatusCode().is4xxClientError() );
    }

    @Test
    void deleteUserById_whenUserNotExists_thenReturnNonValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort( uriDeleteIdFalse ),
                HttpMethod.DELETE, entity, String.class);

        assertTrue ( response2.getStatusCode().is4xxClientError() );
        assertFalse (h2Repository.existsById(544) );
    }
    String createURLWithPort(String uri) {
        return LOCALHOST_PREFIX + port + uri;
    }
}