package com.example.animerating.dtos;

public record AnimeDataDTO(String titleEng,
                           String titleJP,
                           String releaseDate,
                           String episodes,
                           String description,
                           String posterImage,
                           String artStyleRating,
                           String animationRating,
                           String storyRating,
                           String charactersRating,
                           Long id) {
    public Integer getArtStyleRatingAsInt() {
        return convertToInt(artStyleRating);
    }

    public Integer getAnimationRatingAsInt() {
        return convertToInt(animationRating);
    }

    public Integer getStoryRatingAsInt() {
        return convertToInt(storyRating);
    }

    public Integer getCharactersRatingAsInt() {
        return convertToInt(charactersRating);
    }

    private Integer convertToInt(String rating) {
        try {
            return rating != null ? Integer.parseInt(rating) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
