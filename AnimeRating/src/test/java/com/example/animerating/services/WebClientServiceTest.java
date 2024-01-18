package com.example.animerating.services;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class WebClientServiceTest {

    @Autowired
    private WebClientService webClientService;

    @Test
    void testFetchDataById() {
        assertNotNull(webClientService.fetchDataById(1));
    }

    @Test
    void testSearchAnimeByCategories() {
        assertNotNull(webClientService.searchAnimeByCategories(List.of("adventure", "mecha")));
    }
}
