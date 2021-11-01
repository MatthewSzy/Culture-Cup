package com.CultureCup.Services.Movie;

import com.CultureCup.DTO.Movie.MovieData;
import com.CultureCup.DTO.Movie.MovieListItem;
import com.CultureCup.Services.Movie.CollectionCreateClass.CollectionCreateClass;
import com.CultureCup.Services.Movie.HttpRequestClass.HttpRequestClass;
import com.CultureCup.Services.Movie.ImageDownloadClass.ImageDownloadClass;
import com.CultureCup.Services.Movie.ObjectCreateClass.ObjectCreateClass;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@Service
public class MovieService {

    public static final String getMovieFirstPartURL = "https://api.themoviedb.org/3/movie/";
    public static final String getMovieSecondPartURL = "?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL";
    public static final String getPopularMoviesURL = "https://api.themoviedb.org/3/movie/popular?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL&page=";
    public static final String getTopMoviesURL = "https://api.themoviedb.org/3/movie/top_rated?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL&page=";
    public static final String getUpcomingMoviesURL = "https://api.themoviedb.org/3/movie/upcoming?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL&page=";
    public static final String getMovieImageURL = "http://image.tmdb.org/t/p/original";

    public MovieData getMovie(Long movieId) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getMovieFirstPartURL + movieId + getMovieSecondPartURL).body());

        byte[] backdropPath = ImageDownloadClass.getImage(getMovieImageURL + jsonObject.getString("backdrop_path"));
        byte[] posterPath = ImageDownloadClass.getImage(getMovieImageURL + jsonObject.getString("poster_path"));

        return MovieData.builder()
                .movieId(jsonObject.getLong("id"))
                .originalLanguage(jsonObject.getString("original_language"))
                .originalTitle(jsonObject.getString("original_title"))
                .title(jsonObject.getString("title"))
                .overview(jsonObject.getString("overview"))
                .runtime(jsonObject.getLong("runtime"))
                .releaseDate(Date.valueOf(jsonObject.getString("release_date")))
                .backdropImage(backdropPath)
                .posterImage(posterPath)
                .homePage(jsonObject.getString("homepage"))
                .budget(jsonObject.getLong("budget"))
                .revenue(jsonObject.getLong("revenue"))
                .voteAverage(0.0)
                .voteCount(0L)
                .status(jsonObject.getString("status"))
                .categories(CollectionCreateClass.createCategoriesCollection(jsonObject.getJSONArray("genres")))
                .productionCountries(CollectionCreateClass.createProductionCountriesCollection(jsonObject.getJSONArray("production_countries")))
                .productionCompanies(CollectionCreateClass.createProductionCompaniesCollection(jsonObject.getJSONArray("production_companies")))
                .build();
    }

    public List<MovieListItem> getPopularMovies(Long page) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getPopularMoviesURL + page).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("results"));

        List<MovieListItem> movieListData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) movieListData.add(ObjectCreateClass.createMovieListItem(jsonArray.getJSONObject(i)));

        return movieListData;
    }

    public List<MovieListItem> getTopMovies(Long page) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getTopMoviesURL + page).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("results"));

        List<MovieListItem> movieListData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) movieListData.add(ObjectCreateClass.createMovieListItem(jsonArray.getJSONObject(i)));

        return movieListData;
    }

    public List<MovieListItem> getUpcomingMovies(Long page) {

        JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getUpcomingMoviesURL + page).body());
        JSONArray jsonArray = new JSONArray(jsonObject.getJSONArray("results"));

        List<MovieListItem> movieListData = new ArrayList<>();
        for (int i = 0; i < jsonArray.length(); i++) movieListData.add(ObjectCreateClass.createMovieListItem(jsonArray.getJSONObject(i)));

        return movieListData;
    }

    public List<MovieListItem> getAllMovies(Long page) {

        List<MovieListItem> movieListData = new ArrayList<>();
        for (long i = 101+((page-1)*20); i < 121+((page-1)*20); i++) {
            JSONObject movie = new JSONObject(HttpRequestClass.sendRequest(getMovieFirstPartURL + i + getMovieSecondPartURL).body());
            if (!movie.has("poster_path") || movie.isNull("poster_path")) continue;
            movieListData.add(ObjectCreateClass.createMovieListItem(movie));
        }

        return movieListData;
    }
}
