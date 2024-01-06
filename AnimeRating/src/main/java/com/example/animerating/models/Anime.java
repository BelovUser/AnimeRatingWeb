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
    private Integer artStyleRatting;
    private Integer animationRatting;
    private Integer charactersRatting;
    private Integer storyRatting;
    private Double averageRatting;
    private Boolean currentlyWatching = false;

    public Boolean getCurrentlyWatching() {
        return currentlyWatching;
    }

    public void setCurrentlyWatching(Boolean currentlyWatching) {
        this.currentlyWatching = currentlyWatching;
    }

    public Double getAverageRatting() {
        List<Integer> animeRattings = Arrays.asList(
                Objects.requireNonNullElse(animationRatting, 0),
                Objects.requireNonNullElse(artStyleRatting, 0),
                Objects.requireNonNullElse(storyRatting, 0),
                Objects.requireNonNullElse(charactersRatting, 0)
        );

        System.out.println("Ratings: " + animeRattings);


        return animeRattings.stream()
                .mapToInt(Integer::intValue)
                .average()
                .orElse(Double.NaN);
    }

    public void setAverageRatting(Double averageRatting) {
        this.averageRatting = averageRatting;
    }

    public Integer getArtStyleRatting() {
        return artStyleRatting;
    }

    public void setArtStyleRatting(Integer artStyleRatting) {
        this.artStyleRatting = artStyleRatting;
    }

    public Integer getAnimationRatting() {
        return animationRatting;
    }

    public void setAnimationRatting(Integer animationRatting) {
        this.animationRatting = animationRatting;
    }

    public Integer getCharactersRatting() {
        return charactersRatting;
    }

    public void setCharactersRatting(Integer charactersRatting) {
        this.charactersRatting = charactersRatting;
    }

    public Integer getStoryRatting() {
        return storyRatting;
    }

    public void setStoryRatting(Integer storyRatting) {
        this.storyRatting = storyRatting;
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
