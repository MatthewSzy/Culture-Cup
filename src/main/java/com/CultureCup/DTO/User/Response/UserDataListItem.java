package com.CultureCup.DTO.User.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDataListItem {

    private Long userId;

    private String username;

    private String email;

    private List<String> roles;
}
