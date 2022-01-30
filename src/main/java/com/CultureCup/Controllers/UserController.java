package com.CultureCup.Controllers;

import com.CultureCup.DTO.Game.GameListItem;
import com.CultureCup.DTO.MessageResponse;
import com.CultureCup.DTO.Movie.MovieListItem;
import com.CultureCup.DTO.User.Request.*;
import com.CultureCup.DTO.User.Response.UserDataListItem;
import com.CultureCup.DTO.User.Response.UserImageData;
import com.CultureCup.DTO.User.Response.UserTokenData;
import com.CultureCup.Services.User.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) { this.userService = userService; }

    @PostMapping("/login")
    public ResponseEntity<UserTokenData> userLogin(@RequestBody LoginData loginData) {

        UserTokenData userTokenData = userService.userLogin(loginData);
        return ResponseEntity.ok(userTokenData);
    }

    @PostMapping("/registration")
    public ResponseEntity<MessageResponse> userRegistration(@RequestBody RegistrationData registrationData) {

        MessageResponse messageResponse = userService.userRegistration(registrationData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserTokenData> userUpdate(@RequestBody UpdateData updateData) {

        UserTokenData userTokenData = userService.userUpdate(updateData);
        return ResponseEntity.ok(userTokenData);
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userDelete(@RequestBody DeleteData deleteData) {

        MessageResponse messageResponse = userService.userDelete(deleteData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/upload/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userUpload(@PathVariable(name = "id") Long userId, @RequestParam("image") MultipartFile image) {

        MessageResponse messageResponse = userService.userUpload(userId, image);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/get/image/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<UserImageData> userGetImage(@PathVariable(name = "id") Long userId) {

        UserImageData userImageData = userService.userGetImage(userId);
        return ResponseEntity.ok(userImageData);
    }

    @GetMapping("/get/usersList")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDataListItem>> getListOfUsers() {

        List<UserDataListItem> response = userService.getListOfUsers();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/add/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> addAdminRole(@PathVariable(name = "id") Long userId) {

        MessageResponse response = userService.addAdminRole(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("delete/admin/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<MessageResponse> deleteAdminRole(@PathVariable(name = "id") Long userId) {

        MessageResponse response = userService.deleteAdminRole(userId);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/find/{username}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Long> findUserbyUsername(@PathVariable(name = "username") String username) {

        Long response = userService.findUserByUsername(username);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/add/toWatch")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddMovieToWatch(@RequestBody AddMovieToWatchData addMovieToWatchData) {

        MessageResponse messageResponse = userService.userAddMovieToWatch(addMovieToWatchData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/add/watched")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddMovieToWatched(@RequestBody AddMovieToWatchedData addMovieToWatchedData) {

        MessageResponse messageResponse = userService.userAddMovieToWatched(addMovieToWatchedData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/add/favoriteMovie")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddMovieToFavorite(@RequestBody AddMovieToFavoriteData addMovieToFavoriteData) {

        MessageResponse messageResponse = userService.userAddMovieToFavorite(addMovieToFavoriteData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/add/movieRating")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddRatingToMovie(@RequestBody AddRatingToMovie addRatingToMovie) {

        MessageResponse messageResponse = userService.userAddRatingToMovie(addRatingToMovie);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/get/toWatch/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<MovieListItem>> userGetToWatchMovies(@PathVariable(name = "id") Long userId) {

        List<MovieListItem> movieListItem = userService.userGetToWatchMovies(userId);
        return ResponseEntity.ok(movieListItem);
    }

    @GetMapping("/get/watched/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<MovieListItem>> userGetWatchedMovies(@PathVariable(name = "id") Long userId) {

        List<MovieListItem> movieListItem = userService.userGetWatchedMovies(userId);
        return ResponseEntity.ok(movieListItem);
    }

    @GetMapping("/get/favoriteMovie/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<MovieListItem>> userGetFavoriteMovies(@PathVariable(name = "id") Long userId) {

        List<MovieListItem> movieListItem = userService.userGetFavoriteMovies(userId);
        return ResponseEntity.ok(movieListItem);
    }

    @PostMapping("/get/movieRating")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> userGetMovieRating(@RequestBody CheckUserMoviesInfoData checkUserMoviesInfoData) {

        Integer response = userService.userGetMovieRating(checkUserMoviesInfoData);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get/movieInfo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Boolean>> userGetMoviesInfo(@RequestBody CheckUserMoviesInfoData checkUserMoviesInfoData) {

        List<Boolean> moviesInfo = userService.userGetMoviesInfo(checkUserMoviesInfoData);
        return ResponseEntity.ok(moviesInfo);
    }

    @PutMapping("/add/toPlay")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddGameToPlay(@RequestBody AddGameToPlayData addGameToPlayData) {

        MessageResponse messageResponse = userService.userAddGameToPlay(addGameToPlayData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/add/played")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddGameToPlayed(@RequestBody AddGameToPlayedData addGameToPlayedData) {

        MessageResponse messageResponse = userService.userAddGameToPlayed(addGameToPlayedData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/add/favoriteGame")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddGameToFavorite(@RequestBody AddGameToFavoriteData addGameToFavoriteData) {

        MessageResponse messageResponse = userService.userAddGameToFavorite(addGameToFavoriteData);
        return ResponseEntity.ok(messageResponse);
    }

    @PutMapping("/add/gameRating")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddRatingToGame(@RequestBody AddRatingToGame addRatingToGame) {

        MessageResponse messageResponse = userService.userAddRatingToGame(addRatingToGame);
        return ResponseEntity.ok(messageResponse);
    }

    @GetMapping("/get/toPlay/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GameListItem>> userGetToPlayGames(@PathVariable(name = "id") Long userId) {

        List<GameListItem> gameListItem = userService.userGetToPlayGames(userId);
        return ResponseEntity.ok(gameListItem);
    }

    @GetMapping("/get/played/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GameListItem>> userGetPlayedGames(@PathVariable(name = "id") Long userId) {

        List<GameListItem> gameListItem = userService.userGetPlayedGames(userId);
        return ResponseEntity.ok(gameListItem);
    }

    @GetMapping("/get/favoriteGame/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GameListItem>> userGetFavoriteGames(@PathVariable(name = "id") Long userId) {

        List<GameListItem> gameListItem = userService.userGetFavoriteGames(userId);
        return ResponseEntity.ok(gameListItem);
    }

    @PostMapping("/get/gameRating")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<Integer> userGetGameRating(@RequestBody CheckUserGamesInfoData checkUserGamesInfoData) {

        Integer response = userService.userGetGameRating(checkUserGamesInfoData);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/get/gameInfo")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Boolean>> userGetGamesInfo(@RequestBody CheckUserGamesInfoData checkUserGamesInfoData) {

        List<Boolean> gamesInfo = userService.userGetGamesInfo(checkUserGamesInfoData);
        return ResponseEntity.ok(gamesInfo);
    }
}
