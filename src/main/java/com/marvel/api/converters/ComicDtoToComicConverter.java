package com.marvel.api.converters;

import com.marvel.api.model.ComicDateDTO;
import com.marvel.api.model.ComicPriceDTO;
import com.marvel.domain.Comic;
import com.marvel.api.model.ComicDTO;
import com.marvel.domain.ComicDate;
import com.marvel.domain.ComicPrice;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ComicDtoToComicConverter implements Converter<ComicDTO, Comic> {

    private final ComicDateDtoToComicDateConverter comicDateDtoToComicDateConverter;
    private final ComicPriceDtoToComicPriceConverter comicPriceDtoToComicPriceConverterr;

    public ComicDtoToComicConverter(ComicDateDtoToComicDateConverter comicDateDtoToComicDateConverter,
                                    ComicPriceDtoToComicPriceConverter comicPriceDtoToComicPriceConverterr) {
        this.comicDateDtoToComicDateConverter = comicDateDtoToComicDateConverter;
        this.comicPriceDtoToComicPriceConverterr = comicPriceDtoToComicPriceConverterr;
    }

    @Override
    @Nullable
    public Comic convert(ComicDTO comicDto) {
        if (comicDto == null)
            return null;

        final List<ComicPrice> prices = comicDto.getPrices()
                .stream()
                .map(comicPriceDtoToComicPriceConverterr::convert)
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
                .setPrices(prices);

        return comic;
    }
}
