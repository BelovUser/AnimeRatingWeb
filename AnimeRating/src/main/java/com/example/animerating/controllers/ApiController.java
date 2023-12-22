package com.example.animerating.controllers;

import com.example.animerating.kitsuapicalls.KitsuApiCalls;
import com.example.animerating.models.KitsuAnimeResponse;
import com.example.animerating.services.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private final AnimeService animeService;

    @Autowired
    public ApiController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping("/anime/{id}")
    public ResponseEntity<?> getAnimeById(@PathVariable Long id) {
        if(animeService.getAnimeById(id) == null){
            return ResponseEntity.badRequest().body("The anime was not found :(");
        }
        return ResponseEntity.ok(animeService.getAnimeById(id));
    }
}