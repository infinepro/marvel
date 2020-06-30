package com.marvel.repositories;

import com.marvel.domain.Comic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface ComicRepository extends JpaRepository<Comic, Long> {


}

