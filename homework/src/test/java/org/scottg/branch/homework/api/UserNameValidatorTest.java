package org.scottg.branch.homework.api;

import org.junit.jupiter.api.Test;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class UserNameValidatorTest {

    @Test
    public void testNoNulls(){
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            UserNameValidator.validateUserName(null);
        });
    }

    @Test
    public void testNoEmpty(){
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            UserNameValidator.validateUserName("");
        });
    }

    @Test
    public void testNoWhiteSpace(){
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            UserNameValidator.validateUserName("");
        });
    }

    @Test
    public void testTooShort(){
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            UserNameValidator.validateUserName("ab");
        });
    }

    @Test
    public void testTooLong(){
        assertThrowsExactly(IllegalArgumentException.class, () -> {
            char[] ary = new char[201];
            Arrays.fill(ary, 'a');
            String val = String.copyValueOf(ary);
            UserNameValidator.validateUserName(val);
        });
    }


}