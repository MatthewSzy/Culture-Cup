package com.CultureCup.Entities;

import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "users")
public class User {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long userId;

        private String username;

        private String firstname;

        private String lastname;

        private String email;

        private String password;

        private byte[] profileImage;

        @ElementCollection
        private Set<Long> moviesToWatch = new HashSet<>();

        @ElementCollection
        private Map<Long, Double> moviesWatched = new HashMap<>();

        @ElementCollection
        private Map<Long, Double> favoriteMovies = new HashMap<>();

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable( name = "user_roles",
                joinColumns = @JoinColumn(name = "user_id"),
                inverseJoinColumns = @JoinColumn(name = "role_id"))
        private Set<Role> roles = new HashSet<>();
}
