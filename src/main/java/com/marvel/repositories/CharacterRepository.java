package com.marvel.repositories;

import com.marvel.domain.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CharacterRepository extends MongoRepository<Character, Long> {

    Page<Character> findAll(Pageable pageable);

    Optional<Character> findById(Long id);

    Optional<Character> findByName(String name);
}
