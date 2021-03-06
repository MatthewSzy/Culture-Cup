package com.CultureCup.DTO.User.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserData {

    private Long userId;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    private Collection<? extends GrantedAuthority> roles;
}
