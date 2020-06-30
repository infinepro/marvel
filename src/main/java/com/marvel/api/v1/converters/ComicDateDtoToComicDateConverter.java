package com.marvel.api.v1.converters;

import com.marvel.domain.ComicDate;
import com.marvel.api.v1.model.ComicDateDTO;
import com.marvel.services.DateServiceHelper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

@Component
public class ComicDateDtoToComicDateConverter implements Converter<ComicDateDTO, ComicDate>, DateServiceHelper {

    @Override
    @Nullable
    public ComicDate convert(ComicDateDTO comicDateDTO) {
        if (comicDateDTO == null)
            return null;

        return new ComicDate()
                .setId(Long.valueOf(comicDateDTO.getId()))
                .setDate(parseStringDateFormatToLocalDateTime(comicDateDTO.getDate()))
                .setType(comicDateDTO.getType());
    }
}
