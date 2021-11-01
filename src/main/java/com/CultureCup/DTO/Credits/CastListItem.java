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

    private byte[] profileImage;

    private String character;
}
