package com.marvel.converters;

import com.marvel.domain.Comic;
import com.marvel.domain.Character;
import com.marvel.dto.CharacterDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
public class CharacterDtoToCharacterConverter implements Converter<CharacterDTO, Character> {

    private final ComicDtoToComicConverter comicDtoToComicConverter;

    public CharacterDtoToCharacterConverter(ComicDtoToComicConverter comicDtoToComicConverter) {
        this.comicDtoToComicConverter = comicDtoToComicConverter;
    }

    @Override
    @Nullable
    public Character convert(CharacterDTO characterDto) {
        if (characterDto == null)
            return null;

        final Set<Comic> comicList = characterDto.getComicList()
                .stream()
                .map(comicDtoToComicConverter::convert)
                .collect(Collectors.toSet());

        final Character character = new Character();

        character
                .setId(Long.valueOf(characterDto.getId()))
                .setName(characterDto.getName())
                .setDescription(characterDto.getDescription())
                .setModified(characterDto.getModified())
                .setThumbnail(characterDto.getThumbnail())
                .setFullImage(characterDto.getFullImage())
                .setComicList(comicList);

        return character;
    }
}
