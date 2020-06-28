package com.marvel.api.converters;

import com.marvel.domain.Comic;
import com.marvel.api.model.ComicDTO;
import com.marvel.api.model.ComicDateDTO;
import com.marvel.api.model.ComicPriceDTO;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

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
        if (comic == null)
            return null;

        final List<ComicPriceDTO> pricesDto = comic.getPrices()
                .stream()
                .map(comicPriceToComicPriceDtoConverter::convert)
                .collect(Collectors.toList());

        final List<ComicDateDTO> datesDto = comic.getDates()
                .stream()
                .map(comicDateToComicDateDtoConverter::convert)
                .collect(Collectors.toList());

        final ComicDTO comicDto = new ComicDTO();

        comicDto
                .setId(comic.getId().toString())
                .setTitle(comic.getTitle())
                .setDescription(comic.getDescription())
                .setModified(comic.getModified())
                .setFormat(comic.getFormat())
                .setPageCount(comic.getPageCount())
                .setThumbnail(comic.getThumbnail())
                .setFullImage(comic.getFullImage())
                .setDates(datesDto)
                .setPrices(pricesDto);

        return comicDto;
    }
}
