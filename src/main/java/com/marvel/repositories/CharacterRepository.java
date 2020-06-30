package com.marvel.repositories;

import com.marvel.domain.MarvelCharacter;
import org.springframework.data.repository.CrudRepository;

public interface CharacterRepository extends CrudRepository<MarvelCharacter, Long> {



}
