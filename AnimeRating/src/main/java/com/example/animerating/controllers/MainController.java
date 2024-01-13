package com.example.animerating.controllers;

import com.example.animerating.dtos.AnimeDataDTO;
import com.example.animerating.dtos.AnimeRatingDTO;
import com.example.animerating.dtos.FetchedAnimeDataDTO;
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
        FetchedAnimeDataDTO randomAnime = animeService.getRandomUniqueToUserAnime(user);
        model.addAttribute("anime",randomAnime);
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

    @PostMapping("/edit_anime")
    public String editAnime(AnimeRatingDTO animeRatingDTO){
        animeService.editAnimeToUser(animeRatingDTO);
        return "redirect:/user_list";
    }

    @GetMapping("/top_rated_anime")
    public String getTopRatedPage(Model model, Principal principal) {
        List<Anime> topAnime = animeService.getTopSixAnime();
        model.addAttribute("topAnime", topAnime);
        return "topRatedAnime";
    }

    @PostMapping("/add_watching_anime")
    public String addAnimeToCurrentlyWatching(@RequestParam Long animeId, Principal principal) {
        User user = getUser(principal);
        animeService.changeAnimeStatus(user, animeId);
        return "redirect:/user_list";
    }

    @DeleteMapping("/delete_anime")
    public String deleteAnime(@RequestParam Long animeId, Principal principal) {
        User user = getUser(principal);
        animeService.deleteAnime(user, animeId);
        return "redirect:/user_list";
    }

    @PostMapping("/rate_anime")
    public String rateAnime(@ModelAttribute AnimeDataDTO anime, Principal principal,Model model) {
        model.addAttribute("anime",anime);
        return "main";
    }

    @PostMapping("/edit_anime_rating")
    public String editAnimeRating(@RequestParam Long animeId, Principal principal,Model model) {
        User user = getUser(principal);
        Anime anime = animeService.getById(user,animeId);
        model.addAttribute("anime",anime);
        return "animeRating";
    }

    @GetMapping("/anime_categories")
    public String getSearchAnimePage(){
        return "searchPage";
    }

    @PostMapping("/search_anime")
    public String searchAnime(@RequestBody@RequestParam(value = "category", required = false) List<String> categories,
                                     Model model){
            List<FetchedAnimeDataDTO> searchedAnime = animeService.getFetchedAnimeDTOByCategories(categories);
            model.addAttribute("searchedAnime", searchedAnime);
            return "searchPage";
    }

    private User getUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + principal.getName()));
    }
}
