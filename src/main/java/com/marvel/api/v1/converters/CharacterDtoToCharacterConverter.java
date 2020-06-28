package com.marvel.api.v1.converters;

import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.domain.Character;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class CharacterDtoToCharacterConverter implements Converter<CharacterDTO, Character> {

    @Override
    @Nullable
    public Character convert(CharacterDTO characterDto) {
        if (characterDto == null)
            return null;

        final Character character = new Character();

        character
                .setId(Long.valueOf(characterDto.getId()))
                .setName(characterDto.getName())
                .setDescription(characterDto.getDescription())
                .setModified(characterDto.getModified())
                .setThumbnail(characterDto.getThumbnail())
                .setFullImage(characterDto.getFullImage());

        return character;
    }
}
