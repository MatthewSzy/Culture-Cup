package com.CultureCup.Controllers;

import com.CultureCup.DTO.Game.GameData;
import com.CultureCup.DTO.Game.GameListItem;
import com.CultureCup.Services.Game.GameService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/game")
public class GameController {

    private final GameService gameService;

    public GameController(GameService gameService) { this.gameService = gameService; }

    @GetMapping("/get/{id}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<GameData> getGame(@PathVariable(name = "id") Long gameId) {

        GameData gameData = gameService.getGame(gameId);
        return ResponseEntity.ok(gameData);
    }

    @GetMapping("/get/all/{page}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GameListItem>> getAllGames(@PathVariable(name = "page") Long page) {

        List<GameListItem> gameListItem = gameService.getAllGames(page);
        return ResponseEntity.ok(gameListItem);
    }

    @GetMapping("/get/top/{page}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GameListItem>> getTopGames(@PathVariable(name = "page") Long page) {

        List<GameListItem> gameListItem = gameService.getTopGames(page);
        return ResponseEntity.ok(gameListItem);
    }

    @GetMapping("/get/upcoming/{page}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<GameListItem>> getUpcomingGames(@PathVariable(name = "page") Long page) {

        List<GameListItem> gameListItem = gameService.getUpcomingGames(page);
        return ResponseEntity.ok(gameListItem);
    }

    @GetMapping("/search/{query}")
    public ResponseEntity<List<GameListItem>> searchGames(@PathVariable(name = "query") String query) {

        List<GameListItem> gameListItem = gameService.searchGames(query);
        return ResponseEntity.ok(gameListItem);
    }
}
