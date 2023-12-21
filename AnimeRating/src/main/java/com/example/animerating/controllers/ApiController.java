package com.example.animerating.controllers;

import com.example.animerating.kitsuapicalls.KitsuApiCalls;
import com.example.animerating.models.KitsuAnimeResponse;
import com.example.animerating.services.AnimeService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public KitsuAnimeResponse getAnimeById(@PathVariable String id) {
        return animeService.getAnimeById(id);
    }
}