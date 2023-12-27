package com.example.animerating.controllers;

import com.example.animerating.dtos.AnimeData;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/anime_rate")
public class MainController {

    @GetMapping("/")
    public String getMainPage(){
        return "main";
    }

    @GetMapping("/user_list")
    public String getUserListPage(){
        return "userList";
    }

    @PostMapping("/add_seen")
    public String addToWatched(@RequestParam("titleEng") String titleEng,
                               @RequestParam("titleJP") String titleJP,
                               @RequestParam("releaseDate") String releaseDate,
                               @RequestParam("episodes") String episodes,
                               @RequestParam("description") String description,
                               @RequestParam("posterImage") String posterImage) {


        System.out.println("Received data:");
        System.out.println("Title (EN): " + titleEng);
        System.out.println("Title (JP): " + titleJP);
        System.out.println("Release Date: " + releaseDate);
        System.out.println("Episodes: " + episodes);
        System.out.println("Description: " + description);
        System.out.println("Poster Image: " + posterImage);

        return "redirect:/anime_rate/";
    }
}
