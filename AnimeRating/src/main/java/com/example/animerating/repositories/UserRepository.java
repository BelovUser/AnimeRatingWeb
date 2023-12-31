package com.example.animerating.repositories;

import com.example.animerating.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User,Long> {
    Optional<User> findByUsername(String name);
}
