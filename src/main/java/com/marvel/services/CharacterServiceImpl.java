package com.marvel.services;

import com.marvel.api.v1.converters.CharacterDtoToCharacterConverter;
import com.marvel.api.v1.converters.CharacterToCharacterDtoConverter;
import com.marvel.api.v1.converters.ComicToComicDtoConverter;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;
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

import javax.validation.constraints.NotNull;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.marvel.services.ModelService.MINUS_ONE;

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


    private Page<MarvelCharacter> getCharactersByModel(QueryCharacterModel model) {

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
    public ResponseDataContainerModel<MarvelCharacterDTO> getCharacters(QueryCharacterModel model) {
        try {
            List<MarvelCharacterDTO> charactersDto = getCharactersByModel(model)
                    .stream()
                    .peek(System.out::println)
                    .map(characterToDtoConverter::convert)
                    .collect(Collectors.toList());

            if (charactersDto.isEmpty())
                throw new CharacterNotFoundException("Character from comic where ID:" + model.getComicId() + "not found");

            return new ResponseDataContainerModel<MarvelCharacterDTO>()
                    .setResults(charactersDto)
                    .setCount(charactersDto.size())
                    .setNumberPage(model.getNumberPage())
                    .setPageSize(model.getPageSize());
        } catch (Exception e) {
            throw new CharacterNotFoundException("Character from comic where ID:" + model.getComicId() + "not found");
        }

    }

    @Override
    @Transactional
    public ResponseDataContainerModel<MarvelCharacterDTO> getCharacterById(Long id) {
        Optional<MarvelCharacter> optionalCharacter = characterRepository.findById(id);

        if (optionalCharacter.isPresent()) {
            List<MarvelCharacterDTO> list = new ArrayList<>();
            list.add(characterToDtoConverter.convert(optionalCharacter.get()));
            ResponseDataContainerModel<MarvelCharacterDTO> model = new ResponseDataContainerModel<>();
            model.setResults(list);

            return model;
        } else
            throw new ComicNotFoundException("Character with id:" + id + " not found");

    }

    @Override
    @Transactional
    public ResponseDataContainerModel<ComicDTO> getComicsByCharacterId(Long characterId) {
        Optional<MarvelCharacter> optionalCharacter = characterRepository.findById(characterId);

        if (optionalCharacter.isPresent()) {
            ResponseDataContainerModel<ComicDTO> model = new ResponseDataContainerModel<>();
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
    public ResponseDataContainerModel<MarvelCharacterDTO> saveMarvelCharacterDto(MarvelCharacterDTO model) {

        try {
            if (model == null)
                throw new IllegalArgumentException();

            MarvelCharacter result = characterRepository.save(dtoToCharacterConverter.convert(model));
            ResponseDataContainerModel<MarvelCharacterDTO> responseModel = new ResponseDataContainerModel<>();
            responseModel.getResults().add(characterToDtoConverter.convert(result));

            return responseModel;

        } catch (IllegalArgumentException e) {
            throw new NotValidCharacterParametersException("Not valid data for MarvelCharacter");
        }


    }
}
