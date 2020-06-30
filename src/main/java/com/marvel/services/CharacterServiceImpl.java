package com.marvel.services;

import com.marvel.api.v1.converters.CharacterToCharacterDtoConverter;
import com.marvel.api.v1.converters.ComicToComicDtoConverter;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.MarvelCharacterDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;
import com.marvel.domain.MarvelCharacter;
import com.marvel.exceptions.CharacterNotFoundException;
import com.marvel.repositories.CharacterRepository;
import com.marvel.repositories.ComicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CharacterServiceImpl implements CharacterService, DateServiceHelper {

    private final CharacterRepository characterRepository;
    private final CharacterToCharacterDtoConverter characterToDtoConverter;
    private final ComicToComicDtoConverter comicToDtoConverter;
    private final ComicRepository comicRepository;

    public CharacterServiceImpl(CharacterRepository characterRepository,
                                CharacterToCharacterDtoConverter characterToDtoConverter,
                                ComicToComicDtoConverter comicToDtoConverter,
                                ComicRepository comicRepository) {
        this.characterRepository = characterRepository;
        this.characterToDtoConverter = characterToDtoConverter;
        this.comicToDtoConverter = comicToDtoConverter;
        this.comicRepository = comicRepository;
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

        Page<MarvelCharacter> characters = characterRepository.findAllByModifiedDateOrderByNameAsc(
                parseStringDateFormatToLocalDateTime(model.getModifiedFrom()),
                parseStringDateFormatToLocalDateTime(model.getModifiedTo()),
                pageable);

        return characters;
    }

    @Override
    public ResponseDataContainerModel<MarvelCharacterDTO> getCharacters(QueryCharacterModel model) {

        List<MarvelCharacterDTO> charactersDto = getCharactersByModel(model)
                .stream()
                .peek(System.out::println)
                .map(characterToDtoConverter::convert)
                .collect(Collectors.toList());

        return new ResponseDataContainerModel<MarvelCharacterDTO>()
                .setResults(charactersDto)
                .setCount(charactersDto.size())
                .setNumberPage(model.getNumberPage())
                .setPageSize(model.getPageSize());
    }

    @Override
    public MarvelCharacterDTO getCharacterById(Long id) {
        Optional<MarvelCharacter> optionalCharacter = characterRepository.findById(id);

        if (optionalCharacter.isPresent())
            return characterToDtoConverter.convert(optionalCharacter.get());
        else
            throw new CharacterNotFoundException("Character with id:" + id + " not found");

    }

    @Override
    public List<ComicDTO> getComicsByCharacterId(Long characterId) {
        Optional<MarvelCharacter> optionalCharacter = characterRepository.findById(characterId);

        return optionalCharacter.map(character -> character.getComics()
                .stream()
                .map(comicToDtoConverter::convert)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

}
