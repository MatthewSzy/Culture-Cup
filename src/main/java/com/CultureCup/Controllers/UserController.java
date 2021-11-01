package com.CultureCup.Controllers;

import com.CultureCup.DTO.MessageResponse;
import com.CultureCup.DTO.Movie.MovieListItem;
import com.CultureCup.DTO.User.Request.*;
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
    public ResponseEntity<MessageResponse> userUpdate(@RequestBody UpdateData updateData) {

        MessageResponse messageResponse = userService.userUpdate(updateData);
        return ResponseEntity.ok(messageResponse);
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

    @PutMapping("/add/favorite")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<MessageResponse> userAddMovieToFavorite(@RequestBody AddMovieToFavoriteData addMovieToFavoriteData) {

        MessageResponse messageResponse = userService.userAddMovieToFavorite(addMovieToFavoriteData);
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

    @GetMapping("/get/favorite/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<MovieListItem>> userGetFavoriteMovies(@PathVariable(name = "id") Long userId) {

        List<MovieListItem> movieListItem = userService.userGetFavoriteMovies(userId);
        return ResponseEntity.ok(movieListItem);
    }

    @GetMapping("/get/info")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Boolean>> userGetMoviesInfo(@RequestBody CheckUserMoviesInfoData checkUserMoviesInfoData) {

        List<Boolean> moviesInfo = userService.userGetMoviesInfo(checkUserMoviesInfoData);
        return ResponseEntity.ok(moviesInfo);
    }
}
