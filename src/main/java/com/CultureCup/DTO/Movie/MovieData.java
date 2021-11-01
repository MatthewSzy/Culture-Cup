package com.CultureCup.DTO.Movie;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MovieData {

    private Long movieId;

    private String originalLanguage;

    private String originalTitle;

    private String title;

    private String overview;

    private Long runtime;

    private Date releaseDate;

    private byte[] backdropImage;

    private byte[] posterImage;

    private String homePage;

    private Long budget;

    private Long revenue;

    private Double voteAverage;

    private Long voteCount;

    private String status;

    private Set<String> categories;

    private Set<String> productionCountries;

    private Map<Long, String> productionCompanies;
}
