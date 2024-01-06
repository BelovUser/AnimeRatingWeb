package com.example.animerating.repositories;

import com.example.animerating.models.Anime;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepository extends ListCrudRepository<Anime,Long> {
    List<Anime> findTop6ByOrderByAverageRattingDesc();
    List<Anime> findAllByOrderByAverageRattingDesc();

    Optional<Anime> findFirstByOrderByAverageRattingDesc();

    Optional<Anime> findByTitleJp(String titleJp);
    List<Anime> findAllByCurrentlyWatchingTrue();
}
