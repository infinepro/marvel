package com.marvel.api.v1.converters;

import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.domain.Comic;
import com.marvel.domain.MarvelCharacter;
import com.marvel.services.DateHelperService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CharacterDtoToCharacterConverter implements Converter<MarvelCharacterDTO, MarvelCharacter>, DateHelperService {

    @Override
    @Nullable
    public MarvelCharacter convert(MarvelCharacterDTO characterDto) {
        if (characterDto == null)
            return null;

        final MarvelCharacter character = new MarvelCharacter();

        final List<Comic> comicList = characterDto.getComicsId()
                .stream()
                .map(x -> new Comic().setId(x))
                .collect(Collectors.toList());

        character
                .setId(characterDto.getId())
                .setName(characterDto.getName())
                .setDescription(characterDto.getDescription())
                .setModified(parseStringDateFormatToLocalDateTime(characterDto.getModified()))
                .setThumbnail(characterDto.getThumbnail())
                .setFullImage(characterDto.getFullImage())
                .setComics(comicList);

        return character;
    }
}
