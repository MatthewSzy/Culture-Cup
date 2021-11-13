package com.CultureCup.DTO.Game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GameListItem {

    private Long gameId;

    private String title;

    private Date releaseDate;

    private String posterImageURL;

    private Double voteAverage;

    private Long voteCount;
}
