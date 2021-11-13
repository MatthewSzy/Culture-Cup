package com.CultureCup.Services.Game.HttpRequestClass;

import com.CultureCup.Exceptions.RequestErrorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class HttpRequestClass {

    static Logger logger = LoggerFactory.getLogger(HttpRequestClass.class);

    public static HttpResponse<String> sendGetRequestToIGDB(String path, String token) {

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .GET()
                    .header("Client-ID", "o7e5emy3bv5krc8795n6di1o2rm1ce")
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .uri(URI.create(path))
                    .build();

            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            logger.error("The query to the movie database could not be completed");
            throw new RequestErrorException("Nie można zrealizować zapytania do bazy filmów!");
        }
    }

    public static HttpResponse<String> sendPostRequestToIGDB(String path, String data, String token) {

        try {
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest httpRequest = HttpRequest.newBuilder()
                    .POST(HttpRequest.BodyPublishers.ofString(data))
                    .header("Client-ID", "o7e5emy3bv5krc8795n6di1o2rm1ce")
                    .header("Authorization", "Bearer " + token)
                    .header("Accept", "application/json")
                    .uri(URI.create(path))
                    .build();

            return httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        } catch (IOException | InterruptedException e) {
            logger.error("The query to the movie database could not be completed");
            throw new RequestErrorException("Nie można zrealizować zapytania do bazy filmów!");
        }
    }
}
