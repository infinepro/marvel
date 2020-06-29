package com.marvel.repositories;

import com.marvel.domain.Character;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CharacterRepository extends MongoRepository<Character, Long> {

    @Query("{name : ?0, comicList.id : ?1, modified : ?2}")
    Page<Character> findAllByNameAndComicIdAndModifiedDateSince(String name, Long id,  Long modifiedSince, Pageable pageable);

    Optional<Character> findById(Long id);

    List<Character> findCharactersByNameAndDescription(Pageable page, String name);
}
