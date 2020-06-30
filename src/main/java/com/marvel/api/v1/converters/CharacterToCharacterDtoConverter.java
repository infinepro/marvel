package com.marvel.api.v1.converters;

import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.domain.MarvelCharacter;
import com.marvel.services.DateServiceHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CharacterToCharacterDtoConverter implements Converter<MarvelCharacter, MarvelCharacterDTO>, DateServiceHelper {

    @Override
    @Nullable
    public MarvelCharacterDTO convert(MarvelCharacter character) {
        if (character == null)
            return null;

        final MarvelCharacterDTO characterDTO = new MarvelCharacterDTO();

        characterDTO
                .setId(character.getId().toString())
                .setName(character.getName())
                .setDescription(character.getDescription())
                .setModified(parseLocalDateTimeFormatToString(character.getModified()))
                .setThumbnail(character.getThumbnail())
                .setFullImage(character.getFullImage());

        return characterDTO;
    }
}
