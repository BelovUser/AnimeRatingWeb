package com.example.animerating.dtos;

public record FetchedAnimeDataDTO(String titleEng,
                                  String titleJP,
                                  String releaseDate,
                                  String episodes,
                                  String description,
                                  String posterImage) {
}
