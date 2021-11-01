package com.CultureCup.Services.Movie.CollectionCreateClass;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CollectionCreateClass {

    static Logger logger = LoggerFactory.getLogger(CollectionCreateClass.class);

    public static Set<String> createCategoriesCollection(JSONArray jsonArray) {

        Set<String> categories = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            categories.add(jsonObject.getString("name"));
        }

        return categories;
    }

    public static Set<String> createProductionCountriesCollection(JSONArray jsonArray) {

        Set<String> productionCountries = new HashSet<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            productionCountries.add(jsonObject.getString("name"));
        }

        return productionCountries;
    }

    public static Map<Long, String> createProductionCompaniesCollection(JSONArray jsonArray) {

        Map<Long, String> productionCompanies = new HashMap<>();
        for (int i = 0; i < jsonArray.length(); i++) {

            JSONObject jsonObject = jsonArray.getJSONObject(i);
            productionCompanies.put(jsonObject.getLong("id"), jsonObject.getString("name"));
        }

        return productionCompanies;
    }
}
