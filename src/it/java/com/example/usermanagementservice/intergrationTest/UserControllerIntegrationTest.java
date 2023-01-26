package com.example.usermanagementservice.intergrationTest;

import com.example.usermanagementservice.model.User;
import com.example.usermanagementservice.repsitory.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.minidev.json.JSONArray;
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
import java.util.zip.DeflaterOutputStream;

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

    static final String NAME = "NewName" ;

    static final String NAME_1 = "Eli" ;
    static final String SUR_NAME = "NewSurname" ;

    static final String SUR_NAME_1 = "Gotier" ;

    static final String URI_GET_ID_TRUE =  "/crud/findUserById/1" ;
    static final String URI_GET_NAME_TRUE =  "/crud/findUserByName/Rick" ;
    static final String URI_POST_USER_TRUE =  "/crud/AddUser?name="+ NAME_1 +"&&surname="+ SUR_NAME_1 +"&age=41" ;
    static final String URI_GET_ALL =  "/crud/findAll" ;
    static final String URI_PUT_USER =  "/crud/Update" ;
    static final String URI_DELETE_ID_TRUE =  "/crud/Delete/4" ;
    static final String URI_GET_ID_FALSE =  "/crud/findUserById/10" ;
    static final String URI_GET_NAME_FALSE =  "/crud/findUserByName/Elias" ;
    static final String URI_POST_USER_FALSE =  "/crud/AddUser?name=&&surname=Pierson&age=" ;
    static final  String URI_DELETE_ID_FALSE =  "/crud/Delete/544" ;
    static final String URI_GET_TYPE_BY_ID_TRUE =  "/crud/findUserTypeById/1" ;

    private static final String LOCALHOST_PREFIX = "http://localhost:";
    @Autowired
    private UserRepository userRepository;

    @Test
    void findAll_whenUsersExists_thenReturnValidResponse() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_GET_ALL),
                HttpMethod.GET, entity, String.class);

        String expected = "[{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43},"
                +"{\"id\":2,\"name\":\"Rick\",\"surname\":\"Robinson\",\"age\":73},"
                +"{\"id\":3,\"name\":\"George\",\"surname\":\"Palson\",\"age\":22}]" ;

        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    void findUserById_whenUserExists_thenReturnValidResponseAndUser() throws Exception {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String expected = "{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43}";

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_GET_ID_TRUE),
                HttpMethod.GET, entity, String.class);

        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    void findUsersByName_whenUserExists_thenReturnValidResponseAndListOfUsers() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        String expected = "[{\"id\":1,\"name\":\"Rick\",\"surname\":\"Morty\",\"age\":43},"
                         +"{\"id\":2,\"name\":\"Rick\",\"surname\":\"Robinson\",\"age\":73}]";

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_GET_NAME_TRUE),
                HttpMethod.GET, entity, String.class);
        JSONAssert.assertEquals(expected, response2.getBody(), false);
    }

    @Test
    void  addUser_thenReturnValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_POST_USER_TRUE),
                HttpMethod.POST, entity, String.class);

        assertTrue(response2.getStatusCode().is2xxSuccessful()) ;

        final User ACTUAL = h2Repository.findByName(NAME_1);

        assertEquals(NAME_1,ACTUAL.getName());

        assertEquals(SUR_NAME_1,ACTUAL.getSurname());

        assertEquals(41,ACTUAL.getAge());

        h2Repository.deleteById(ACTUAL.getId());

    }

    @Test
    void updateUser_whenGivenIdExists_thenReturnValidResponse()  {

        HttpEntity<User> entity = new HttpEntity<>(new User(1, NAME, SUR_NAME,56));

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_PUT_USER),
                HttpMethod.PUT, entity, String.class);

        final User USER = h2Repository.findByName(NAME) ;

        assertEquals(USER.getName(), NAME);

        assertEquals(USER.getSurname(), SUR_NAME);

        assertEquals(USER.getAge(),56);

        assertTrue(response2.getStatusCode().is2xxSuccessful()) ;
    }

    @Test
    @Sql({"/RandomUser.sql"})
    void deleteUserById_whenUserExists_thenReturnValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_DELETE_ID_TRUE),
                HttpMethod.DELETE, entity, String.class);

        assertTrue ( response2.getStatusCode().is2xxSuccessful() );
        assertFalse(h2Repository.existsById(4));
    }

    @Test
    void findUserById_whenUserNotExists_thenReturnNonValidResponseAndUser()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_GET_ID_FALSE),
                HttpMethod.GET, entity, String.class);

        assertTrue( response2.getStatusCode().is4xxClientError() );
    }

    @Test
    void  addUser_thenReturnNonValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_POST_USER_FALSE),
                HttpMethod.POST, entity, String.class);
        assertTrue ( response2.getStatusCode().is4xxClientError() );
    }

    @Test
    void findUsersByName_whenUserNotExists_thenReturnNonValidResponse()  {
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_GET_NAME_FALSE),
                HttpMethod.GET, entity, String.class);

        assertTrue ( response2.getStatusCode().is4xxClientError() );

    }

    @Test
    void updateUser_whenGivenIdNotExists_thenReturnValidResponse()  {

        HttpEntity<User> entity = new HttpEntity<>(new User(16767, NAME, SUR_NAME, 56));

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_PUT_USER),
                HttpMethod.PUT, entity, String.class);

        assertTrue( response2.getStatusCode().is4xxClientError() );
    }

    @Test
    void deleteUserById_whenUserNotExists_thenReturnNonValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_DELETE_ID_FALSE),
                HttpMethod.DELETE, entity, String.class);
        assertTrue ( response2.getStatusCode().is4xxClientError() );
        assertFalse (h2Repository.existsById(544) );
    }

    @Test
    void  addNonValidUser_thenReturnNonValidResponse()  {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_POST_USER_TRUE),
                HttpMethod.POST, entity, String.class);

        assertTrue(response2.getStatusCode().is2xxSuccessful()) ;

        final User ACTUAL = h2Repository.findByName(NAME_1);

        assertEquals(NAME_1,ACTUAL.getName());

        assertEquals(SUR_NAME_1,ACTUAL.getSurname());

        assertEquals(41,ACTUAL.getAge());

        h2Repository.deleteById(ACTUAL.getId());

    }

    @Test
    void  findUserOccupationAndTypeById_thenReturnValidResponse() throws JSONException {

        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        List<String> typeList = new ArrayList<>() ;
        typeList.add( "userType : Adult");
        typeList.add( "userOccupation : Work");

        String expected =  JSONArray.toJSONString(typeList);

        ResponseEntity<String> response2 = restTemplate.exchange(
                createURLWithPort(URI_GET_TYPE_BY_ID_TRUE),
                HttpMethod.GET, entity, String.class);

        System.out.println(userRepository.findAll());

        JSONAssert.assertEquals(expected, response2.getBody(), false);

    }

    String createURLWithPort(String uri) {
        return LOCALHOST_PREFIX + port + uri;
    }
}