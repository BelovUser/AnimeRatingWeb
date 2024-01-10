package com.example.animerating.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class WebClientService {

    private final WebClient webClient;

    @Autowired
    public WebClientService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://kitsu.io/api/edge").build();
    }

    public Mono<String> fetchDataById(int id) {
        return webClient.get()
                .uri("/anime/{id}",id)
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> searchAnimeCategories(List<String> categories) {
        return webClient.get()
                .uri("/anime", uriBuilder -> uriBuilder
                        .queryParam("filter[categories]", categories.toArray())
                        .build())
                .retrieve()
                .bodyToMono(String.class);
    }
}
