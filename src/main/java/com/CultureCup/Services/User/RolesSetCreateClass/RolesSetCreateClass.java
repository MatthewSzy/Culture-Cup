package com.CultureCup.Services.User.RolesSetCreateClass;

import com.CultureCup.Entities.Enum.RoleEnum;
import com.CultureCup.Entities.Role;
import com.CultureCup.Exceptions.IncorrectDataInput;
import com.CultureCup.Repositories.RoleRepository;
import com.CultureCup.Services._DatabaseAnalysis.RoleAnalysis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

public class RolesSetCreateClass {

    static Logger logger = LoggerFactory.getLogger(RolesSetCreateClass.class);

    public static Set<Role> newUserRoles(RoleRepository roleRepository, Set<String> roles) {

        Set<Role> rolesSet = new HashSet<>();
        if (roles == null) {
            logger.error("No roles in Set");
            throw new IncorrectDataInput("Role dla użytkownika nie zostały wybrane!");
        }

        roles.forEach(role -> {
            if ("admin".equals(role)) rolesSet.add(RoleAnalysis.findRoleByName(roleRepository, RoleEnum.ROLE_ADMIN));
            else if ("user".equals(role)) rolesSet.add(RoleAnalysis.findRoleByName(roleRepository, RoleEnum.ROLE_USER));
        });

        return rolesSet;
    }

    public static Set<Role> rolesSetWithAdmin(RoleRepository roleRepository) {

        Set<Role> newRolesSet = new HashSet<>();
        newRolesSet.add(RoleAnalysis.findRoleByName(roleRepository, RoleEnum.ROLE_USER));
        newRolesSet.add(RoleAnalysis.findRoleByName(roleRepository, RoleEnum.ROLE_ADMIN));

        return newRolesSet;
    }

    public static Set<Role> rolesSetWithoutAdmin(RoleRepository roleRepository) {

        Set<Role> newRolesSet = new HashSet<>();
        newRolesSet.add(RoleAnalysis.findRoleByName(roleRepository, RoleEnum.ROLE_USER));

        return newRolesSet;
    }
}
