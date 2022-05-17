package io.github.ifariskh.donationsystem;

import org.junit.Test;

import static org.junit.Assert.*;

import java.util.regex.Pattern;


public class UnitTest {


    private boolean validateEmail(String email) {
        if (email.isEmpty()) {
            return false;
        } else return Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
                Pattern.CASE_INSENSITIVE).matcher(email).matches();
    }

    @Test
    public void validateEmail() {

        boolean flag = validateEmail("f4ris1999@gmail.com");

        assertEquals(true, flag);
    }

    private boolean validateId(String id) {
        if (id.isEmpty()) {
            return false;
        } else return Pattern.compile("^[0-9]{10}+$").matcher(id).matches();
    }

    @Test
    public void validateID() {

        boolean flag = validateId("1111111111");

        assertEquals(true, flag);
    }

}