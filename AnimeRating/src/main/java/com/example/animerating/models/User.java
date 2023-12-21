package com.example.animerating.models;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private Role role = Role.USER;
    private String email;
    private String password;
    @ManyToMany
    private List<Anime> anime;
}
