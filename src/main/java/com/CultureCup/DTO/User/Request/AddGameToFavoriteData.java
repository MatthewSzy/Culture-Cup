package com.CultureCup.DTO.User.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddGameToFavoriteData {

    private Long userId;

    private Long gameId;

    private Double gameRating;
}
