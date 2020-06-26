package com.marvel.converters;

import com.marvel.domain.Comic;
import com.marvel.dto.ComicDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ComicToComicDtoConverter implements Converter<Comic, ComicDTO> {

    private final ComicDateToComicDateDtoConverter comicDateToComicDateDtoConverter;
    private final ComicPriceToComicPriceDtoConverter comicPriceToComicPriceDtoConverter;

    public ComicToComicDtoConverter(ComicDateToComicDateDtoConverter comicDateToComicDateDtoConverter,
                                    ComicPriceToComicPriceDtoConverter comicPriceToComicPriceDtoConverter) {
        this.comicDateToComicDateDtoConverter = comicDateToComicDateDtoConverter;
        this.comicPriceToComicPriceDtoConverter = comicPriceToComicPriceDtoConverter;
    }

    @Override
    @Nullable
    public ComicDTO convert(Comic comic) {
        return null;
    }
}
