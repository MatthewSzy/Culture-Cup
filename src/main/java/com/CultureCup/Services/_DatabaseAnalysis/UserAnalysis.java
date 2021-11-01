package com.CultureCup.Services._DatabaseAnalysis;

import com.CultureCup.Entities.User;
import com.CultureCup.Exceptions.UserAlreadyExistsException;
import com.CultureCup.Exceptions.UserNotFoundException;
import com.CultureCup.Repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class UserAnalysis {

    static Logger logger = LoggerFactory.getLogger(UserAnalysis.class);

    public static User findUserByUserId(UserRepository userRepository, Long userId) {

        Optional<User> user = userRepository.findUserByUserId(userId);
        if (user.isEmpty()) {
            logger.error("User with the given ID was not found: " + "\"" + userId + "\"");
            throw new UserNotFoundException("Użytkownik o podanym ID nie został odnaleziony!");
        }

        return user.get();
    }

    public static void existsUserByUsername(UserRepository userRepository, String username) {

        if (userRepository.existsUserByUsername(username)) {
            logger.error("User with the given name already exists in Database: " + "\"" + username + "\"");
            throw new UserAlreadyExistsException("Użytkownik o podanej nazwie już istnieje!");
        }
    }

    public static void existsUserByEmail(UserRepository userRepository, String email) {

        if (userRepository.existsUserByEmail(email)) {
            logger.error("User with the given e-mail already exists in Database: " + "\"" + email + "\"");
            throw new UserAlreadyExistsException("Użytkownik o podanym e-mailu już istnieje!");
        }
    }
}
