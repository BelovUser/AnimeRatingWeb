package com.example.animerating.services;

import com.example.animerating.dtos.AnimeDataDTO;
import com.example.animerating.dtos.AnimeRatingDTO;
import com.example.animerating.dtos.FetchedAnimeDataDTO;
import com.example.animerating.models.Anime;
import com.example.animerating.models.User;
import com.example.animerating.repositories.AnimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;
    private final UserService userService;
    private final WebClientService webClientService;


    @Autowired
    public AnimeService(AnimeRepository animeRepository, UserService userService, WebClientService webClientService) {
        this.animeRepository = animeRepository;
        this.userService = userService;
        this.webClientService = webClientService;
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
        anime.setAnimationRating(animeDataDTO.getAnimationRatingAsInt());
        anime.setArtStyleRating(animeDataDTO.getArtStyleRatingAsInt());
        anime.setCharactersRating(animeDataDTO.getCharactersRatingAsInt());
        anime.setStoryRating(animeDataDTO.getStoryRatingAsInt());

        if (addToSeen.get().equals("true")) {
            anime.setSeen(true);
        } else {
            anime.setSeen(false);
        }
        anime.getUsers().add(user);

        animeRepository.save(anime);

        user.getAnime().add(anime);
        userService.save(user);
    }

    public void editAnimeToUser(AnimeRatingDTO animeRatingDTO) {
        Anime anime = animeRepository.findById(animeRatingDTO.animeId()).get();
        anime.setAnimationRating(animeRatingDTO.getAnimationRatingAsInt());
        anime.setArtStyleRating(animeRatingDTO.getArtStyleRatingAsInt());
        anime.setCharactersRating(animeRatingDTO.getCharactersRatingAsInt());
        anime.setStoryRating(animeRatingDTO.getStoryRatingAsInt());
        anime.setSeen(true);
        anime.setCurrentlyWatching(false);

        animeRepository.save(anime);
    }

    public Integer getAllEpisodesSum() {
        return animeRepository.findAll().stream()
                .mapToInt(a -> Integer.parseInt(a.getEpisodeCount()))
                .sum();
    }

    public List<Anime> getTopSixAnime() {
        return animeRepository.findTop6ByOrderByAverageRatingDesc().stream()
                .filter(Anime::getSeen)
                .toList();
    }

    public Anime getFavoriteAnime() {
        return animeRepository.findFirstByOrderByAverageRatingDesc().orElse(null);
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

    public Anime getById(User user, Long animeId) {
        Optional<Anime> animeOptional = user.getAnime().stream()
                .filter(a -> Objects.equals(a.getId(), animeId))
                .findFirst();
        if (animeOptional.isEmpty()) {
            throw new RuntimeException("Could not found anime by this id:" + animeId);
        }
        return animeOptional.get();
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

    public void deleteAnime(User user, Long animeId) {
        user.getAnime().removeIf(a -> Objects.equals(a.getId(), animeId));
        userService.save(user);
    }

    public List<Anime> getCurretlyWatchingList() {
        return animeRepository.findAllByCurrentlyWatchingTrue();
    }

    public FetchedAnimeDataDTO getRandomUniqueToUserAnime(User user) {
        List<String> titlesJpOwnedByUser = user.getAnime().stream()
                .map(Anime::getTitleJp)
                .toList();

        FetchedAnimeDataDTO randomAnime;
        do {
            try {
                randomAnime = getRandomFetchedAnimeDTO();
            } catch (WebClientResponseException.NotFound ex) {
                randomAnime = null;
                break;
            }
        } while (titlesJpOwnedByUser.contains(randomAnime != null ? randomAnime.titleJP() : null));

        return randomAnime;
    }

    public FetchedAnimeDataDTO getRandomFetchedAnimeDTO() {
        Mono<String> fetchedData = webClientService.fetchDataById(new Random().nextInt(100));
        return fetchedData
                .map(r -> convertJsonForRandomAnime(r))
                .block();
    }

    private List<FetchedAnimeDataDTO> convertJsonArrayToFetchedAnimeDTOList(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonArray = objectMapper.readTree(jsonString).path("data");

            return StreamSupport.stream(jsonArray.spliterator(), false)
                    .map(data -> convertJson(data.toString()))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    public List<FetchedAnimeDataDTO> getFetchedAnimeDTOByCategories(List<String> animeCategories) {
        String joinedCategories = String.join(",", animeCategories);
        Mono<String> fetchedData = webClientService.searchAnimeByCategories(List.of(joinedCategories));

        return fetchedData
                .map(r -> convertJsonArrayToFetchedAnimeDTOList(r))
                .block();
    }

    private FetchedAnimeDataDTO convertJsonForRandomAnime(String json){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode attributes = jsonNode.path("data").path("attributes");

            return new FetchedAnimeDataDTO(
                    attributes.path("titles").path("en").asText("Unknown"),
                    attributes.path("titles").path("ja_jp").asText("Unknown"),
                    attributes.path("startDate").asText("Unknown"),
                    attributes.path("episodeCount").asText("Unknown"),
                    attributes.path("synopsis").asText("No description available"),
                    attributes.path("posterImage").path("medium").asText("No image available")
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }



    private FetchedAnimeDataDTO convertJson(String json) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(json);
            JsonNode attributes = jsonNode.path("attributes");

            return new FetchedAnimeDataDTO(
                    attributes.path("titles").path("en").asText("Unknown"),
                    attributes.path("titles").path("ja_jp").asText("Unknown"),
                    attributes.path("startDate").asText("Unknown"),
                    attributes.path("episodeCount").asText("Unknown"),
                    attributes.path("synopsis").asText("No description available"),
                    attributes.path("posterImage").path("medium").asText("No image available")
            );
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
