package com.marvel.services;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;

public interface CharacterService {

    ResponseDataContainerModel<MarvelCharacterDTO> getCharacters(QueryCharacterModel model);

    ResponseDataContainerModel<MarvelCharacterDTO> getCharacterById(Long id);

    ResponseDataContainerModel<ComicDTO> getComicsByCharacterId(Long characterId);

    ResponseDataContainerModel<MarvelCharacterDTO> saveMarvelCharacterDto(MarvelCharacterDTO model);
}
