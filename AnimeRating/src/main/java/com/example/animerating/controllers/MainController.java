package com.example.animerating.controllers;

import com.example.animerating.dtos.AnimeDataDTO;
import com.example.animerating.dtos.AnimeRattingDTO;
import com.example.animerating.dtos.KitsuAnimeDTO;
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
public class MainController {

    private final UserService userService;
    private final AnimeService animeService;

    public MainController(UserService userService, AnimeService animeService) {
        this.userService = userService;
        this.animeService = animeService;
    }

    @GetMapping("/")
    public String getMainPage(Principal principal,Model model) {
        User user = getUser(principal);
        KitsuAnimeDTO randomAnime = animeService.getRandomUniqueToUserAnime(user);
        model.addAttribute("randomAnime",randomAnime);
        return "main";

    }

    @GetMapping("/user_list")
    public String getUserListPage(Principal principal, Model model) {
        User user = getUser(principal);
        List<Anime> seenAnime = animeService.getSeenAnime(user);
        List<Anime> notSeenAnime = animeService.getNotSeenAnime(user);
        List<Anime> watchingAnime = animeService.getCurretlyWatchingList();

        model.addAttribute("seenAnime", seenAnime);
        model.addAttribute("notSeenAnime", notSeenAnime);
        model.addAttribute("currentlyWatching", watchingAnime);

        return "userList";
    }

    @PostMapping("/add_seen")
    public String addToWatched(@RequestParam Optional<String> seenAnime, @RequestParam Optional<String> dontAddToList,
                               AnimeDataDTO animeDataDTO, Principal principal) {
        User user = getUser(principal);
        animeService.saveAnimeToUser(animeDataDTO, user, seenAnime, dontAddToList);

        return "redirect:/";
    }

    @PostMapping("/edit")
    public String editAnime(AnimeRattingDTO animeRattingDTO){
        animeService.editAnimeToUser(animeRattingDTO);
        return "redirect:/user_list";
    }

    @GetMapping("/top_rated")
    public String getTopRatedPage(Model model, Principal principal) {
        List<Anime> topAnime = animeService.getTopTenAnime();
        model.addAttribute("topAnime", topAnime);
        return "topRatedAnime";
    }

    @PostMapping("/watching")
    public String addToCurrentlyWatching(@RequestParam Long animeId, Principal principal) {
        User user = getUser(principal);
        animeService.changeAnimeStatus(user, animeId);
        return "redirect:/user_list";
    }

    @PostMapping("/delete")
    public String deleteAnime(@RequestParam Long animeId, Principal principal) {
        User user = getUser(principal);
        animeService.deleteAnime(user, animeId);
        return "redirect:/user_list";
    }

    @PostMapping("/rate")
    public String getRateAnimePage(@RequestParam Long animeId, Principal principal,Model model) {
        User user = getUser(principal);
        Anime anime = animeService.getById(user,animeId);
        model.addAttribute("anime",anime);
        return "animeRating";
    }

    @PostMapping("/search")
    public String getSearchAnimePage(@RequestParam String title,Principal principal,Model model){
        KitsuAnimeDTO searchedAnime = animeService.getKitsuAnimeDTOByTitle(title);
        model.addAttribute("anime", searchedAnime);
        return "animeRating";
    }

    private User getUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + principal.getName()));
    }
}
