package com.CultureCup.Services.User.AuthenticationClass;

import com.CultureCup.DTO.User.Request.DeleteData;
import com.CultureCup.DTO.User.Request.LoginData;
import com.CultureCup.DTO.User.Response.UserTokenData;
import com.CultureCup.Security.TokenClass.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationClass {

    public static Authentication authenticateUser(AuthenticationManager authenticationManager, LoginData loginData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginData.getUsername(), loginData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return authentication;
    }

    public static void authenticateUser(AuthenticationManager authenticationManager, DeleteData deleteData) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(deleteData.getUsername(), deleteData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    public static UserTokenData getUserData(Authentication authentication, JwtUtils jwtUtils) {

        String token = jwtUtils.generateJwtToken(authentication);
        return UserTokenData.builder()
                .token(token)
                .type("Bearer")
                .build();
    }
}
