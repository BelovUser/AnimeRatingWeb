package com.example.animerating.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Anime {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Long releaseDate;
    @OneToMany(mappedBy = "anime")
    private List<AnimeCharacter> casting;
    @ManyToMany(mappedBy = "ratedAnime")
    private List<User> users;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(Long releaseDate) {
        this.releaseDate = releaseDate;
    }

    public List<AnimeCharacter> getCasting() {
        return casting;
    }

    public void setCasting(List<AnimeCharacter> casting) {
        this.casting = casting;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
