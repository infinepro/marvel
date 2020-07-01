package com.marvel.services;

import com.marvel.api.v1.converters.ComicToComicDtoConverter;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.ModelDataContainer;
import com.marvel.api.v1.model.QueryComicModel;
import com.marvel.domain.Comic;
import com.marvel.exceptions.BadParametersException;
import com.marvel.exceptions.ComicNotFoundException;
import com.marvel.repositories.ComicRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ComicServiceImpl implements ComicService, DateHelperService {

    private final ComicRepository comicRepository;
    private final ComicToComicDtoConverter comicToDtoConverter;

    public ComicServiceImpl(ComicRepository comicRepository, ComicToComicDtoConverter comicToDtoConverter) {
        this.comicRepository = comicRepository;
        this.comicToDtoConverter = comicToDtoConverter;
    }

    private Page<Comic> getComicPageByModel(QueryComicModel model) {
        Sort sort;

        if (model.getOrderBy().equals("title"))
            sort = Sort.by("title").ascending();
        else if (model.getOrderBy().equals("-title"))
            sort = Sort.by("title").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified").ascending();
        else
            sort = Sort.by("modified").descending();

        Pageable pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        Page<Comic> comics;
        try {
            if (model.getTitle() == null) {
                comics = comicRepository.findAllBetweenDatesOrdered(
                        parseStringDateFormatToLocalDateTime(model.getDateFrom()),
                        parseStringDateFormatToLocalDateTime(model.getDateTo()),
                        pageable);
            } else {
                comics = comicRepository.findAllByTitleAndBetweenDatesOrdered(
                        parseStringDateFormatToLocalDateTime(model.getDateFrom()),
                        parseStringDateFormatToLocalDateTime(model.getDateTo()),
                        model.getTitle(),
                        pageable);
            }
        } catch (DateTimeParseException e) {
            throw new BadParametersException("Bad parameter, the date must be in the format: " + DATE_FORMAT);
        }

        return comics;
    }

    @Override
    @Transactional
    public ModelDataContainer<ComicDTO> findComics(QueryComicModel model) {
        try {
            List<ComicDTO> comics = getComicPageByModel(model)
                    .stream()
                    .map(comicToDtoConverter::convert)
                    .collect(Collectors.toList());

            if (comics.size() == 0)
                throw new ComicNotFoundException("Comics not found");

            ModelDataContainer<ComicDTO> responseModel = new ModelDataContainer<>();
            responseModel.setResults(comics);

            return responseModel;
        } catch (Exception e) {
            throw new ComicNotFoundException("Comics not found");
        }
    }

    @Override
    @Transactional
    public ModelDataContainer<ComicDTO> findComicById(Long comicId) {
        Optional<Comic> optionalComic = comicRepository.findById(comicId);

        if (optionalComic.isPresent()) {
            ModelDataContainer<ComicDTO> responseModel = new ModelDataContainer<>();
            responseModel.getResults().add(comicToDtoConverter.convert(optionalComic.get()));

            return responseModel;
        } else
            throw new ComicNotFoundException("Comic with id:" + comicId + " not found");
    }
}
