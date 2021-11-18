package com.CultureCup.Entities;

import lombok.*;

import javax.persistence.*;
import java.sql.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "comments")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    private Long movieId;

    private String username;

    private byte[] profileImage;

    private Date addingDate;

    private String addingTime;

    @Column(length = 1020)
    private String info;

    private Long likeNumber;

    private Long unlikeNumber;

    @ElementCollection
    private Set<Long> usersLike = new HashSet<>();

    @ElementCollection
    private Set<Long> usersUnlike = new HashSet<>();
}
