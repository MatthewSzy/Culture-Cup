package com.CultureCup.Services.Game.ObjectCreateClass;

import com.CultureCup.DTO.Game.GameListItem;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

public class ObjectCreateClass {

    static Logger logger = LoggerFactory.getLogger(ObjectCreateClass.class);

    public static GameListItem CreateGameListItem(JSONObject jsonObject) {

        return GameListItem.builder()
                .gameId(jsonObject.getLong("id"))
                .title(jsonObject.getString("name"))
                .releaseDate(jsonObject.getJSONArray("release_dates").getJSONObject(0).isNull("date")? new Date((long)jsonObject.getJSONArray("release_dates").getJSONObject(1).getLong("date")*1000) : new Date((long)jsonObject.getJSONArray("release_dates").getJSONObject(0).getLong("date")*1000))
                .posterImageURL(changeImageTypeToCoverBig(jsonObject.getJSONObject("cover").getString("url")))
                .voteAverage(jsonObject.isNull("rating")? 0.0 : jsonObject.getDouble("rating")/10)
                .voteCount(jsonObject.isNull("rating_count")? 0L : jsonObject.getLong("rating_count"))
                .build();
    }

    public static String changeImageTypeToCoverBig(String imagePath) {
        return imagePath.replace("t_thumb", "t_cover_big");
    }

    public static String changeImageTypeToOriginal(String imagePath) {
        return imagePath.replace("t_thumb", "t_original");
    }
}
