package com.example.animerating.dtos;

public record AnimeRattingDTO(String artStyleRatting,
                              String animationRatting,
                              String storyRatting,
                              String charactersRatting,
                              Long animeId) {
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
