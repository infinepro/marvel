package com.marvel.converters;

import com.marvel.domain.ComicDate;
import com.marvel.dto.ComicDateDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ComicDateDtoToComicDateConverter implements Converter<ComicDateDTO, ComicDate> {

    @Override
    @Nullable
    public ComicDate convert(ComicDateDTO comicDateDTO) {
        if (comicDateDTO == null)
            return null;

        return new ComicDate()
                .setId(Long.valueOf(comicDateDTO.getId()))
                .setDate(comicDateDTO.getDate())
                .setType(comicDateDTO.getType());
    }
}
