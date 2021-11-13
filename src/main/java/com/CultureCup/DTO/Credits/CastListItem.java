package com.CultureCup.DTO.Credits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CastListItem {

    private Long actorId;

    private String name;

    private String profileImage;

    private String character;
}
