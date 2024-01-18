package com.example.animerating.services;

import com.example.animerating.dtos.FetchedAnimeDataDTO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.when;

@SpringBootTest
public class AnimeServiceTest {

    @Mock
    private ObjectMapper objectMapper;

    @Mock
    private WebClientService webClientService;

    @InjectMocks
    private AnimeService animeService;

    @Test
    void testConvertJsonArrayToFetchedAnimeDTOList() throws JsonProcessingException {
        String jsonString = "{\"data\": [{\"attributes\": {\"titles\": {\"en\": \"Title1\", \"ja_jp\": \"TitleJP1\"}, \"startDate\": \"2022-01-01\", \"episodeCount\": \"12\", \"synopsis\": \"Synopsis1\", \"posterImage\": {\"medium\": \"image1.jpg\"}}}, {\"attributes\": {\"titles\": {\"en\": \"Title2\", \"ja_jp\": \"TitleJP2\"}, \"startDate\": \"2022-02-01\", \"episodeCount\": \"24\", \"synopsis\": \"Synopsis2\", \"posterImage\": {\"medium\": \"image2.jpg\"}}}]}}";

        JsonNode jsonNode = objectMapper.readTree(jsonString);
        when(objectMapper.readTree(jsonString)).thenReturn(jsonNode);

        List<FetchedAnimeDataDTO> result = animeService.convertJsonArrayToFetchedAnimeDTOList(jsonString);

        assertEquals(2, result.size());
        assertEquals("Title1", result.get(0).titleEng());
        assertEquals("TitleJP1", result.get(0).titleJP());
        assertEquals("Synopsis1", result.get(0).description());
        assertEquals("image1.jpg", result.get(0).posterImage());
        assertEquals("Title2", result.get(1).titleEng());
        assertEquals("TitleJP2", result.get(1).titleJP());
        assertEquals("Synopsis2", result.get(1).description());
        assertEquals("image2.jpg", result.get(1).posterImage());
    }

    @Test
    void testGetFetchedAnimeDTOByCategories() {
        List<String> categories = List.of("adventure", "mecha");
        when(webClientService.searchAnimeByCategories(anyList())).thenReturn(Mono.just(""));

        List<FetchedAnimeDataDTO> result = animeService.getFetchedAnimeDTOByCategories(categories);

        assertNotNull(result);
    }

    @Test
    void testGetRandomFetchedAnimeDTO() {
        String mockJsonResponse = "{\"data\": {\"attributes\": {\"titles\": {\"en\": \"Title1\", \"ja_jp\": \"TitleJP1\"}, \"startDate\": \"2022-01-01\", \"episodeCount\": \"12\", \"synopsis\": \"Synopsis1\", \"posterImage\": {\"medium\": \"image1.jpg\"}}}}";
        when(webClientService.fetchDataById(anyInt())).thenReturn(Mono.just(mockJsonResponse));

        FetchedAnimeDataDTO result = animeService.getRandomFetchedAnimeDTO();

        assertEquals("Title1", result.titleEng());
        assertEquals("TitleJP1", result.titleJP());
        assertEquals("2022-01-01", result.releaseDate());
        assertEquals("12", result.episodes());
        assertEquals("Synopsis1", result.description());
        assertEquals("image1.jpg", result.posterImage());
    }
}
