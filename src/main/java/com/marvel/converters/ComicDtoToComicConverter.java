package com.marvel.converters;

import com.marvel.domain.Comic;
import com.marvel.dto.ComicDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ComicDtoToComicConverter implements Converter<ComicDTO, Comic> {
    @Override
    public Comic convert(ComicDTO comicDTO) {
        return null;
    }
}
