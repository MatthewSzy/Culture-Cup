package com.CultureCup.Services.User.CastToListClass;

import com.CultureCup.DTO.User.Response.UserDataListItem;
import com.CultureCup.Entities.Role;
import com.CultureCup.Entities.User;

import java.util.ArrayList;
import java.util.List;

public class CastToListClass {

    public static List<UserDataListItem> castToListForAdmin(List<User> users) {
        List<UserDataListItem> usersResponse = new ArrayList<>();
        for(User user: users) {
            List<String> roles = new ArrayList<>();

            for(Role role : user.getRoles()) roles.add(role.getName().toString());
            usersResponse.add(new UserDataListItem(user.getUserId(), user.getUsername(), user.getEmail(), roles));
        }

        return usersResponse;
    }
}
