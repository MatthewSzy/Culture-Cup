package com.CultureCup.DTO.Movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieListItem {

    private Long movieId;

    private String title;

    private Date releaseDate;

    private String posterImage;

    private Double voteAverage;

    private Long voteCount;
}
