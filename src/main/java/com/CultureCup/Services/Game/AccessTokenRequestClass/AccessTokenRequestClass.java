package com.CultureCup.Services.Game.AccessTokenRequestClass;

import com.CultureCup.Exceptions.RequestErrorException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class AccessTokenRequestClass {

    static Logger logger = LoggerFactory.getLogger(AccessTokenRequestClass.class);

    public static String AccessTokenRequest(String path) {

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .header("accept", "application/json")
                    .uri(URI.create("https://id.twitch.tv/oauth2/token?client_id=o7e5emy3bv5krc8795n6di1o2rm1ce&client_secret=kumwkncx39ee7m4stywpmuizaot80v&grant_type=client_credentials"))
                    .build();

            JSONObject tokenRequest = new JSONObject(httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString()).body());
            return tokenRequest.getString("access_token");

        } catch (IOException | InterruptedException e) {
            logger.error("The query to the movie database could not be completed");
            throw new RequestErrorException("Nie można zrealizować zapytania do bazy filmów!");
        }
    }
}
