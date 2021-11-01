package com.CultureCup.Security.TokenClass;

import com.CultureCup.DTO.User.Response.UserData;
import com.CultureCup.Security.UserDetails.UserDetailsClass;
import io.jsonwebtoken.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtils {

    private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

    @Value("${bezkoder.app.jwtSecret}")
    private String jwtSecret;

    @Value("${bezkoder.app.jwtExpirationMs}")
    private int jwtExpirationMs;

    public String generateJwtToken(Authentication authentication) {

        UserDetailsClass userPrincipal = (UserDetailsClass) authentication.getPrincipal();

        return Jwts.builder().setSubject((userPrincipal.getUsername())).setIssuedAt(new Date())
                .setExpiration(new Date((new Date()).getTime() + jwtExpirationMs)).signWith(SignatureAlgorithm.HS512, jwtSecret)
                .claim("UserData", new UserData(userPrincipal.getUserId(), userPrincipal.getUsername(), userPrincipal.getFirstname(), userPrincipal.getLastname(), userPrincipal.getEmail(), userPrincipal.getAuthorities()))
                .compact();
    }

    public String getUserNameFromJwtToken(String token) { return Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(token).getBody().getSubject(); }

    public boolean validateJwtToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            logger.error("Invalid JWT signature");
        } catch (MalformedJwtException e) {
            logger.error("Invalid JWT token");
        } catch (ExpiredJwtException e) {
            logger.error("JWT token is expired");
        } catch (UnsupportedJwtException e) {
            logger.error("JWT token is unsupported");
        } catch (IllegalArgumentException e) {
            logger.error("JWT claims string is empty");
        }

        return false;
    }
}
