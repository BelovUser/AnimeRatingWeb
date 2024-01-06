package com.example.animerating.services;

import com.example.animerating.dtos.AnimeDataDTO;
import com.example.animerating.models.Anime;
import com.example.animerating.models.User;
import com.example.animerating.repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final UserService userService;


    @Autowired
    public AnimeService(AnimeRepository animeRepository, UserService userService) {
        this.animeRepository = animeRepository;
        this.userService = userService;
    }


    public Optional<Anime> getById(Long id) {
        return animeRepository.findById(id);
    }

    public List<Anime> getAll() {
        return (List<Anime>) animeRepository.findAll();
    }

    public void save(Anime anime) {
        animeRepository.save(anime);
    }

    public void saveAnimeToUser(AnimeDataDTO animeDataDTO, User user,
                                Optional<String> addToSeen, Optional<String> dontAddToList) {
        Anime anime = new Anime();
        anime.setTitleEn(animeDataDTO.titleEng());
        anime.setTitleJp(animeDataDTO.titleJP());
        anime.setReleaseDate(animeDataDTO.releaseDate());
        anime.setEpisodeCount(animeDataDTO.episodes());
        anime.setSynopsis(animeDataDTO.description());
        anime.setPosterUrl(animeDataDTO.posterImage());
        anime.setAnimationRatting(animeDataDTO.getAnimationRatingAsInt());
        anime.setArtStyleRatting(animeDataDTO.getArtStyleRatingAsInt());
        anime.setCharactersRatting(animeDataDTO.getCharactersRatingAsInt());
        anime.setStoryRatting(animeDataDTO.getStoryRatingAsInt());

        if(addToSeen.get().equals("true")){
            anime.setSeen(true);
        } else {
            anime.setSeen(false);
        }
        anime.getUsers().add(user);

        animeRepository.save(anime);

        user.getAnime().add(anime);
        userService.save(user);
    }

    public Integer getAllEpisodesSum() {
        return animeRepository.findAll().stream()
                .mapToInt(a -> Integer.parseInt(a.getEpisodeCount()))
                .sum();
    }

    public List<Anime> getTopTenAnime() {
        return animeRepository.findTop6ByOrderByAverageRattingDesc().stream()
                .filter(Anime::getSeen)
                .toList();
    }

    public Anime getFavoriteAnime() {
        return animeRepository.findFirstByOrderByAverageRattingDesc().orElse(null);
    }

    public List<Anime> getSeenAnime(User user) {
        return user.getAnime().stream()
                .filter(Anime::getSeen)
                .toList();
    }

    public List<Anime> getNotSeenAnime(User user) {
        return user.getAnime().stream()
                .filter(a -> !a.getSeen())
                .filter(a -> !a.getCurrentlyWatching())
                .toList();
    }

    public Anime getByJpTitle(String titleJp){
        if(animeRepository.findByTitleJp(titleJp).isEmpty()){
            throw new RuntimeException("Could not found an Anime with" + titleJp + " title.");
        }
        return animeRepository.findByTitleJp(titleJp).get();
    }

    public void changeAnimeStatus(User user, Long animeId) {
        Optional<Anime> animeOptional = user.getAnime().stream()
                .filter(a -> Objects.equals(a.getId(), animeId))
                .findFirst();

        animeOptional.ifPresent(anime -> {
            anime.setCurrentlyWatching(true);
            userService.save(user);
        });
    }

    public void deleteAnime(User user, Long animeId){
        user.getAnime().removeIf(a -> Objects.equals(a.getId(), animeId));
        userService.save(user);
    }

    public List<Anime> getCurretlyWatchingList(){
        return animeRepository.findAllByCurrentlyWatchingTrue();
    }
}
