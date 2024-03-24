package org.scottg.branch.homework.api;

import org.apache.commons.lang3.StringUtils;

public class UserNameValidator {

    public final static void validateUserName(final String val) throws IllegalArgumentException {
        if (StringUtils.isAllEmpty(val) || val.length() < 3 || val.length() > 200) {
            throw new IllegalArgumentException("Input Validation failure on User Name Parameter.");
            // in real life I would probably have an object with more feedback...
        }
    }
}
