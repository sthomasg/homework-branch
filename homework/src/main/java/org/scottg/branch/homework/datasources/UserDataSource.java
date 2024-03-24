package org.scottg.branch.homework.datasources;

import org.scottg.branch.github.model.User;

public interface UserDataSource {

    User retrieveUserByUserName(String userName);

}
