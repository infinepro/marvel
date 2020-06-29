package com.marvel.services;

import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.ResponseDataContainerModel;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.domain.Character;

import java.util.List;

public interface CharacterService {

    ResponseDataContainerModel<Character> getCharacters(QueryCharacterModel model);

    CharacterDTO getCharacterById(Long id);

    List<ComicDTO> getComicsByCharacterId(Long characterId);
}
