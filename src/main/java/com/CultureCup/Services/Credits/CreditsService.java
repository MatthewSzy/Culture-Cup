package com.CultureCup.Services.Credits;

import com.CultureCup.DTO.Credits.CastListItem;
import com.CultureCup.DTO.Credits.CrewListItem;
import com.CultureCup.DTO.Credits.PersonData;
import com.CultureCup.Services.Movie.HttpRequestClass.HttpRequestClass;
import com.CultureCup.Services.Movie.ImageDownloadClass.ImageDownloadClass;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class CreditsService {

    public static final String getMovieCreditsFirstPartURL = "https://api.themoviedb.org/3/movie/";
    public static final String getMovieCreditsSecondPartURL = "/credits?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL";
    public static final String getPersonFirstPartURL = "https://api.themoviedb.org/3/person/";
    public static final String getPersonSecondPartURL = "?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL";
    public static final String getProfileImageURL = "http://image.tmdb.org/t/p/original";

    public List<CastListItem> getMovieCast(Long movieId) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getMovieCreditsFirstPartURL + movieId + getMovieCreditsSecondPartURL).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("cast"));

        List<CastListItem> castListItems = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject actor = jsonArray.getJSONObject(i);

            byte[] profileImage = null;
            if (!actor.isNull("profile_path")) profileImage = ImageDownloadClass.getImage(getProfileImageURL + actor.getString("profile_path"));
            castListItems.add(CastListItem.builder()
                    .actorId(actor.getLong("id"))
                    .name(actor.getString("name"))
                    .profileImage(profileImage)
                    .character(actor.getString("character"))
                    .build());
        }

        return castListItems;
    }

    public List<CrewListItem> getMovieCrew(Long movieId) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getMovieCreditsFirstPartURL + movieId + getMovieCreditsSecondPartURL).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("crew"));

        List<CrewListItem> crewListItems = new ArrayList<>();
        for (int i = 0; i <jsonArray.length(); i++) {

            JSONObject person = jsonArray.getJSONObject(i);
            byte[] profileImage = null;
            if (!person.isNull("profile_path")) profileImage = ImageDownloadClass.getImage(getProfileImageURL + person.getString("profile_path"));
            crewListItems.add(CrewListItem.builder()
                    .personId(person.getLong("id"))
                    .name(person.getString("name"))
                    .profileImage(profileImage)
                    .department(person.getString("department"))
                    .build());
        }

        return crewListItems;
    }

    public PersonData getPerson(Long personId) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getPersonFirstPartURL + personId + getPersonSecondPartURL).body());

        byte[] profileImage = ImageDownloadClass.getImage(getProfileImageURL + jsonObject.getString("profile_path"));

        return PersonData.builder()
                .personId(jsonObject.getLong("id"))
                .name(jsonObject.getString("name"))
                .department(jsonObject.getString("known_for_department"))
                .biography(jsonObject.getString("biography"))
                .birthDay(Date.valueOf(jsonObject.getString("birthday")))
                .deathDay(jsonObject.isNull("deathday")? null : Date.valueOf(jsonObject.getString("deathday")))
                .placeOfBirth(jsonObject.getString("place_of_birth"))
                .profileImage(profileImage)
                .build();
    }
}
