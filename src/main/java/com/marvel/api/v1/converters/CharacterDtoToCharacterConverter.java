package com.marvel.api.v1.converters;

import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.domain.MarvelCharacter;
import com.marvel.services.DateServiceHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CharacterDtoToCharacterConverter implements Converter<MarvelCharacterDTO, MarvelCharacter>, DateServiceHelper {

    @Override
    @Nullable
    public MarvelCharacter convert(MarvelCharacterDTO characterDto) {
        if (characterDto == null)
            return null;

        final MarvelCharacter character = new MarvelCharacter();

        character
                .setId(Long.valueOf(characterDto.getId()))
                .setName(characterDto.getName())
                .setDescription(characterDto.getDescription())
                .setModified(parseStringDateFormatToLong(characterDto.getModified()))
                .setThumbnail(characterDto.getThumbnail())
                .setFullImage(characterDto.getFullImage());

        return character;
    }
}
