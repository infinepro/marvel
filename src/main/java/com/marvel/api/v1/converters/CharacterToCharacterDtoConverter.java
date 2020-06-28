package com.marvel.api.v1.converters;

import com.marvel.domain.Character;
import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterToCharacterDtoConverter implements Converter<Character, CharacterDTO> {

    private final ComicToComicDtoConverter comicToComicDtoConverter;

    public CharacterToCharacterDtoConverter(ComicToComicDtoConverter comicToComicDtoConverter) {
        this.comicToComicDtoConverter = comicToComicDtoConverter;
    }

    @Override
    @Nullable
    public CharacterDTO convert(Character character) {
        if (character == null)
            return null;

        final Set<ComicDTO> comicDtoList = character.getComicList()
                .stream()
                .map(comicToComicDtoConverter::convert)
                .collect(Collectors.toSet());

        final CharacterDTO characterDTO = new CharacterDTO();

        characterDTO
                .setId(character.getId().toString())
                .setName(character.getName())
                .setDescription(character.getDescription())
                .setModified(character.getModified())
                .setThumbnail(character.getThumbnail())
                .setFullImage(character.getFullImage())
                .setComicList(comicDtoList);

        return characterDTO;
    }
}
