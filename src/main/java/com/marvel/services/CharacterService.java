package com.marvel.services;

import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;

import java.util.List;

public interface CharacterService {

    ResponseDataContainerModel<MarvelCharacterDTO> getCharacters(QueryCharacterModel model);

    MarvelCharacterDTO getCharacterById(Long id);

    List<ComicDTO> getComicsByCharacterId(Long characterId);

}
