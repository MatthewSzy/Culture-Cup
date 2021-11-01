package com.CultureCup.DTO.Credits;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PersonData {

    public Long personId;

    public String name;

    public String department;

    public String biography;

    public Date birthDay;

    public Date deathDay;

    public String placeOfBirth;

    public byte[] profileImage;
}
