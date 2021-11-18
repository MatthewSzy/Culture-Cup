package com.CultureCup.DTO.Comments.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddUnlikeRequest {

    private Long commentId;

    private Long userId;
}
