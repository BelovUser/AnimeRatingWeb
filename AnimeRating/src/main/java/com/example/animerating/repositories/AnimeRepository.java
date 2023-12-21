package com.example.animerating.repositories;

import com.example.animerating.models.Anime;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends ListCrudRepository<Anime,Long> {
}
