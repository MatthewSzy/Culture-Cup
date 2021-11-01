package com.CultureCup.Services.User;

import com.CultureCup.DTO.MessageResponse;
import com.CultureCup.DTO.Movie.MovieListItem;
import com.CultureCup.DTO.User.Request.*;
import com.CultureCup.DTO.User.Response.UserImageData;
import com.CultureCup.DTO.User.Response.UserTokenData;
import com.CultureCup.Entities.User;
import com.CultureCup.Exceptions.IncorrectDataInput;
import com.CultureCup.Exceptions.MovieAlreadyAddedToListException;
import com.CultureCup.Exceptions.MovieNotBeenWatchedException;
import com.CultureCup.Repositories.RoleRepository;
import com.CultureCup.Repositories.UserRepository;
import com.CultureCup.Security.TokenClass.JwtUtils;
import com.CultureCup.Services.Movie.HttpRequestClass.HttpRequestClass;
import com.CultureCup.Services.Movie.ObjectCreateClass.ObjectCreateClass;
import com.CultureCup.Services.User.AuthenticationClass.AuthenticationClass;
import com.CultureCup.Services.User.ConvertToArrayBytesClass.ConvertToArrayBytesClass;
import com.CultureCup.Services.User.PatternCheckClass.PatternCheckClass;
import com.CultureCup.Services.User.RolesSetCreateClass.RolesSetCreateClass;
import com.CultureCup.Services._DatabaseAnalysis.UserAnalysis;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    public static final String getMovieFirstPartURL = "https://api.themoviedb.org/3/movie/";
    public static final String getMovieSecondPartURL = "?api_key=6728e1ab041b59d1f357590bff4384f5&language=pl-PL";

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

    public MessageResponse userUpdate(UpdateData updateData) {

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
        return new MessageResponse("Dane zostały zaktualizowane.");
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
            throw new MovieNotBeenWatchedException("Film nie został obejrzny, nie można go dodac do ulubionych!");
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

        logger.info("Movie: " + "\"" + addMovieToFavoriteData.getMovieId() + "\"" + " added to " + "\"" + user.getUsername() + "\"" + " list of movies watched");
        return new MessageResponse("Film został dodany do listy \"ULUBIONE\".");
    }

    public List<MovieListItem> userGetToWatchMovies(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<MovieListItem> movieListItem = new ArrayList<>();

        for (Long movieId : user.getMoviesToWatch()) {
            JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getMovieFirstPartURL + movieId + getMovieSecondPartURL).body());
            movieListItem.add(ObjectCreateClass.createMovieListItem(jsonObject));
        }

        return movieListItem;
    }

    public List<MovieListItem> userGetWatchedMovies(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<MovieListItem> movieListItem = new ArrayList<>();

        user.getMoviesWatched().forEach((movieId, movieRating) -> {
            JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getMovieFirstPartURL + movieId + getMovieSecondPartURL).body());
            movieListItem.add(ObjectCreateClass.createMovieListItem(jsonObject));
        });

        return movieListItem;
    }

    public List<MovieListItem> userGetFavoriteMovies(Long userId) {

        User user = UserAnalysis.findUserByUserId(userRepository, userId);
        List<MovieListItem> movieListItem = new ArrayList<>();

        user.getFavoriteMovies().forEach((movieId, movieRating) -> {
            JSONObject jsonObject = new JSONObject(HttpRequestClass.sendRequest(getMovieFirstPartURL + movieId + getMovieSecondPartURL).body());
            movieListItem.add(ObjectCreateClass.createMovieListItem(jsonObject));
        });

        return movieListItem;
    }

    public List<Boolean> userGetMoviesInfo(CheckUserMoviesInfoData checkUserMoviesInfoData) {

        User user = UserAnalysis.findUserByUserId(userRepository, checkUserMoviesInfoData.getUserId());
        Long movieId = checkUserMoviesInfoData.getMovieId();
        List<Boolean> moviesInfo = new ArrayList<>();

        if ((user.getMoviesToWatch().contains(movieId))) { moviesInfo.add(true); } else { moviesInfo.add(false); }
        if ((user.getMoviesWatched().containsKey(movieId))) { moviesInfo.add(true); } else { moviesInfo.add(false); }
        if ((user.getFavoriteMovies().containsKey(movieId))) { moviesInfo.add(true); } else { moviesInfo.add(false); }

        return moviesInfo;
    }
}
