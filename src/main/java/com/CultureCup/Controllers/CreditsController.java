package com.CultureCup.Controllers;

import com.CultureCup.DTO.Credits.CastListItem;
import com.CultureCup.DTO.Credits.CrewListItem;
import com.CultureCup.DTO.Credits.PersonData;
import com.CultureCup.Services.Credits.CreditsService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/credit")
public class CreditsController {

    private final CreditsService creditsService;

    public CreditsController(CreditsService creditsService) { this.creditsService = creditsService; }

    @GetMapping("/get/cast/{id}")
    public ResponseEntity<List<CastListItem>> getMovieCast(@PathVariable(name = "id") Long movieId) {

        List<CastListItem> castListItems = creditsService.getMovieCast(movieId);
        return ResponseEntity.ok(castListItems);
    }

    @GetMapping("/get/crew/{id}")
    public ResponseEntity<List<CrewListItem>> getMovieCrew(@PathVariable(name = "id") Long movieId) {

        List<CrewListItem> crewListItems = creditsService.getMovieCrew(movieId);
        return ResponseEntity.ok(crewListItems);
    }

    @GetMapping("/get/person/{id}")
    public ResponseEntity<PersonData> getPerson(@PathVariable(name = "id") Long personId) {

        PersonData personData = creditsService.getPerson(personId);
        return ResponseEntity.ok(personData);
    }
}
