package com.example.animerating.dtos;

public record AnimeDataDTO(String titleEng,
                           String titleJP,
                           String releaseDate,
                           String episodes,
                           String description,
                           String posterImage,
                           String artStyleRatting,
                           String animationRatting,
                           String storyRatting,
                           String charactersRatting,
                           Long id) {
    public Integer getArtStyleRatingAsInt() {
        return convertToInt(artStyleRatting);
    }

    public Integer getAnimationRatingAsInt() {
        return convertToInt(animationRatting);
    }

    public Integer getStoryRatingAsInt() {
        return convertToInt(storyRatting);
    }

    public Integer getCharactersRatingAsInt() {
        return convertToInt(charactersRatting);
    }

    private Integer convertToInt(String rating) {
        try {
            return rating != null ? Integer.parseInt(rating) : null;
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
