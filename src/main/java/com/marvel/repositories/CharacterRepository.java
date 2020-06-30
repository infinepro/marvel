package com.marvel.repositories;

import com.marvel.domain.MarvelCharacter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface CharacterRepository extends JpaRepository<MarvelCharacter, Long> {

    Optional<MarvelCharacter> findById(Long id);

    Optional<MarvelCharacter> findByName(String name);

    @Query("SELECT m FROM MarvelCharacter m " +
            "WHERE m.modified >= :modifiedFrom AND m.modified <= :modifiedTo ")
    Page<MarvelCharacter> findAllByModifiedDateOrderByNameAsc(@Param("modifiedFrom") LocalDateTime modifiedFrom,
                                                              @Param("modifiedTo") LocalDateTime modifiedTo,
                                                              Pageable pageable);
/*

    @Query("SELECT m FROM MarvelCharacter m " +
            "WHERE m.modified >= :modifiedFrom AND m.modified <= :modifiedTo " +
            "ORDER BY m.name desc")
    Page<MarvelCharacter> findAllByModifiedDateOrderByNameDesc(@Param("modifiedFrom") LocalDateTime modifiedFrom,
                                                           @Param("modifiedTo") LocalDateTime modifiedTo,
                                                           Pageable pageable);

    @Query("SELECT m FROM MarvelCharacter m " +
            "WHERE m.modified >= :modifiedFrom AND m.modified <= :modifiedTo " +
            "ORDER BY m.modified ASC")
    Page<MarvelCharacter> findAllByModifiedDateOrderByModifiedAsc(@Param("modifiedFrom") LocalDateTime modifiedFrom,
                                                              @Param("modifiedTo") LocalDateTime modifiedTo,
                                                              Pageable pageable);

    @Query("SELECT m FROM MarvelCharacter m " +
            "WHERE m.modified >= :modifiedFrom AND m.modified <= :modifiedTo " +
            "ORDER BY m.modified desc")
    Page<MarvelCharacter> findAllByModifiedDateOrderByModifiedDesc(@Param("modifiedFrom") LocalDateTime modifiedFrom,
                                                               @Param("modifiedTo") LocalDateTime modifiedTo,
                                                               Pageable pageable);
*/

}
