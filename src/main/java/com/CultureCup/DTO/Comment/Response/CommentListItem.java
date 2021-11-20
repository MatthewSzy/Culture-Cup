package com.CultureCup.DTO.Comment.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommentListItem {

    private Long commentId;

    private String username;

    private byte[] profileImage;

    private Date addingDate;

    private String addingTime;

    private String info;

    private Long likeNumber;

    private Long unlikeNumber;
}
