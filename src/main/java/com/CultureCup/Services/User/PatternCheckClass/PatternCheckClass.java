package com.CultureCup.Services.User.PatternCheckClass;

import com.CultureCup.Exceptions.IncorrectDataInput;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternCheckClass {

    static Logger logger = LoggerFactory.getLogger(PatternCheckClass.class);

    public static void usernamePattern(String username) {

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9._-]{3,20}$");
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            logger.error("Entered username is incorrect: \"" + username + "\"" );
            throw new IncorrectDataInput("Podana nazwa użytkownika jest nie prawidłowa!");
        }
    }

    public static void emailPattern(String email) {

        Pattern pattern = Pattern.compile("^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}$");
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            logger.error("Entered e-mail is incorrect: \"" + email + "\"");
            throw new IncorrectDataInput("Podany e-mail jest nie prawidłowy!");
        }
    }

    public static void passwordPattern(String password) {

        Pattern pattern = Pattern.compile("^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$");
        Matcher matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            logger.error("Entered password is incorrect: \"" + password + "\"");
            throw new IncorrectDataInput("Podane hasło jest nie prawidłowe!");
        }
    }
}
