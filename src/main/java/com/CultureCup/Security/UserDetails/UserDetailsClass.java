package com.CultureCup.Security.UserDetails;

import com.CultureCup.Entities.User;
import net.minidev.json.annotate.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serial;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserDetailsClass implements UserDetails {

    @Serial
    private static final long serialVersionUID = 1L;

    private Long userId;

    private String username;

    private String firstname;

    private String lastname;

    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> roles;

    public UserDetailsClass(Long userId, String username, String firstname, String lastname, String email,
                            String password, Collection<? extends GrantedAuthority> roles) {

        this.userId = userId;
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.roles = roles;
    }

    public static UserDetailsClass build(User user) {

        List<GrantedAuthority> roles = user.getRoles().stream().map(role ->
                new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

        return new UserDetailsClass(
                user.getUserId(),
                user.getUsername(),
                user.getFirstname(),
                user.getLastname(),
                user.getEmail(),
                user.getPassword(),
                roles);
    }

    public Long getUserId() { return userId; }

    public String getFirstname() { return firstname; }

    public String getLastname() { return lastname; }

    public String getEmail() { return email; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        UserDetailsClass user = (UserDetailsClass) o;
        return Objects.equals(userId, user.userId);
    }
}
