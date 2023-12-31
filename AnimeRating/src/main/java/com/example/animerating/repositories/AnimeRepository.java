package com.example.animerating.repositories;

import com.example.animerating.models.Anime;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepository extends ListCrudRepository<Anime,Long> {
    List<Anime> findAllTop10OrderByAverageRatting();
    Optional<Anime> findTopByAverageRatting();
}
