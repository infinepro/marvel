package com.marvel.services;

import com.marvel.api.v1.converters.CharacterToCharacterDtoConverter;
import com.marvel.api.v1.converters.ComicToComicDtoConverter;
import com.marvel.api.v1.model.CharacterDTO;
import com.marvel.api.v1.model.ComicDTO;
import com.marvel.api.v1.model.QueryCharacterModel;
import com.marvel.api.v1.model.ResponseDataContainerModel;
import com.marvel.domain.Character;
import com.marvel.repositories.CharacterRepository;
import com.marvel.repositories.ComicRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
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

    @Override
    public ResponseDataContainerModel<CharacterDTO> getCharacters(QueryCharacterModel model) {
        Sort sort;
        List<CharacterDTO> characters;
        Long modifiedSince;

        //create sort
        if (model.getOrderBy() == null || model.getOrderBy().equals("name") || model.getOrderBy().isEmpty())
            sort = Sort.by("name");
        else if (model.getOrderBy().equals("-name"))
            sort = Sort.by("name").descending();
        else if (model.getOrderBy().equals("modified"))
            sort = Sort.by("modified");
        else
            sort = Sort.by("modified").descending();

        PageRequest pageable = PageRequest.of(model.getNumberPage(), model.getPageSize(), sort);

        //parse date
        if (model.getModifiedSince() == null || model.getModifiedSince().isEmpty())
            modifiedSince = Long.MIN_VALUE;
        else
            modifiedSince = parseStringDateFormatToLong(model.getModifiedSince());


        if (model.getName() == null || model.getName().isEmpty()) {
            characters = characterRepository
                    .findAllByModifiedAfter(modifiedSince, pageable)
                    .stream()
                    .map(characterToDtoConverter::convert)
                    .collect(Collectors.toList());
        } else {
            characters = characterRepository
                    .findAllByNameAndModifiedAfter(model.getName(), modifiedSince, pageable)
                    .stream()
                    .map(characterToDtoConverter::convert)
                    .collect(Collectors.toList());
        }

        return new ResponseDataContainerModel<CharacterDTO>()
                .setResults(characters)
                .setCount(characters.size())
                .setNumberPage(model.getNumberPage())
                .setPageSize(model.getPageSize());
    }

    @Override
    public CharacterDTO getCharacterById(Long id) {
        log.warn(String.valueOf(characterRepository.findById(id).get()));
        Optional<Character> optionalCharacter = characterRepository.findById(id);

        if (optionalCharacter.isPresent())
            return characterToDtoConverter.convert(optionalCharacter.get());
        else
            return new CharacterDTO();

    }

    @Override
    public List<ComicDTO> getComicsByCharacterId(Long characterId) {
        Optional<Character> optionalCharacter = characterRepository.findById(characterId);

        return optionalCharacter.map(character -> character.getComicList()
                .stream()
                .map(comicToDtoConverter::convert)
                .collect(Collectors.toList())).orElseGet(ArrayList::new);
    }

}
