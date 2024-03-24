package org.scottg.branch.homework.delegates;

import org.scottg.branch.github.model.User;
import org.scottg.branch.homework.api.UserNameValidator;
import org.scottg.branch.homework.datasources.UserDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserDelegateImpl implements UserDelegate{

    public UserDelegateImpl(@Autowired UserDataSource userDataSource) {
        this.dataSource = userDataSource;
    }

    private final UserDataSource dataSource;


    @Cacheable(value = "users", key = "#userName")
    public User getUserByName(final String userName){
        UserNameValidator.validateUserName(userName);
        return dataSource.retrieveUserByUserName(userName);
    }
}
