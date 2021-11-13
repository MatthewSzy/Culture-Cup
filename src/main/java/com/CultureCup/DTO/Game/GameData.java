package com.CultureCup.DTO.Game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.Map;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameData {

    private Long gameId;

    private String title;

    private String overview;

    private String perspective;

    private String posterImageURL;

    private String backdropImageURL;

    private Map<Date, ArrayList<String>> releaseDates;

    private Map<String, String> expansions;

    private Set<String> platforms;

    private String franchises;

    private String engine;

    private String mods;

    private int ageRating;

    private Double voteAverage;

    private Long voteCount;

    private Set<String> categories;
}
