package com.CultureCup.Repositories;

import com.CultureCup.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserByUserId(Long userId);

    Optional<User> findUserByUsername(String username);

    Boolean existsUserByUserId(Long userId);

    Boolean existsUserByUsername(String username);

    Boolean existsUserByEmail(String email);
}
