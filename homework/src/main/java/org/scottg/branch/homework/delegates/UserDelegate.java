package org.scottg.branch.homework.delegates;

import org.scottg.branch.github.model.User;

public interface UserDelegate {

    User getUserByName(final String userName);
}
