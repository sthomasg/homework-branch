package org.scottg.branch.homework.api;

import org.junit.jupiter.api.Test;
import org.scottg.branch.github.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.scottg.branch.homework.HomeworkApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.MOCK,
        classes = HomeworkApplication.class)
class UsersControllerIT {

    @Autowired
    private UsersController testSubject;

    @Test
    public void testGetUser_HappyPath(){
        ResponseEntity<User> responseEntity = testSubject.getUserByName("octocat");
        assertEquals(200, responseEntity.getStatusCode().value());

        User user = responseEntity.getBody();
        assertNotNull(user);
    }

    @Test
    public void test_NotFound(){
        assertThrowsExactly(HttpClientErrorException.NotFound.class, () -> {
            ResponseEntity<User> responseEntity = testSubject.getUserByName("asdfsdyfoisfnasdfy[0dfsijfosidfy[asdufas0dfas");
        });
    }

    @Test
    public void test_invalidRequest(){
        assertThrowsExactly(jakarta.validation.ConstraintViolationException.class, () -> {
            ResponseEntity<User> responseEntity = testSubject.getUserByName("");
        });
    }





}