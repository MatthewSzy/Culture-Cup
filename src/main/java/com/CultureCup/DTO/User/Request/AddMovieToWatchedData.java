package com.CultureCup.DTO.User.Request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMovieToWatchedData {

    private Long userId;

    private Long movieId;

    private Double movieRating;
}
