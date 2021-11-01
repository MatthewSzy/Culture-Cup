package com.CultureCup.Repositories;

import com.CultureCup.Entities.Enum.RoleEnum;
import com.CultureCup.Entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findRoleByName(RoleEnum name);
}
