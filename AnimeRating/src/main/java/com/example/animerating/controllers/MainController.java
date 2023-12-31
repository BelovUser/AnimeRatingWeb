package com.example.animerating.controllers;

import com.example.animerating.dtos.AnimeDataDTO;
import com.example.animerating.models.Anime;
import com.example.animerating.models.User;
import com.example.animerating.services.AnimeService;
import com.example.animerating.services.UserService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/anime_rate")
public class MainController {

    private final UserService userService;
    private final AnimeService animeService;

    public MainController(UserService userService, AnimeService animeService) {
        this.userService = userService;
        this.animeService = animeService;
    }

    @GetMapping("/")
    public String getMainPage(Principal principal){
        return "main";
    }

    @GetMapping("/user_list")
    public String getUserListPage(Principal principal, Model model){
        User user = getUser(principal);
        List<Anime> seenAnime = animeService.getSeenAnime(user);
        List<Anime> notSeenAnime = animeService.getNotSeenAnime(user);
        Integer EpisodesSeen = animeService.getAllEpisodesSum();
        Anime favoriteAnime = animeService.getFavoriteAnime();

        model.addAttribute("seenAnime",seenAnime);
        model.addAttribute("notSeenAnime",notSeenAnime);
        model.addAttribute("episodesSeen", EpisodesSeen);
        model.addAttribute("favoriteAnime",favoriteAnime);

        return "userList";
    }

    @PostMapping("/add_seen")
    public String addToWatched(@RequestParam Optional<String> seenAnime,@RequestParam Optional<String> dontAddToList,
                               AnimeDataDTO animeDataDTO, Principal principal) {
        User user = getUser(principal);
        animeService.saveAnimeToUser(animeDataDTO,user,seenAnime,dontAddToList);

        return "redirect:/anime_rate/";
    }

    private User getUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + principal.getName()));
    }
}
