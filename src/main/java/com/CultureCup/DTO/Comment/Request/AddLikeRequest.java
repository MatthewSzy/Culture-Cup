package com.CultureCup.DTO.Comment.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddLikeRequest {

    private Long commentId;

    private Long userId;
}
