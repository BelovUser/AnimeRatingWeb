package com.example.animerating.services;

import com.example.animerating.dtos.AnimeDataDTO;
import com.example.animerating.models.Anime;
import com.example.animerating.models.User;
import com.example.animerating.repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
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

    public void saveAnimeToUser(AnimeDataDTO animeDataDTO, User user){
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
        anime.setSeen(true);
        anime.getUsers().add(user);

        animeRepository.save(anime);

        user.getAnime().add(anime);
        userService.save(user);
    }
}
