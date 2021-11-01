package com.CultureCup.DTO.User.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateData {

    private Long userId;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private String password;
}
