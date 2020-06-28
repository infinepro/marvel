package com.marvel.repositories;

import com.marvel.domain.Comic;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface ComicRepository extends MongoRepository<Comic, Long> {

    Optional<Comic> findById(Long id);

    Optional<Comic> findByTitle(String title);


}

