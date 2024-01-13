package com.example.animerating.models;


import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String titleEn;
    private String titleJp;
    private String releaseDate;
    private Boolean seen;
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String synopsis;
    private String episodeCount;
    private String posterUrl;
    private Integer artStyleRating;
    private Integer animationRating;
    private Integer charactersRating;
    private Integer storyRating;
    private Double averageRating;
    private Boolean currentlyWatching = false;

    public Boolean getCurrentlyWatching() {
        return currentlyWatching;
    }

    public void setCurrentlyWatching(Boolean currentlyWatching) {
        this.currentlyWatching = currentlyWatching;
    }

    public Double getAverageRating() {
        List<Integer> animeRatings = Arrays.asList(
                Objects.requireNonNullElse(animationRating, 0),
                Objects.requireNonNullElse(artStyleRating, 0),
                Objects.requireNonNullElse(storyRating, 0),
                Objects.requireNonNullElse(charactersRating, 0)
        );

        System.out.println("Ratings: " + animeRatings);


        return animeRatings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);
    }

    public void setAverageRating(Double averageRatting) {
        this.averageRating = averageRatting;
    }

    public Integer getArtStyleRating() {
        return artStyleRating;
    }

    public void setArtStyleRating(Integer artStyleRatting) {
        this.artStyleRating = artStyleRatting;
    }

    public Integer getAnimationRating() {
        return animationRating;
    }

    public void setAnimationRating(Integer animationRatting) {
        this.animationRating = animationRatting;
    }

    public Integer getCharactersRating() {
        return charactersRating;
    }

    public void setCharactersRating(Integer charactersRatting) {
        this.charactersRating = charactersRatting;
    }

    public Integer getStoryRating() {
        return storyRating;
    }

    public void setStoryRating(Integer storyRatting) {
        this.storyRating = storyRatting;
    }

    public String getPosterUrl() {
        return posterUrl;
    }

    public void setPosterUrl(String posterUrl) {
        this.posterUrl = posterUrl;
    }

    @ManyToMany(mappedBy = "anime")
    private List<User> users = new ArrayList<>();
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitleEn() {
        if(titleEn.isEmpty()){
            return titleJp;
        }
        return titleEn;
    }

    public void setTitleEn(String titleEn) {
        this.titleEn = titleEn;
    }

    public String getTitleJp() {
        return titleJp;
    }

    public void setTitleJp(String titleJp) {
        this.titleJp = titleJp;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getEpisodeCount() {
        return episodeCount;
    }

    public void setEpisodeCount(String episodeCount) {
        this.episodeCount = episodeCount;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
