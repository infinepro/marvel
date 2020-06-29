package com.marvel.api.v1.converters;

import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.domain.Character;
import com.marvel.services.DateServiceHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CharacterToCharacterDtoConverter implements Converter<Character, CharacterDTO>, DateServiceHelper {

    @Override
    @Nullable
    public CharacterDTO convert(Character character) {
        if (character == null)
            return null;

        final CharacterDTO characterDTO = new CharacterDTO();

        characterDTO
                .setId(character.getId().toString())
                .setName(character.getName())
                .setDescription(character.getDescription())
                .setModified(parseLongDateFormatToString(character.getModified()))
                .setThumbnail(character.getThumbnail())
                .setFullImage(character.getFullImage());

        return characterDTO;
    }
}
