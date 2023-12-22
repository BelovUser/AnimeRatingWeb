package com.example.animerating.services;

import com.example.animerating.models.Anime;
import com.example.animerating.repositories.AnimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

    private final AnimeRepository animeRepository;


    @Autowired
    public AnimeService(AnimeRepository animeRepository) {
        this.animeRepository = animeRepository;
    }


    public Optional<Anime> getById(Long id) {
        return animeRepository.findById(id);
    }

    public List<Anime> getAll() {
        return (List<Anime>) animeRepository.findAll();
    }

    public void save(Anime anime) {
        animeRepository.save(anime);
    }
}
