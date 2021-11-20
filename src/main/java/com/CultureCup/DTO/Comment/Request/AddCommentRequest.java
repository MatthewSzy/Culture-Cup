package com.CultureCup.DTO.Comment.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddCommentRequest {

    private Long movieId;

    private Long gameId;

    private Long userId;

    private String info;
}
