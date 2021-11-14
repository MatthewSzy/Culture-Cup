package com.CultureCup.Services.Game;

import com.CultureCup.DTO.Game.GameData;
import com.CultureCup.DTO.Game.GameListItem;
import com.CultureCup.Services.Game.AccessTokenRequestClass.AccessTokenRequestClass;
import com.CultureCup.Services.Game.CollectionCreateClass.CollectionCreateClass;
import com.CultureCup.Services.Game.HttpRequestClass.HttpRequestClass;
import com.CultureCup.Services.Game.ObjectCreateClass.ObjectCreateClass;
import org.apache.http.client.utils.DateUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Service
public class GameService {

    public static final String getGameFirstPartURL = "https://api.igdb.com/v4/games/";
    public static final String getGameSecondPartURL = "?&fields=age_ratings.rating,category,cover.url,expansions.id,franchises.name,game_engines.name,game_modes.name,genres.name,name,player_perspectives.name,platforms.name,release_dates.*,status,screenshots.url,storyline,summary,themes.name,total_rating,total_rating_count";

    public String accessToken = "fgkdv5nm5d8d7dga2z2ed3p4zcdltn";

    public GameData getGame(Long gameId) {

        if (accessToken.equals("")) { AccessTokenRequestClass.AccessTokenRequest("https://id.twitch.tv/oauth2/token?client_id=o7e5emy3bv5krc8795n6di1o2rm1ce&client_secret=kumwkncx39ee7m4stywpmuizaot80v&grant_type=client_credentials"); }

        JSONArray jsonArray = new JSONArray(HttpRequestClass.sendGetRequestToIGDB(getGameFirstPartURL + gameId + getGameSecondPartURL, accessToken).body());
        JSONObject jsonObject = jsonArray.getJSONObject(0);
        return GameData.builder()
                .gameId(jsonObject.getLong("id"))
                .title(jsonObject.getString("name"))
                .overview(jsonObject.isNull("summary")? null : jsonObject.getString("summary"))
                .perspective(jsonObject.isNull("player_perspective")? null : jsonObject.getJSONArray("player_perspectives").getJSONObject(0).getString("name"))
                .posterImageURL(jsonObject.isNull("cover")? null : ObjectCreateClass.changeImageTypeToCoverBig(jsonObject.getJSONObject("cover").getString("url")))
                .backdropImageURL(jsonObject.isNull("screenshots")? null : ObjectCreateClass.changeImageTypeToOriginal(jsonObject.getJSONArray("screenshots").getJSONObject(0).getString("url")))
                .releaseDates(jsonObject.isNull("release_dates")? null : CollectionCreateClass.CreateReleaseDatesCollection(jsonObject.getJSONArray("release_dates"), accessToken))
                .expansions(jsonObject.isNull("expansions")? null : CollectionCreateClass.CreateExpansionsCollection(jsonObject.getJSONArray("expansions"), accessToken))
                .platforms(jsonObject.isNull("platforms")? null : CollectionCreateClass.CreatePlatformsCollection(jsonObject.getJSONArray("platforms")))
                .franchises(jsonObject.isNull("franchises")? null : jsonObject.getJSONArray("franchises").getJSONObject(0).getString("name"))
                .engine(jsonObject.isNull("game_engines")? null : jsonObject.getJSONArray("game_engines").getJSONObject(0).getString("name"))
                .mods(jsonObject.isNull("game_modes")? null : jsonObject.getJSONArray("game_modes").getJSONObject(0).getString("name"))
                .ageRating((jsonObject.isNull("age_ratings") || jsonObject.getJSONArray("age_ratings").getJSONObject(1).isNull("rating"))? null : jsonObject.getJSONArray("age_ratings").getJSONObject(1).getInt("rating"))
                .voteAverage(jsonObject.isNull("total_rating")? null : jsonObject.getDouble("total_rating")/10)
                .voteCount(jsonObject.isNull("total_rating_count")? null : jsonObject.getLong("total_rating_count"))
                .categories(jsonObject.isNull("genres")? null : CollectionCreateClass.CreateCategoriesCollection(jsonObject.getJSONArray("genres"), jsonObject.getJSONArray("themes")))
                .build();
    }

    public List<GameListItem> getAllGames(Long page) {

        JSONArray jsonArray = new JSONArray(HttpRequestClass.sendPostRequestToIGDB(getGameFirstPartURL, "fields cover.url,name,rating,rating_count,release_dates.date; where id > 1000 & cover != null & release_dates != null & release_dates.date != null; limit 20; offset " + (page-1)*20 + ";", accessToken).body());

        List<GameListItem> gameListItem = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) gameListItem.add(ObjectCreateClass.CreateGameListItem(jsonArray.getJSONObject(i)));

        return gameListItem;

    }

    public List<GameListItem> getTopGames(Long page) {

        JSONArray jsonArray = new JSONArray(HttpRequestClass.sendPostRequestToIGDB(getGameFirstPartURL, "fields cover.url,name,rating,rating_count,release_dates.date; sort rating desc; where rating != null & cover != null & rating_count > 50 & release_dates != null & release_dates.date != null; limit 20; offset " + (page-1)*20 + ";", accessToken).body());

        List<GameListItem> gameListItem = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) gameListItem.add(ObjectCreateClass.CreateGameListItem(jsonArray.getJSONObject(i)));

        return gameListItem;
    }

    public List<GameListItem> getUpcomingGames(Long page) {

        Date actualDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, 1);
        JSONArray jsonArray = new JSONArray(HttpRequestClass.sendPostRequestToIGDB(getGameFirstPartURL, "fields cover.url,name,rating,rating_count,release_dates.date; sort rating desc; where cover != null & release_dates != null & release_dates.date != null & release_dates.date > " + actualDate.getTime() + " & release_dates.date > " + calendar.getTimeInMillis() + "; limit 20; offset " + (page-1)*20 + ";", accessToken).body());

        List<GameListItem> gameListItem = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) gameListItem.add(ObjectCreateClass.CreateGameListItem(jsonArray.getJSONObject(i)));

        return gameListItem;
    }
}
