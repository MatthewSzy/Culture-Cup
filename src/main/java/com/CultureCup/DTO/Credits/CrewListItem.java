package com.CultureCup.DTO.Credits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrewListItem {

    private Long personId;

    private String name;

    private byte[] profileImage;

    private String department;
}
