package com.marvel.repositories;

import com.marvel.domain.Comic;
import org.springframework.data.repository.CrudRepository;

public interface ComicRepository extends CrudRepository<Comic, Long> {


}

