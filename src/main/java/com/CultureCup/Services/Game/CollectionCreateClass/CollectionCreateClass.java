package com.CultureCup.Services.Game.CollectionCreateClass;

import com.CultureCup.Services.Game.HttpRequestClass.HttpRequestClass;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class CollectionCreateClass {

    static Logger logger = LoggerFactory.getLogger(CollectionCreateClass.class);

    public static final String getExpensionsFirstPartURL = "https://api.igdb.com/v4/games/";
    public static final String getExpensionsSecondPartURL = "?&fields=cover.*,name";
    public static final String getPlatformFirstPartURL = "https://api.igdb.com/v4/platforms/";
    public static final String getPlatformSecondPartURL = "?&fields=name";

    public static Map<Date, ArrayList<String>> CreateReleaseDatesCollection(JSONArray jsonArray, String accessToken) {

        if (jsonArray.isEmpty()) return null;
        Map<Date, ArrayList<String>> releaseDates = new HashMap<>();

        for (int i = 0; i <jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray platform = new JSONArray(HttpRequestClass.sendGetRequestToIGDB(getPlatformFirstPartURL + jsonObject.getLong("platform") + getPlatformSecondPartURL, accessToken).body());

            Date date = new Date((long)jsonObject.getLong("date")*1000);
            if (!releaseDates.containsKey(date)) releaseDates.put(date, new ArrayList<>());
            releaseDates.get(date).add(platform.getJSONObject(0).getString("name"));
        }

        return releaseDates;
    }

    public static Map<String, String> CreateExpansionsCollection(JSONArray jsonArray, String accessToken) {

        if (jsonArray.isEmpty()) return null;
        Map<String, String> expansions = new HashMap<>();

        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            JSONArray expansion = new JSONArray(HttpRequestClass.sendGetRequestToIGDB(getExpensionsFirstPartURL + jsonObject.getLong("id") + getExpensionsSecondPartURL, accessToken).body());
            jsonObject = expansion.getJSONObject(0);

            expansions.put(jsonObject.getString("name"), jsonObject.getJSONObject("cover").getString("url"));
        }

        return expansions;
    }

    public static Set<String> CreatePlatformsCollection(JSONArray jsonArray) {

        Set<String> platforms = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) platforms.add(jsonArray.getJSONObject(i).getString("name"));

        return platforms;
    }

    public static Set<String> CreateCategoriesCollection(JSONArray jsonArrayGenres, JSONArray jsonArrayThemes) {

        Set<String> categories = new HashSet<>();
        for (int i = 0; i < jsonArrayGenres.length(); i++) categories.add(jsonArrayGenres.getJSONObject(i).getString("name"));
        for (int i = 0; i < jsonArrayThemes.length(); i++) categories.add(jsonArrayThemes.getJSONObject(i).getString("name"));

        return categories;
    }
}
