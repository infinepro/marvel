package com.marvel.services;

import com.marvel.api.v1.converters.CharacterDtoToCharacterConverter;
import com.marvel.api.v1.converters.CharacterToCharacterDtoConverter;
import com.marvel.api.v1.converters.ComicToComicDtoConverter;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ModelDataContainer;
import com.marvel.domain.MarvelCharacter;
import com.marvel.exceptions.BadParametersException;
import com.marvel.exceptions.CharacterNotFoundException;
import com.marvel.exceptions.ComicNotFoundException;
import com.marvel.exceptions.NotValidCharacterParametersException;
import com.marvel.repositories.CharacterRepository;
import com.marvel.repositories.ComicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.marvel.services.ModelHelperService.MINUS_ONE;

@Slf4j
@Service
public class CharacterServiceImpl implements CharacterService, DateHelperService {

    private final CharacterRepository characterRepository;
    private final CharacterToCharacterDtoConverter characterToDtoConverter;
    private final ComicToComicDtoConverter comicToDtoConverter;
    private final ComicRepository comicRepository;
    private final CharacterDtoToCharacterConverter dtoToCharacterConverter;

    public CharacterServiceImpl(CharacterRepository characterRepository,
                                CharacterToCharacterDtoConverter characterToDtoConverter,
                                ComicToComicDtoConverter comicToDtoConverter,
                                ComicRepository comicRepository,
                                CharacterDtoToCharacterConverter dtoToCharacterConverter) {
        this.characterRepository = characterRepository;
        this.characterToDtoConverter = characterToDtoConverter;
        this.comicToDtoConverter = comicToDtoConverter;
        this.comicRepository = comicRepository;
        this.dtoToCharacterConverter = dtoToCharacterConverter;
    }


    private Page<MarvelCharacter> getCharactersPageByModel(QueryCharacterModel model) {

        Sort sort;

        if (model.getOrderBy().equals("name"))
            sort = Sort.by("name").ascending();
        else if (model.getOrderBy().equals("-name"))
            sort = Sort.by("name").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified").ascending();
        else
            sort = Sort.by("modified").descending();

        Pageable pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        Page<MarvelCharacter> characters;
        try {
            if (model.getComicId().equals(MINUS_ONE)) {
                characters = characterRepository.findAllByModifiedDateOrdered(
                        parseStringDateFormatToLocalDateTime(model.getModifiedFrom()),
                        parseStringDateFormatToLocalDateTime(model.getModifiedTo()),
                        pageable);
            } else {
                characters = characterRepository.findAllByComicIdAndModifiedDateOrdered(
                        model.getComicId(),
                        parseStringDateFormatToLocalDateTime(model.getModifiedFrom()),
                        parseStringDateFormatToLocalDateTime(model.getModifiedTo()),
                        pageable);
            }
        } catch (DateTimeParseException e) {
            throw new BadParametersException("Bad parameter, the date must be in the format: " + DATE_FORMAT);
        }

        return characters;
    }

    @Override
    @Transactional
    public ModelDataContainer<MarvelCharacterDTO> getCharacters(QueryCharacterModel model) {
        try {
            List<MarvelCharacterDTO> charactersDto = getCharactersPageByModel(model)
                    .stream()
                    .peek(System.out::println)
                    .map(characterToDtoConverter::convert)
                    .collect(Collectors.toList());

            if (charactersDto.isEmpty())
                throw new CharacterNotFoundException("Characters not found");

            return new ModelDataContainer<MarvelCharacterDTO>()
                    .setResults(charactersDto)
                    .setCount(charactersDto.size())
                    .setNumberPage(model.getNumberPage())
                    .setPageSize(model.getPageSize());
        } catch (Exception e) {
            throw new CharacterNotFoundException("Characters not found");
        }
    }

    @Override
    @Transactional
    public ModelDataContainer<MarvelCharacterDTO> getCharacterById(Long id) {
        Optional<MarvelCharacter> optionalCharacter = characterRepository.findById(id);

        if (optionalCharacter.isPresent()) {
            List<MarvelCharacterDTO> list = new ArrayList<>();
            list.add(characterToDtoConverter.convert(optionalCharacter.get()));
            ModelDataContainer<MarvelCharacterDTO> model = new ModelDataContainer<>();
            model.setResults(list);

            return model;
        } else
            throw new ComicNotFoundException("Character with id:" + id + " not found");

    }

    @Override
    @Transactional
    public ModelDataContainer<ComicDTO> getComicsByCharacterId(Long characterId) {
        Optional<MarvelCharacter> optionalCharacter = characterRepository.findById(characterId);

        if (optionalCharacter.isPresent()) {
            ModelDataContainer<ComicDTO> model = new ModelDataContainer<>();
            model.setResults(
                    optionalCharacter.get().getComics()
                            .stream()
                            .map(comicToDtoConverter::convert)
                            .collect(Collectors.toList()));

            return model;
        } else
            throw new ComicNotFoundException("Character with id:" + characterId + " not found");
    }

    @Override
    @Transactional
    public ModelDataContainer<MarvelCharacterDTO> saveMarvelCharacterDto(MarvelCharacterDTO model) {

        try {
            if (model == null) {
                throw new IllegalArgumentException();
            }

            model.setId(null);
            MarvelCharacter result = characterRepository
                    .saveAndFlush(dtoToCharacterConverter.convert(model).setModified(LocalDateTime.now()));
            ModelDataContainer<MarvelCharacterDTO> responseModel = new ModelDataContainer<>();
            responseModel.getResults().add(characterToDtoConverter.convert(result));

            return responseModel;
        } catch (IllegalArgumentException e) {
            throw new NotValidCharacterParametersException("Not valid data for MarvelCharacter");
        }
    }

    @Override
    @Transactional
    public ModelDataContainer<MarvelCharacterDTO> updateMarvelCharacterById(Long characterId,
                                                                            MarvelCharacterDTO model) {
        try {
            if (model == null)
                throw new IllegalArgumentException();

            model.setId(characterId);
            MarvelCharacter result = characterRepository
                    .saveAndFlush(dtoToCharacterConverter.convert(model).setModified(LocalDateTime.now()));
            ModelDataContainer<MarvelCharacterDTO> responseModel = new ModelDataContainer<>();
            responseModel.getResults().add(characterToDtoConverter.convert(result));

            return responseModel;
        } catch (IllegalArgumentException e) {
            throw new NotValidCharacterParametersException("Not valid data for MarvelCharacter");
        }
    }

    @Override
    @Transactional
    public void deleteCharacterById(Long characterId) {
        Optional<MarvelCharacter> optionalCharacter = characterRepository.findById(characterId);

        if (optionalCharacter.isPresent()) {
            characterRepository.delete(optionalCharacter.get());
        } else
            throw new CharacterNotFoundException("character with id: " + characterId + "not found");
    }
}
