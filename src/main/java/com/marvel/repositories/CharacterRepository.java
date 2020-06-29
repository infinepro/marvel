package com.marvel.repositories;

import com.marvel.domain.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends MongoRepository<Character, Long> {

    Page<Character> findAllByNameAndModifiedAfter(String name, Long modifiedSince, Pageable pageable);

    Page<Character> findAllByModifiedAfter(Long modifiedSince, Pageable pageable);

    Optional<Character> findById(Long id);

    List<Character> findCharactersByNameAndDescription(Pageable page, String name);
}
