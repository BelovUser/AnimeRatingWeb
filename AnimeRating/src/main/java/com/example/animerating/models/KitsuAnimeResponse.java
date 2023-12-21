package com.example.animerating.models;


import java.util.List;

public class KitsuAnimeResponse {

    private String createdAt;

    private String updatedAt;

    private String slug;

    private String synopsis;

    private Double coverImageTopOffset;

    private List<Title> titles;

    private Double averageRating;

    private RatingFrequencies ratingFrequencies;

    private Integer userCount;

    private Integer favoritesCount;

    private String startDate;

    private String endDate;

    private Integer popularityRank;

    private Integer ratingRank;

    private AgeRating ageRating;

    private String ageRatingGuide;

    private Subtype subtype;

    private Status status;

    private Integer episodeCount;

    private Integer episodeLength;

    private String youtubeVideoId;

    private ShowType showType;

    private Boolean nsfw;

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Double getCoverImageTopOffset() {
        return coverImageTopOffset;
    }

    public void setCoverImageTopOffset(Double coverImageTopOffset) {
        this.coverImageTopOffset = coverImageTopOffset;
    }

    public List<Title> getTitles() {
        return titles;
    }

    public void setTitles(List<Title> titles) {
        this.titles = titles;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public RatingFrequencies getRatingFrequencies() {
        return ratingFrequencies;
    }

    public void setRatingFrequencies(RatingFrequencies ratingFrequencies) {
        this.ratingFrequencies = ratingFrequencies;
    }

    public Integer getUserCount() {
        return userCount;
    }

    public void setUserCount(Integer userCount) {
        this.userCount = userCount;
    }

    public Integer getFavoritesCount() {
        return favoritesCount;
    }

    public void setFavoritesCount(Integer favoritesCount) {
        this.favoritesCount = favoritesCount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public Integer getPopularityRank() {
        return popularityRank;
    }

    public void setPopularityRank(Integer popularityRank) {
        this.popularityRank = popularityRank;
    }

    public Integer getRatingRank() {
        return ratingRank;
    }

    public void setRatingRank(Integer ratingRank) {
        this.ratingRank = ratingRank;
    }

    public AgeRating getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(AgeRating ageRating) {
        this.ageRating = ageRating;
    }

    public String getAgeRatingGuide() {
        return ageRatingGuide;
    }

    public void setAgeRatingGuide(String ageRatingGuide) {
        this.ageRatingGuide = ageRatingGuide;
    }

    public Subtype getSubtype() {
        return subtype;
    }

    public void setSubtype(Subtype subtype) {
        this.subtype = subtype;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(Integer episodeCount) {
        this.episodeCount = episodeCount;
    }

    public Integer getEpisodeLength() {
        return episodeLength;
    }

    public void setEpisodeLength(Integer episodeLength) {
        this.episodeLength = episodeLength;
    }

    public String getYoutubeVideoId() {
        return youtubeVideoId;
    }

    public void setYoutubeVideoId(String youtubeVideoId) {
        this.youtubeVideoId = youtubeVideoId;
    }

    public ShowType getShowType() {
        return showType;
    }

    public void setShowType(ShowType showType) {
        this.showType = showType;
    }

    public Boolean getNsfw() {
        return nsfw;
    }

    public void setNsfw(Boolean nsfw) {
        this.nsfw = nsfw;
    }

    public static class Title {
        private String canonicalTitle;
        private List<String> abbreviatedTitles;
    }

    public static class RatingFrequencies {
        private List<Integer> frequencies;
    }

    public static enum AgeRating {
        G, PG, R, R18
    }

    public static enum Subtype {
        TV, OVA, Movie, Special, ONA, Music
    }

    public static enum Status {
        FINISHED, CURRENT, TBA, UNRELEASED, UPCOMING
    }

    public static enum ShowType {
        ANIME, MANGA
    }
}
