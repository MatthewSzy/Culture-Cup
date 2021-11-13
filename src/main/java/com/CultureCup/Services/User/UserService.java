package com.CultureCup.Services.User;

import com.CultureCup.DTO.Game.GameListItem;
import com.CultureCup.DTO.MessageResponse;
import com.CultureCup.DTO.Movie.MovieListItem;
import com.CultureCup.DTO.User.Request.*;
import com.CultureCup.DTO.User.Response.UserDataListItem;
import com.CultureCup.DTO.User.Response.UserImageData;
import com.CultureCup.DTO.User.Response.UserTokenData;
import com.CultureCup.Entities.User;
import com.CultureCup.Exceptions.*;
import com.CultureCup.Repositories.RoleRepository;
import com.CultureCup.Repositories.UserRepository;
import com.CultureCup.Security.TokenClass.JwtUtils;
import com.CultureCup.Services.Movie.HttpRequestClass.HttpRequestClass;
import com.CultureCup.Services.Movie.ObjectCreateClass.ObjectCreateClass;
import com.CultureCup.Services.User.AuthenticationClass.AuthenticationClass;
import com.CultureCup.Services.User.CastToListClass.CastToListClass;
import com.CultureCup.Services.User.ConvertToArrayBytesClass.ConvertToArrayBytesClass;
import com.CultureCup.Services.User.PatternCheckClass.PatternCheckClass;
import com.CultureCup.Services.User.RolesSetCreateClass.RolesSetCreateClass;
import com.CultureCup.Services._DatabaseAnalysis.UserAnalysis;
import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public static final String getMovieFirstPartURL = "https://api.themoviedb.org/3/movie/";
    public static final String getMovieSecondPartURL = "?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL";
    public static final String getGameFirstPartURL = "https://api.igdb.com/v4/games/";

    public String accessToken = "fgkdv5nm5d8d7dga2z2ed3p4zcdltn";

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtUtils jwtUtils;

    @Autowired
    PasswordEncoder passwordEncoder;

    Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserTokenData userLogin(LoginData loginData) {

        Authentication authentication = AuthenticationClass.authenticateUser(authenticationManager, loginData);
        UserTokenData userTokenData = AuthenticationClass.getUserData(authentication, jwtUtils);

        logger.info("User successfully logged in: " +  "\"" + loginData.getUsername() + "\"");
        return userTokenData;
    }

    public MessageResponse userRegistration(RegistrationData registrationData) {

        String username = registrationData.getUsername();
        String email = registrationData.getEmail();
        String password = registrationData.getPassword();

        UserAnalysis.existsUserByUsername(userRepository, username);
        UserAnalysis.existsUserByEmail(userRepository, email);

        PatternCheckClass.usernamePattern(username);
        PatternCheckClass.emailPattern(email);
        PatternCheckClass.passwordPattern(password);

        userRepository.save(User.builder()
                .username(username)
                .firstname("")
                .lastname("")
                .email(email)
                .password(passwordEncoder.encode(password))
                .profileImage(ConvertToArrayBytesClass.convertDefaultIcon())
                .roles(RolesSetCreateClass.newUserRoles(roleRepository, registrationData.getRoles()))
                .build());

        logger.info("User successfully registered: \"" + username + "\"");
        return new MessageResponse("Rejestracja przebiegła pomyślnie.");
    }

    public UserTokenData userUpdate(UpdateData updateData) {

        if (updateData.getUsername() == null || updateData.getFirstname() == null || updateData.getLastname() == null || updateData.getEmail() == null) {
            logger.error("New user data is empty");
            throw new IncorrectDataInput("Nowe dane są błędne!");
        }

        User user = UserAnalysis.findUserByUserId(userRepository, updateData.getUserId());

        String username = updateData.getUsername();
        if (!user.getUsername().equals(username) && !username.equals("")) {
            UserAnalysis.existsUserByUsername(userRepository, username);
            PatternCheckClass.usernamePattern(username);
            user.setUsername(username);
        }

        String firstname = updateData.getFirstname();
        if (!user.getFirstname().equals(firstname) && !firstname.equals("")) {
            user.setFirstname(firstname);
        }

        String lastname = updateData.getLastname();
        if (!user.getLastname().equals(lastname) && !lastname.equals("")) {
            user.setLastname(lastname);
        }

        String email = updateData.getEmail();
        if (!user.getEmail().equals(email) && !email.equals("")) {
            UserAnalysis.existsUserByEmail(userRepository, email);
            PatternCheckClass.emailPattern(email);
            user.setEmail(email);
        }

        userRepository.save(user);

        logger.error("User data successfully updated: \"" + username + "\"");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(updateData.getUsername(), updateData.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserTokenData userTokenData = AuthenticationClass.getUserData(authentication, jwtUtils);
        return userTokenData;
    }

    public MessageResponse userDelete(DeleteData deleteData) {

        AuthenticationClass.authenticateUser(authenticationManager, deleteData);
        userRepository.delete(UserAnalysis.findUserByUserId(userRepository, deleteData.getUserId()));

        logger.info("User successfully deleted: " +  "\"" + deleteData.getUsername() + "\"");
        return new MessageResponse("Konto zostało usunięte.");
    }

    public MessageResponse userUpload(Long userId, MultipartFile image) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        user.setProfileImage(ConvertToArrayBytesClass.convertNewIcon(image));

        userRepository.save(user);
        logger.info("New profile picture added correctly: " + user.getUsername());
        return new MessageResponse("Poprawnie dodano nowe zdjęcie profilowe.");
    }

    public UserImageData userGetImage(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        return UserImageData.builder().profileImage(user.getProfileImage()).build();
    }

    public List<UserDataListItem> getListOfUsers() {

        List<User> users = UserAnalysis.findAllUsers(userRepository);
        return CastToListClass.castToListForAdmin(users);
    }

    public MessageResponse addAdminRole(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        user.setRoles(RolesSetCreateClass.rolesSetWithAdmin(roleRepository));

        userRepository.save(user);
        logger.info("The admin role for the user has been added for: " + user.getUsername());
        return new MessageResponse("Rola admina dla użytkownika została dodana");
    }

    public MessageResponse deleteAdminRole(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        user.setRoles(RolesSetCreateClass.rolesSetWithoutAdmin(roleRepository));

        userRepository.save(user);
        logger.info("The admin role for the user has been removed for: " + user.getUsername());
        return new MessageResponse("Rola admina dla użytkownika została usunięta");
    }

    public MessageResponse userAddMovieToWatch(AddMovieToWatchData addMovieToWatchData) {

        User user = UserAnalysis.findUserByUserId(userRepository, addMovieToWatchData.getUserId());

        if (user.getMoviesToWatch().contains(addMovieToWatchData.getMovieId())) {
            logger.error("Movie has already been added to " + addMovieToWatchData.getUserId() + " watch list!");
            throw new MovieAlreadyAddedToListException("Film został już dodany do listy DO OBEJRZENIA!");
        }

        if (user.getMoviesWatched().containsKey(addMovieToWatchData.getMovieId())) {
            logger.error("Movie has already been added to " + addMovieToWatchData.getUserId() + " watched list!");
            throw new MovieAlreadyAddedToListException("Film został już obejrzany!");
        }

        user.getMoviesToWatch().add(addMovieToWatchData.getMovieId());
        userRepository.save(user);

        logger.info("Movie: " + "\"" + addMovieToWatchData.getMovieId() + "\"" + " added to " + "\"" + user.getUsername() + "\"" + " list of movies to watch");
        return new MessageResponse("Film został dodany do listy \"DO OBEJRZENIA\".");
    }

    public MessageResponse userAddMovieToWatched(AddMovieToWatchedData addMovieToWatchedData) {

        User user = UserAnalysis.findUserByUserId(userRepository, addMovieToWatchedData.getUserId());

        if (user.getMoviesWatched().containsKey(addMovieToWatchedData.getMovieId())) {
            logger.error("Movie has already been added to " + addMovieToWatchedData.getUserId() + " watched list!");
            throw new MovieAlreadyAddedToListException("Film został już obejrzany!");
        }

        user.getMoviesToWatch().remove(addMovieToWatchedData.getMovieId());
        user.getMoviesWatched().put(addMovieToWatchedData.getMovieId(), addMovieToWatchedData.getMovieRating());
        userRepository.save(user);

        logger.info("Movie: " + "\"" + addMovieToWatchedData.getMovieId() + "\"" + " added to " + "\"" + user.getUsername() + "\"" + " list of movies watched");
        return new MessageResponse("Film został dodany do listy \"OBEJRZANE\".");
    }

    public MessageResponse userAddMovieToFavorite(AddMovieToFavoriteData addMovieToFavoriteData) {

        User user = UserAnalysis.findUserByUserId(userRepository, addMovieToFavoriteData.getUserId());

        if (user.getMoviesToWatch().contains(addMovieToFavoriteData.getMovieId())) {
            logger.error("You need to watch Movie and add to watched!");
            throw new MovieNotBeenWatchedException("Film nie został obejrzany, nie można go dodac do ulubionych!");
        }

        if (!user.getMoviesWatched().containsKey(addMovieToFavoriteData.getMovieId())) {
            logger.error("Movie has not been watched by " + addMovieToFavoriteData.getUserId());
            throw new MovieNotBeenWatchedException("Film nie został obejrzany, nie można go dodać do ulubionych!");
        }

        if (user.getFavoriteMovies().containsKey(addMovieToFavoriteData.getMovieId())) {
            logger.error("Movie has already been added to " + addMovieToFavoriteData.getUserId() + " favorite list!");
            throw new MovieAlreadyAddedToListException("Film został już dodany do ulubionych!");
        }

        user.getFavoriteMovies().put(addMovieToFavoriteData.getMovieId(), addMovieToFavoriteData.getMovieRating());
        userRepository.save(user);

        logger.info("Movie: " + "\"" + addMovieToFavoriteData.getMovieId() + "\"" + " added to " + "\"" + user.getUsername() + "\"" + " favorite movies list");
        return new MessageResponse("Film został dodany do listy \"ULUBIONE\".");
    }

    public List<MovieListItem> userGetToWatchMovies(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<MovieListItem> movieListItem = new ArrayList<>();

        for (Long movieId : user.getMoviesToWatch()) {
            JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequestToTMDB(getMovieFirstPartURL + movieId + getMovieSecondPartURL).body());
            movieListItem.add(ObjectCreateClass.createMovieListItem(jsonObject));
        }

        return movieListItem;
    }

    public List<MovieListItem> userGetWatchedMovies(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<MovieListItem> movieListItem = new ArrayList<>();

        user.getMoviesWatched().forEach((movieId, movieRating) -> {
            JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequestToTMDB(getMovieFirstPartURL + movieId + getMovieSecondPartURL).body());
            movieListItem.add(ObjectCreateClass.createMovieListItem(jsonObject));
        });

        return movieListItem;
    }

    public List<MovieListItem> userGetFavoriteMovies(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<MovieListItem> movieListItem = new ArrayList<>();

        user.getFavoriteMovies().forEach((movieId, movieRating) -> {
            JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequestToTMDB(getMovieFirstPartURL + movieId + getMovieSecondPartURL).body());
            movieListItem.add(ObjectCreateClass.createMovieListItem(jsonObject));
        });

        return movieListItem;
    }

    public List<Boolean> userGetMoviesInfo(CheckUserMoviesInfoData checkUserMoviesInfoData) {

        User user = UserAnalysis.findUserByUserId(userRepository, checkUserMoviesInfoData.getUserId());
        Long movieId = checkUserMoviesInfoData.getMovieId();
        List<Boolean> moviesInfo = new ArrayList<>();

        if ((user.getMoviesToWatch().contains(movieId))) { moviesInfo.add(true); } else { moviesInfo.add(false); }
        if ((user.getMoviesWatched().containsKey(movieId))) { moviesInfo.add(true); moviesInfo.set(0, true); } else { moviesInfo.add(false); }
        if ((user.getFavoriteMovies().containsKey(movieId))) { moviesInfo.add(true); moviesInfo.set(0, true); moviesInfo.set(1, true); } else { moviesInfo.add(false); }

        return moviesInfo;
    }

    public MessageResponse userAddGameToPlay(AddGameToPlayData addGameToPlayData) {

        User user = UserAnalysis.findUserByUserId(userRepository, addGameToPlayData.getUserId());

        if (user.getGamesToPlay().contains(addGameToPlayData.getGameId())) {
            logger.error("Game has already been added to " + addGameToPlayData.getUserId() +  " play list!");
            throw new GameAlreadyAddedToListException("Film został już dodany do listy DO ZAGRANIA!");
        }

        if (user.getGamesPlayed().containsKey(addGameToPlayData.getGameId())) {
            logger.error("Game has already been added to " + addGameToPlayData.getUserId() +  " played list!");
            throw new GameAlreadyAddedToListException("Film został już dodany do listy ROZEGRANE!");
        }

        user.getGamesToPlay().add(addGameToPlayData.getGameId());
        userRepository.save(user);

        logger.info("Game: " + "\"" + addGameToPlayData.getGameId() + "\"" + " added to " + "\"" + user.getUsername() + "\"" + " list of games to play");
        return new MessageResponse("Gra została dodany do listy \"DO ZAGRANIA\".");
    }

    public MessageResponse userAddGameToPlayed(AddGameToPlayedData addGameToPlayedData) {

        User user = UserAnalysis.findUserByUserId(userRepository, addGameToPlayedData.getUserId());

        if (user.getGamesPlayed().containsKey(addGameToPlayedData.getGameId())) {
            logger.error("Game has already been added to " + addGameToPlayedData.getUserId() + " played list!");
            throw new GameAlreadyAddedToListException("Film został już dodany do listy ROZEGRANE!");
        }

        user.getGamesToPlay().remove(addGameToPlayedData.getGameId());
        user.getGamesPlayed().put(addGameToPlayedData.getGameId(), addGameToPlayedData.getGameRating());
        userRepository.save(user);

        logger.info("Game: " + "\"" + addGameToPlayedData.getGameId() + "\"" + " added to " + "\"" + user.getUsername() + "\"" + " list of played games");
        return new MessageResponse("Gra została dodany do listy \"ROZEGRANE\".");
    }

    public MessageResponse userAddGameToFavorite(AddGameToFavoriteData addGameToFavoriteData) {

        User user = UserAnalysis.findUserByUserId(userRepository, addGameToFavoriteData.getUserId());

        if (user.getGamesToPlay().contains(addGameToFavoriteData.getGameId())) {
            logger.error("You need to play Game and add to played!");
            throw new GameNotBeenWatchedException("Gra nie została ograna, nie można jej dodać do ulubionych!");
        }

        if (!user.getGamesPlayed().containsKey(addGameToFavoriteData.getGameId())) {
            logger.error("You need to play Game and add to played!");
            throw new GameNotBeenWatchedException("Gra nie została ograna, nie można jej dodać do ulubionych!");
        }

        if (user.getFavoriteGames().containsKey(addGameToFavoriteData.getGameId())) {
            logger.error("Game has already been added to " + addGameToFavoriteData.getUserId() +  " favorite list!");
            throw new GameAlreadyAddedToListException("Film został już dodany do listy ULUBIONE!");
        }

        user.getFavoriteGames().put(addGameToFavoriteData.getGameId(), addGameToFavoriteData.getGameRating());
        userRepository.save(user);

        logger.info("Game: " + "\"" + addGameToFavoriteData.getGameId() + "\"" + " added to " + "\"" + user.getUsername() + "\"" + " favorite games list");
        return new MessageResponse("Film został dodany do listy \"ULUBIONE\".");
    }

    public List<GameListItem> userGetToPlayGames(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<GameListItem> gameListItem = new ArrayList<>();

        for (Long gameId : user.getGamesToPlay()) {
            JSONArray jsonArray = new JSONArray(com.CultureCup.Services.Game.HttpRequestClass.HttpRequestClass.sendPostRequestToIGDB(getGameFirstPartURL,"?fields cover.url,name,rating,rating_count,release_dates.date; where id = " + gameId + " & cover != null & release_dates != null & release_dates.date != null;", accessToken).body());
            gameListItem.add(com.CultureCup.Services.Game.ObjectCreateClass.ObjectCreateClass.CreateGameListItem(jsonArray.getJSONObject(0)));
        }

        return gameListItem;
    }

    public List<GameListItem> userGetPlayedGames(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<GameListItem> gameListItem = new ArrayList<>();

        user.getGamesPlayed().forEach((gameId, gameRating) -> {
            JSONArray jsonArray = new JSONArray(com.CultureCup.Services.Game.HttpRequestClass.HttpRequestClass.sendPostRequestToIGDB(getGameFirstPartURL,"?fields cover.url,name,rating,rating_count,release_dates.date; where id = " + gameId + " & cover != null & release_dates != null & release_dates.date != null;", accessToken).body());
            gameListItem.add(com.CultureCup.Services.Game.ObjectCreateClass.ObjectCreateClass.CreateGameListItem(jsonArray.getJSONObject(0)));
        });

        return gameListItem;
    }

    public List<GameListItem> userGetFavoriteGames(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<GameListItem> gameListItem = new ArrayList<>();

        user.getFavoriteGames().forEach((gameId, gameRating) -> {
            JSONArray jsonArray = new JSONArray(com.CultureCup.Services.Game.HttpRequestClass.HttpRequestClass.sendPostRequestToIGDB(getGameFirstPartURL,"?fields cover.url,name,rating,rating_count,release_dates.date; where id = " + gameId + " & cover != null & release_dates != null & release_dates.date != null;", accessToken).body());
            gameListItem.add(com.CultureCup.Services.Game.ObjectCreateClass.ObjectCreateClass.CreateGameListItem(jsonArray.getJSONObject(0)));
        });

        return gameListItem;
    }

    public List<Boolean> userGetGamesInfo(CheckUserGamesInfoData checkUserGamesInfoData) {

        User user = UserAnalysis.findUserByUserId(userRepository, checkUserGamesInfoData.getUserId());
        Long gameId = checkUserGamesInfoData.getGameId();
        List<Boolean> gamesInfo = new ArrayList<>();

        if ((user.getGamesToPlay().contains(gameId))) { gamesInfo.add(true); } else { gamesInfo.add(false); }
        if ((user.getGamesPlayed().containsKey(gameId))) { gamesInfo.add(true); gamesInfo.set(0, true); } else { gamesInfo.add(false); }
        if ((user.getFavoriteGames().containsKey(gameId))) { gamesInfo.add(true); gamesInfo.set(0, true); gamesInfo.set(1, true); } else { gamesInfo.add(false); }

        return gamesInfo;
    }
}
