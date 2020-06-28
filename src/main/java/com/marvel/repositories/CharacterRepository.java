package com.marvel.repositories;

import com.marvel.domain.Character;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends MongoRepository<Character, Long> {

    Optional<Character> findById(Long id);

    List<Character> findAllById(Long id, Pageable pageable);

}
