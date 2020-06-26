package com.marvel.converters;

import com.marvel.domain.ComicDate;
import com.marvel.dto.ComicDateDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ComicDateToComicDateDtoConverter implements Converter<ComicDate, ComicDateDTO> {

    @Override
    @Nullable
    public ComicDateDTO convert(ComicDate comicDate) {
        if (comicDate == null)
            return null;

        return new ComicDateDTO()
                .setId(comicDate.getId().toString())
                .setDate(comicDate.getDate())
                .setType(comicDate.getType());
    }
}
