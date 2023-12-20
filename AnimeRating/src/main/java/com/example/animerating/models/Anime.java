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
    @ManyToMany(mappedBy = "anime")
    private List<User> users;
}
