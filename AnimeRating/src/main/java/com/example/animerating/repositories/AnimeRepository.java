package com.example.animerating.repositories;

import com.example.animerating.models.Anime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnimeRepository extends CrudRepository<Anime,Long> {
}
