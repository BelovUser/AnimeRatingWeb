package com.example.animerating.controllers;

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
        List<Anime> seenAnime = getUser(principal).getAnime().stream()
                .filter(Anime::getSeen)
                .toList();
        model.addAttribute("seenAnime",seenAnime);
        return "userList";
    }

    @PostMapping("/add_seen")
    public String addToWatched(@RequestParam("titleEng") String titleEng,
                               @RequestParam("titleJP") String titleJP,
                               @RequestParam("releaseDate") String releaseDate,
                               @RequestParam("episodes") String episodes,
                               @RequestParam("description") String description,
                               @RequestParam("posterImage") String posterImage,
                               Principal principal) {
        User user = getUser(principal);

        Anime anime = new Anime();
        anime.setTitleEn(titleEng);
        anime.setTitleJp(titleJP);
        anime.setReleaseDate(releaseDate);
        anime.setEpisodeCount(episodes);
        anime.setSynopsis(description);
        anime.setPosterUrl(posterImage);
        anime.setSeen(true);
        anime.getUsers().add(user);

        animeService.save(anime);

        user.getAnime().add(anime);
        userService.save(user);


//        System.out.println("Received data:");
//        System.out.println("Title (EN): " + titleEng);
//        System.out.println("Title (JP): " + titleJP);
//        System.out.println("Release Date: " + releaseDate);
//        System.out.println("Episodes: " + episodes);
//        System.out.println("Description: " + description);
//        System.out.println("Poster Image: " + posterImage);

        return "redirect:/anime_rate/";
    }

    private User getUser(Principal principal) {
        return userService.findByUsername(principal.getName())
                .orElseThrow(() -> new UsernameNotFoundException("User not found with username: " + principal.getName()));
    }
}
