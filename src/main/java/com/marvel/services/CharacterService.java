package com.marvel.services;

import com.marvel.api.v1.model.DataContainerModel;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.domain.Character;

public interface CharacterService {

    DataContainerModel<Character> getCharacters(QueryCharacterModel model);
}
