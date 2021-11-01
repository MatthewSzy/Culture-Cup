package com.CultureCup.Services._DatabaseAnalysis;

import com.CultureCup.Entities.Enum.RoleEnum;
import com.CultureCup.Entities.Role;
import com.CultureCup.Exceptions.RoleNotFoundException;
import com.CultureCup.Repositories.RoleRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class RoleAnalysis {

    static Logger logger = LoggerFactory.getLogger(RoleAnalysis.class);

    public static Role findRoleByName(RoleRepository roleRepository, RoleEnum roleEnum) {

        Optional<Role> role = roleRepository.findRoleByName(roleEnum);
        if (role.isEmpty()) {
            logger.error("User role not found in Database");
            throw new RoleNotFoundException("Rola nie została znaleziona!");
        }

        return role.get();
    }
}
