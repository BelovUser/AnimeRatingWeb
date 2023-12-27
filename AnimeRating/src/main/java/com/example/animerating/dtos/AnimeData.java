package com.example.animerating.dtos;

public record AnimeData(String titleEng,
                        String titleJP,
                        String releaseDate,
                        String episodes,
                        String description,
                        float artStyleRating,
                        float animationRating,
                        float storyRating,
                        float charactersRating) {
}
