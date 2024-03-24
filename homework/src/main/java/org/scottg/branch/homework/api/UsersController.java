package org.scottg.branch.homework.api;

import org.scottg.branch.github.api.V1Api;
import org.scottg.branch.github.model.User;
import org.scottg.branch.homework.delegates.UserDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class UsersController implements V1Api {

    public UsersController(@Autowired UserDelegate userDelegate) {
        this.userDelegate = userDelegate;
    }

    private final UserDelegate userDelegate;

    @Override
    public ResponseEntity<User> getUserByName(final String userName) {
        return ResponseEntity.ok(userDelegate.getUserByName(userName));
    }
}
