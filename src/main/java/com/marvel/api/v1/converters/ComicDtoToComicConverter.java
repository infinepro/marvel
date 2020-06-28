package com.marvel.api.v1.converters;

import com.marvel.api.v1.model.ComicDTO;
import com.marvel.domain.Character;
import com.marvel.domain.Comic;
import com.marvel.domain.ComicDate;
import com.marvel.domain.ComicPrice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComicDtoToComicConverter implements Converter<ComicDTO, Comic> {

    private final CharacterDtoToCharacterConverter characterDtoToCharacterConverter;
    private final ComicDateDtoToComicDateConverter comicDateDtoToComicDateConverter;
    private final ComicPriceDtoToComicPriceConverter comicPriceDtoToComicPriceConverter;

    public ComicDtoToComicConverter(CharacterDtoToCharacterConverter characterDtoToCharacterConverter,
                                    ComicDateDtoToComicDateConverter comicDateDtoToComicDateConverter,
                                    ComicPriceDtoToComicPriceConverter comicPriceDtoToComicPriceConverter) {
        this.characterDtoToCharacterConverter = characterDtoToCharacterConverter;
        this.comicDateDtoToComicDateConverter = comicDateDtoToComicDateConverter;
        this.comicPriceDtoToComicPriceConverter = comicPriceDtoToComicPriceConverter;
    }

    @Override
    @Nullable
    public Comic convert(ComicDTO comicDto) {
        if (comicDto == null)
            return null;

        final List<Character> characters = comicDto.getCharacters()
                .stream()
                .map(characterDtoToCharacterConverter::convert)
                .collect(Collectors.toList());

        final List<ComicPrice> prices = comicDto.getPrices()
                .stream()
                .map(comicPriceDtoToComicPriceConverter::convert)
                .collect(Collectors.toList());

        final List<ComicDate> dates = comicDto.getDates()
                .stream()
                .map(comicDateDtoToComicDateConverter::convert)
                .collect(Collectors.toList());

        final Comic comic = new Comic();

        comic
                .setId(Long.valueOf(comicDto.getId()))
                .setTitle(comicDto.getTitle())
                .setDescription(comicDto.getDescription())
                .setModified(comicDto.getModified())
                .setFormat(comicDto.getFormat())
                .setPageCount(comicDto.getPageCount())
                .setThumbnail(comicDto.getThumbnail())
                .setFullImage(comicDto.getFullImage())
                .setDates(dates)
                .setPrices(prices)
                .setCharacters(characters);

        return comic;
    }
}
