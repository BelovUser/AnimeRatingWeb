package com.example.animerating.dtos;

public record AnimeRatingDTO(String artStyleRating,
                             String animationRating,
                             String storyRating,
                             String charactersRating,
                             Long animeId) {
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
