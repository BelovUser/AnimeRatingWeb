package com.example.animerating.services;

import com.example.animerating.dtos.AnimeDataDTO;
import com.example.animerating.dtos.AnimeRattingDTO;
import com.example.animerating.dtos.KitsuAnimeDTO;
import com.example.animerating.models.Anime;
import com.example.animerating.models.User;
import com.example.animerating.repositories.AnimeRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;
import java.util.stream.Collectors;

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

    public void editAnimeToUser(AnimeRattingDTO animeRattingDTO){
        Anime anime = animeRepository.findById(animeRattingDTO.animeId()).get();
        anime.setAnimationRatting(animeRattingDTO.getAnimationRatingAsInt());
        anime.setArtStyleRatting(animeRattingDTO.getArtStyleRatingAsInt());
        anime.setCharactersRatting(animeRattingDTO.getCharactersRatingAsInt());
        anime.setStoryRatting(animeRattingDTO.getStoryRatingAsInt());
        anime.setSeen(true);
        anime.setCurrentlyWatching(false);

        animeRepository.save(anime);
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

    public Anime getById(User user, Long animeId){
        Optional<Anime> animeOptional = user.getAnime().stream()
                .filter(a -> Objects.equals(a.getId(), animeId))
                .findFirst();
        if(animeOptional.isEmpty()){
            throw new RuntimeException("Could not found anime by this id:"+animeId);
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

    public void deleteAnime(User user, Long animeId){
        user.getAnime().removeIf(a -> Objects.equals(a.getId(), animeId));
        userService.save(user);
    }

    public List<Anime> getCurretlyWatchingList(){
        return animeRepository.findAllByCurrentlyWatchingTrue();
    }

    public KitsuAnimeDTO getRandomUniqueToUserAnime(User user){
        List<String> titlesJpOwnedByUser = user.getAnime().stream()
                .map(Anime::getTitleJp)
                .toList();

        KitsuAnimeDTO randomAnime;
        do {
            randomAnime = getRandomKitsuAnimeDTO();
        } while (titlesJpOwnedByUser.contains(randomAnime.titleJP()));

        return randomAnime;
    }

    public KitsuAnimeDTO getRandomKitsuAnimeDTO(){
        Mono<String> fetchedData = webClientService.fetchDataById(new Random().nextInt(300));
        return fetchedData
                .map(r -> convertJsonToKitsuAnimeDTO(r))
                .block();
    }
    public KitsuAnimeDTO getKitsuAnimeDTOByTitle(String searchTerm) {
        Mono<String> fetchedData = webClientService.searchAnimeByTitle(searchTerm);
        return fetchedData
                .map(r -> convertJsonToKitsuAnimeDTO(r))
                .block();
    }

    private KitsuAnimeDTO convertJsonToKitsuAnimeDTO(String jsonString) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonString);
            JsonNode attributes = jsonNode.path("data").path("attributes");

            String titleEng = attributes.path("titles").path("en").asText();
            String titleJP = attributes.path("titles").path("ja_jp").asText();
            String startDate = attributes.path("startDate").asText();
            String episodeCount = attributes.path("episodeCount").asText();
            String synopsis = attributes.path("synopsis").asText();
            String posterImage = attributes.path("posterImage").path("medium").asText();

            return new KitsuAnimeDTO(titleEng, titleJP, startDate, episodeCount, synopsis, posterImage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            return null;
        }
    }
}
