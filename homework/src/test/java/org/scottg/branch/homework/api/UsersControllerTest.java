package org.scottg.branch.homework.api;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.scottg.branch.github.model.User;
import org.scottg.branch.homework.delegates.UserDelegate;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UsersControllerTest {

    private UsersController testSubject;

    @Mock
    private UserDelegate userDelegate;

    @BeforeEach
    public void init(){
        testSubject = new UsersController(userDelegate);
    }

    @Test
    public void testHappyPath(){
        final String userName = "octocat";
        final User user = new User();
        user.setUserName(userName);

        when(userDelegate.getUserByName(userName)).thenReturn(user);
        assertEquals(ResponseEntity.ok(user), testSubject.getUserByName(userName));

        verify(userDelegate, times(1)).getUserByName(userName);
    }


    @Test
    public void testValidationError(){
        final String userName = "octocat";
        when(userDelegate.getUserByName(userName)).thenThrow(IllegalArgumentException.class);

        assertThrows(IllegalArgumentException.class, () -> {
            testSubject.getUserByName(userName);
        });

        verify(userDelegate, times(1)).getUserByName(userName);
    }

    // ..Running out of time.. moving on..
}